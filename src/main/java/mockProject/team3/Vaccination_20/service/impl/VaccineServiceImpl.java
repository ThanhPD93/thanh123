package mockProject.team3.Vaccination_20.service.impl;

import jakarta.annotation.PostConstruct;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineRequestDto1;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto3;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto4;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto5;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeResponseDto4;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.VaccineService;
import mockProject.team3.Vaccination_20.utils.Status;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VaccineServiceImpl implements VaccineService {
    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void setupMapperVaccineTypeOfVaccineDto() {
        modelMapper.addMappings(new PropertyMap<VaccineType, VaccineTypeResponseDto4>() {
            @Override
            protected void configure() {
                map().setVaccineTypeName(source.getVaccineTypeName());
            }
        });
    }

    @Override
    public int createVaccine(VaccineRequestDto1 vaccineRequestDto1) {
        Vaccine vaccine = modelMapper.map(vaccineRequestDto1, Vaccine.class);
        VaccineType vaccineType = vaccineTypeRepository.findById(vaccineRequestDto1.getVaccineType()).get();
        vaccine.setVaccineType(vaccineType);
        vaccineRepository.save(vaccine);
        return 1;
    }

    @Override
    public Page<VaccineResponseDto3> getVaccineListBySearchInput(String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaccine> vaccines;
        if(searchInput.isEmpty()) {
            vaccines = vaccineRepository.findAll(pageable);
        } else {
            vaccines = vaccineRepository.findBySearchInput(searchInput, pageable);
        }
        List<VaccineResponseDto3> vaccineDtos = modelMapper.map(vaccines.getContent(), new TypeToken<List<VaccineResponseDto3>>(){}.getType());
       	return new PageImpl<>(vaccineDtos, pageable, vaccines.getTotalElements());
    }

    @Override
    public VaccineResponseDto5 getVaccineById(String vaccineId) {
        try {
            Vaccine vaccine = vaccineRepository.findById(vaccineId).get();
            return modelMapper.map(vaccine, VaccineResponseDto5.class);
        } catch(Exception e) {
            return new VaccineResponseDto5();
        }
    }

    @Override
    public int changeStatusVaccine(List<String> vaccineIds) {
        if (vaccineIds == null || vaccineIds.isEmpty()){
            System.out.println("no vaccine was selected");
            return 0;
        }
        try {
            for (String vaccineId : vaccineIds){
                Vaccine selectedVaccine = vaccineRepository.findById(vaccineId).get();
                if(selectedVaccine.getVaccineStatus() == Status.INACTIVE) {
                    System.out.println("Please select only vaccines with ACTIVE status");
                    return -1;
                }
            }
            for (String vaccineId : vaccineIds){
                Vaccine selectedVaccine = vaccineRepository.findById(vaccineId).get();
                selectedVaccine.setVaccineStatus(Status.INACTIVE);
                vaccineRepository.save(selectedVaccine);
            }
        } catch (NoSuchElementException e) {
            System.out.println("could not find a vaccine with given vaccine IDs");
            return -2;
        }
        if(vaccineIds.size() > 1) {
            return vaccineIds.size();
        }
        return 1;
    }

    public List<String> importVaccineFromExcel(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<Vaccine> vaccines = new ArrayList<>();
        List<String> notifications = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            if (row == null) {
                System.out.println("Row " + i + " is empty.");
                continue;
            }

            for (int j = 0; j <= 10; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    System.out.println("Cell " + j + " in row " + i + " is null.");
                } else {
                    System.out.println("Cell " + j + " in row " + i + " contains: " + cell.toString());
                }
            }

            Vaccine vaccine = new Vaccine();

            if (row.getCell(0) != null) {
                vaccine.setVaccineId(row.getCell(0).getStringCellValue());
            }
            if (row.getCell(1) != null) {
                vaccine.setContraindication(row.getCell(1).getStringCellValue());
            }
            if (row.getCell(2) != null) {
                vaccine.setIndication(row.getCell(2).getStringCellValue());
            }
            if (row.getCell(3) != null) {
                vaccine.setNumberOfInjection((int) row.getCell(3).getNumericCellValue());
            }
            if (row.getCell(4) != null) {
                vaccine.setVaccineOrigin(row.getCell(4).getStringCellValue());
            }
            if (row.getCell(5) != null) {
                vaccine.setTimeBeginNextInjection(row.getCell(5).getLocalDateTimeCellValue().toLocalDate());
            }
            if (row.getCell(6) != null) {
                vaccine.setTimeEndNextInjection(row.getCell(6).getLocalDateTimeCellValue().toLocalDate());
            }
            if (row.getCell(7) != null) {
                vaccine.setVaccineUsage(row.getCell(7).getStringCellValue());
            }
            if (row.getCell(8) != null) {
                vaccine.setVaccineName(row.getCell(8).getStringCellValue());
            }
            if (row.getCell(9) != null) {
                vaccine.setVaccineStatus(Status.valueOf(row.getCell(9).getStringCellValue()));
            }

            if (row.getCell(10) != null) {
                String vaccineTypeId = row.getCell(10).getStringCellValue();
                VaccineType vaccineType = vaccineTypeRepository.findById(vaccineTypeId).orElse(null);
                if (vaccineType != null) {
                    if (vaccineType.getVaccineTypeStatus() == Status.ACTIVE) {
                        vaccine.setVaccineType(vaccineType);
                        vaccines.add(vaccine); // Add to vaccines list
                    } else {
                        // Add a notification if vaccineTypeStatus is INACTIVE
                        notifications.add("Cannot add vaccine at row " + (i + 1) + " because the vaccine type is inactive.");
                    }
                }
            }
        }

        System.out.println("Vaccines to be saved: " + vaccines.size());
        vaccineRepository.saveAll(vaccines);

        workbook.close();
        return notifications; // Return notifications to the controller
    }

    public List<VaccineResponseDto4> findAllVaccineName() {
        List<Vaccine> vaccines = vaccineRepository.findAll();
        return modelMapper.map(vaccines, new TypeToken<List<VaccineResponseDto4>>(){}.getType());
    }
}
