package mockProject.team3.Vaccination_20.service.impl;

import jakarta.transaction.Transactional;
import mockProject.team3.Vaccination_20.dto.CInjectionResultDTO;
import mockProject.team3.Vaccination_20.dto.InjectionResultDTO;
import mockProject.team3.Vaccination_20.dto.UInjectionResultDTO;
import mockProject.team3.Vaccination_20.model.Customer;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.repository.InjectionResultRepository;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.InjectionResultService;
import mockProject.team3.Vaccination_20.utils.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class InjectionResultServiceImpl implements InjectionResultService {

    @Autowired
    private InjectionResultRepository injectionResultRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;
    @Autowired
    private ModelMapper modelMapper;

    //show all
    public List<InjectionResultDTO> getAllInjectionResults() {
        return injectionResultRepository.findAllInjectionResults();
    }
    //show with pagination
    public Page<InjectionResultDTO> findBySearchWithPagination(String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return injectionResultRepository.findAllWithPagination(searchInput, pageable);
    }

    //get data from file to dropdown
    static final String PLACES_FILE_PATH = "src/main/resources/static/data/places.txt";

    public List<String> getAllInjectionPlaces() {
        return readFile(PLACES_FILE_PATH);
    }

    private List<String> readFile(String filePath) {
        try {
            return Files.lines(Paths.get(filePath))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //add
    public InjectionResult addInjectionResult(CInjectionResultDTO dto) {
//        System.out.println("Received DTO: " + dto);  // Debugging log

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
//        System.out.println("Customer found: " + customer);  // Debug log

        Vaccine vaccine = vaccineRepository.findById(dto.getVaccineName())
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));
//        System.out.println("Vaccine found: " + vaccine);  // Debug log

        VaccineType vaccineType = vaccineTypeRepository.findById(dto.getVaccineTypeName())
                .orElseThrow(() -> new RuntimeException("Vaccine type not found"));
//        System.out.println("VaccineType found: " + vaccineType);  // Debug log

        // Set and save InjectionResult
        InjectionResult injectionResult = new InjectionResult();
        injectionResult.setCustomer(customer);
        injectionResult.setVaccineFromInjectionResult(vaccine);
        injectionResult.setNumberOfInjection(dto.getInjection());
        injectionResult.setInjectionDate(dto.getInjectionDate());
        injectionResult.setNextInjectionDate(dto.getNextInjectionDate());
        injectionResult.setInjectionPlace(dto.getInjectionPlace());
        injectionResult.setInjectionResultId(dto.getInjectionResultId());

        return injectionResultRepository.save(injectionResult);
    }

    //detail
    @Override
    public UInjectionResultDTO getInjectionResultById(String id) {
        InjectionResult injectionResult = injectionResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("InjectionResult not found"));

        UInjectionResultDTO dtoDetail = new UInjectionResultDTO();
        dtoDetail.setInjectionResultId(injectionResult.getInjectionResultId());
        dtoDetail.setCustomerId(injectionResult.getCustomer().getCustomerId());
        dtoDetail.setVaccineId(injectionResult.getVaccineFromInjectionResult().getVaccineId());
        dtoDetail.setVaccineTypeId(injectionResult.getVaccineFromInjectionResult().getVaccineType().getVaccineTypeId());
        dtoDetail.setInjection(injectionResult.getNumberOfInjection());
        dtoDetail.setInjectionDate(injectionResult.getInjectionDate());
        dtoDetail.setNextInjectionDate(injectionResult.getNextInjectionDate());
        dtoDetail.setInjectionPlace(injectionResult.getInjectionPlace());

        return dtoDetail;
    }

    //-------update
    @Override
    public UInjectionResultDTO updateInjectionResult(String injectionResultId, UInjectionResultDTO uInjectionResultDTO) {
        InjectionResult existInjectionResult = injectionResultRepository.findById(injectionResultId)
                .orElseThrow(() -> new RuntimeException("InjectionResult not found"));

        UInjectionResultDTO dtoUpdate = new UInjectionResultDTO();
        dtoUpdate.setInjectionResultId(injectionResultId);
        dtoUpdate.setCustomerId(existInjectionResult.getCustomer().getCustomerId());
        dtoUpdate.setVaccineId(existInjectionResult.getVaccineFromInjectionResult().getVaccineId());
        dtoUpdate.setVaccineTypeId(existInjectionResult.getVaccineFromInjectionResult().getVaccineType().getVaccineTypeId());
        dtoUpdate.setInjection(existInjectionResult.getNumberOfInjection());
        dtoUpdate.setInjectionDate(existInjectionResult.getInjectionDate());
        dtoUpdate.setNextInjectionDate(existInjectionResult.getNextInjectionDate());
        dtoUpdate.setInjectionPlace(existInjectionResult.getInjectionPlace());
        return dtoUpdate;
    }

    @Transactional
    @Override
    public void deleteInjectionResults(List<String> ids) {
        for (String id : ids) {
            injectionResultRepository.deleteById(id);
        }
    }
}
