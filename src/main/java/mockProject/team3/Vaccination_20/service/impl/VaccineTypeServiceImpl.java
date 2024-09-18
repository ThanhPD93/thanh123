package mockProject.team3.Vaccination_20.service.impl;
import jakarta.persistence.EntityNotFoundException;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.CRequestVaccineType;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.DResponseVaccineType;

import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.FindAllResponseVaccineType;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.FindByIdResponseVaccineType;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.VaccineTypeService;
import mockProject.team3.Vaccination_20.utils.InjectionScheduleStatus;
import mockProject.team3.Vaccination_20.utils.Status;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.List;

@Service
public class VaccineTypeServiceImpl implements VaccineTypeService {

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<VaccineType> getAllVaccineTypes() {
        return vaccineTypeRepository.findAll();
    }

    @Override
    public VaccineType addVaccineType(VaccineType vaccineType) {
        if (vaccineType == null) {
            throw new IllegalArgumentException("VaccineType cannot be null.");
        }

        // If image is not provided, handle as necessary (e.g., setting a default image or leaving it null)
        if (vaccineType.getVaccineTypeImage() != null && vaccineType.getVaccineTypeImage().length > 0) {
            try {
                // Assuming the image is still in Base64 format and needs decoding
                String base64Image = new String(vaccineType.getVaccineTypeImage());
                byte[] decodedImage = Base64.getDecoder().decode(base64Image.replaceAll("\\s", "")); // Clean and decode
                vaccineType.setVaccineTypeImage(decodedImage); // Set the decoded byte array
            } catch (IllegalArgumentException e) {
                System.err.println("Failed to decode image: " + e.getMessage());
                throw new RuntimeException("Invalid Base64 image data", e);
            }
        }
        // Save the vaccine type to the repository
        return vaccineTypeRepository.save(vaccineType);
    }

    @Override
    public VaccineType findVaccineTypeById(String id) {
        return vaccineTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaccine type not found with id: " + id));
    }

    @Override
    public int addVaccineType(CRequestVaccineType cRequestVaccineType) {
        VaccineType vaccineType = vaccineTypeRepository.findByVaccineTypeId(cRequestVaccineType.getVaccineTypeId());
        if(cRequestVaccineType.getVaccineTypeImage() == null && vaccineType != null) {
            byte[] currentImage = vaccineTypeRepository.findByVaccineTypeId(cRequestVaccineType.getVaccineTypeId()).getVaccineTypeImage();
            vaccineType = modelMapper.map(cRequestVaccineType, VaccineType.class);
            vaccineType.setVaccineTypeImage(currentImage);
        } else {
            vaccineType = modelMapper.map(cRequestVaccineType, VaccineType.class);
        }
        if (cRequestVaccineType.getVaccineTypeImage() != null && !cRequestVaccineType.getVaccineTypeImage().isEmpty()) {
            try {
                // Clean and decode the Base64 string
                String base64Image = cRequestVaccineType.getVaccineTypeImage();
                byte[] decodedImage = Base64.getDecoder().decode(base64Image);
                vaccineType.setVaccineTypeImage(decodedImage);
            } catch (IllegalArgumentException e) {
                return 0;
            }
        }
        VaccineType vaccineTypeResult = vaccineTypeRepository.save(vaccineType);
        return 1;
    }

    @Override
    public FindByIdResponseVaccineType findById(String id) {
        VaccineType vaccineType = vaccineTypeRepository.findById(id).get();
        return modelMapper.map(vaccineType, FindByIdResponseVaccineType.class);
    }

    @Override
    public int makeInactive(List<String> vaccineTypeIds) {
        int count = 0;
        List<VaccineType> vaccineTypes = vaccineTypeRepository.findAllById(vaccineTypeIds);
        if(vaccineTypes.isEmpty()) {
            return 0;
        }
        for (VaccineType vaccineType : vaccineTypes) {
            if(vaccineType.getVaccineTypeStatus() == Status.INACTIVE) {
                return 0;
            }
            vaccineType.setVaccineTypeStatus(Status.INACTIVE);
            count++;
        }
        vaccineTypeRepository.saveAll(vaccineTypes);
        return count;
    }

    @Override
    public Page<FindAllResponseVaccineType> findBySearchWithPagination(String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
		Page<VaccineType> vaccineTypePage;
        if (StringUtils.hasText(searchInput)) {
            vaccineTypePage = vaccineTypeRepository.findBySearch(searchInput, pageable);
        } else {
            vaccineTypePage = vaccineTypeRepository.findAll(pageable);
        }
        List<FindAllResponseVaccineType> responseVaccineTypes = modelMapper.map(vaccineTypePage.getContent(), new TypeToken<List<FindAllResponseVaccineType>>() {}.getType());
        return new PageImpl<>(responseVaccineTypes, pageable, vaccineTypePage.getTotalElements());
    }


}
