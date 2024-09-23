package mockProject.team3.Vaccination_20.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto3;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto1;
import mockProject.team3.Vaccination_20.dto.injectionResultDto.*;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeResponseDto3;
import mockProject.team3.Vaccination_20.model.Customer;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.repository.InjectionResultRepository;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.InjectionResultService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void setupMapperCustomerOfResultDto() {
        modelMapper.addMappings(new PropertyMap<Customer, CustomerResponseDto3>() {
            @Override
            protected void configure() {
                map().setCustomerId(source.getCustomerId());
                map().setDateOfBirth(source.getDateOfBirth());
                map().setFullName(source.getFullName());
            }
        });
    }
    @PostConstruct
    public void setupMapperVaccineDisplayDto() {
        modelMapper.addMappings(new PropertyMap<Vaccine, VaccineResponseDto1>() {
            @Override
            protected void configure() {
                map().setVaccineId(source.getVaccineId());
                map().setVaccineName(source.getVaccineName());
            }
        });
    }

    @Override
    public InjectionResultResponseDto2 displayDropdown() {
        InjectionResultResponseDto2 injectionResultResponseDto2 = new InjectionResultResponseDto2();
        List<VaccineType> vaccineTypes = vaccineTypeRepository.findAll();
        List<Customer> customers = customerRepository.findAll();
        List<VaccineTypeResponseDto3> vaccineTypeResponseDto3s = modelMapper.map(vaccineTypes, new TypeToken<List<VaccineTypeResponseDto3>>(){}.getType());
        List<CustomerResponseDto3> customerResponseDto3s = modelMapper.map(customers, new TypeToken<List<CustomerResponseDto3>>(){}.getType());
        injectionResultResponseDto2.setCustomers(customerResponseDto3s);
        injectionResultResponseDto2.setVaccineTypes(vaccineTypeResponseDto3s);
        return injectionResultResponseDto2;
    }

    @Override
    public Page<InjectionResultResponseDto3> findBySearch(String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InjectionResult> injectionResults;
        if (searchInput.trim().isEmpty()) {
         	injectionResults = injectionResultRepository.findAll(pageable);
        } else {
            injectionResults = injectionResultRepository.findBySearch(searchInput, pageable);
        }
        List<InjectionResultResponseDto3> injectionResultResponseDto3s = modelMapper.map(injectionResults.getContent(), new TypeToken<List<InjectionResultResponseDto3>>(){}.getType());
    	return new PageImpl<>(injectionResultResponseDto3s, pageable, injectionResults.getTotalElements());
    }

    //show all
    public List<InjectionResultResponseDto1> getAllInjectionResults() {
        return injectionResultRepository.findAllInjectionResults();
    }
    //show with pagination
    public Page<InjectionResultResponseDto1> findBySearchWithPagination(String searchInput, int page, int size) {
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
    public int addInjectionResult(InjectionResultRequestDto1 dto) {
        InjectionResult injectionResult = modelMapper.map(dto, InjectionResult.class);
        Customer customer;
        Vaccine vaccine;
        VaccineType vaccineType;
        try {
            customer = customerRepository.findById(dto.getCustomerId()).get();
            vaccine = vaccineRepository.findById(dto.getVaccineId()).get();
            vaccineType = vaccineTypeRepository.findById(dto.getVaccineTypeId()).get();
        } catch (Exception e) {
            return 0;
        }
        vaccine.setVaccineType(vaccineType);
        injectionResult.setCustomer(customer);
        injectionResult.setVaccineFromInjectionResult(vaccine);
        injectionResultRepository.save(injectionResult);
        return 1;
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

    //for report
//    @Override
//    public List<Integer> findDistinctYears() {
//        return injectionResultRepository.findDistinctYears();
//    }

    @Override
    public List<Object[]> findInjectionResultsByYear(Integer year) {
        return injectionResultRepository.findInjectionResultsByYear(year);
    }

    //for report
    public List<Object[]> findCustomersVaccinatedByMonth(Integer year) {
        return injectionResultRepository.findCustomersVaccinatedByMonth(year);
    }

    public List<Object[]> findVaccineCountByMonth(Integer year) {
        return injectionResultRepository.findVaccineCountByMonth(year);
    }
}
