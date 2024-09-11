package mockProject.team3.Vaccination_20.service.impl;

import mockProject.team3.Vaccination_20.dto.CInjectionResultDTO;
import mockProject.team3.Vaccination_20.dto.InjectionResultDTO;
import mockProject.team3.Vaccination_20.model.Customer;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.repository.InjectionResultRepository;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.InjectionResultService;
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
    static final String PREVENTIONS_FILE_PATH = "src/main/resources/static/data/preventions.txt";
    static final String PLACES_FILE_PATH = "src/main/resources/static/data/places.txt";

    public List<String> getAllPreventions() {
        return readFile(PREVENTIONS_FILE_PATH);
    }

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
        System.out.println("Received DTO: " + dto); // Debugging line

        InjectionResult injectionResult = new InjectionResult();

//        // Customer
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

//        // Vaccine
        Vaccine vaccine = vaccineRepository.findById(dto.getVaccineName())
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));

        // vaccine type name
        VaccineType vaccineType = vaccineTypeRepository.findById(dto.getVaccineTypeName())
                .orElseThrow(() -> new RuntimeException("Vaccine type not found"));

        // Set value
        injectionResult.setCustomer(customer);
        injectionResult.setVaccineFromInjectionResult(vaccine);
        injectionResult.setPrevention(dto.getVaccineTypeName());
        injectionResult.setNumberOfInjection(dto.getInjection());
        injectionResult.setInjectionDate(dto.getInjectionDate());
        injectionResult.setNextInjectionDate(dto.getNextInjectionDate());
        injectionResult.setInjectionPlace(dto.getInjectionPlace());

        return injectionResultRepository.save(injectionResult);
    }

}
