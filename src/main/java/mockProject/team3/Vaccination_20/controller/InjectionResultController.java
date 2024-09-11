package mockProject.team3.Vaccination_20.controller;


import mockProject.team3.Vaccination_20.dto.CInjectionResultDTO;
import mockProject.team3.Vaccination_20.dto.InjectionResultDTO;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.repository.InjectionResultRepository;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.service.InjectionResultService;
import mockProject.team3.Vaccination_20.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@RestController
@RequestMapping("/injection-result")
public class InjectionResultController {

    @Autowired
    private InjectionResultService injectionResultService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private InjectionResultRepository injectionResultRepository;

    //call
    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/injectionresult/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }

   //show list
    @GetMapping("/list")
    public ResponseEntity<List<InjectionResultDTO>> getAllInjectionResults() {
        List<InjectionResultDTO> injectionResults = injectionResultService.getAllInjectionResults();
        return new ResponseEntity<>(injectionResults, HttpStatus.OK);
    }

    //show with pagination
    @GetMapping("/findAllWithPagination")
    public Page<InjectionResultDTO> findAllWithPagination(@RequestParam String searchInput,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        return injectionResultService.findBySearchWithPagination(searchInput, page, size);
    }


    //load data from file injection place
    @GetMapping("/places")
    public ResponseEntity<List<String>> getAllInjectionPlaces() {
        List<String> places = injectionResultService.getAllInjectionPlaces();
        return new ResponseEntity<>(places, HttpStatus.OK);
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<InjectionResult>> addInjectionResult(@RequestBody CInjectionResultDTO injectionResultDto) {
        InjectionResult savedResult = injectionResultService.addInjectionResult(injectionResultDto);
        ApiResponse<InjectionResult> response;

        if (savedResult != null) {
            response = new ApiResponse<>(1, "Injection result added successfully", savedResult);
        } else {
            response = new ApiResponse<>(0, "Failed to add injection result", null);
        }

        return ResponseEntity.ok(response);
    }

    //-------------
//    @GetMapping("/detail/{id}")
//    public ResponseEntity<CInjectionResultDTO> getInjectionResultById(@PathVariable String id) {
//        InjectionResult injectionResult = injectionResultService.findInjectionResultById(id);
//        if (injectionResult != null) {
//            CInjectionResultDTO dto = new CInjectionResultDTO();
//            dto.setCustomerId(injectionResult.getCustomer().getCustomerId());
//            dto.setVaccineTypeName(injectionResult.getVaccineFromInjectionResult().getVaccineType().getVaccineTypeName());
//            dto.setVaccineName(injectionResult.getVaccineFromInjectionResult().getVaccineName());
//            dto.setInjection(String.valueOf(injectionResult.getNumberOfInjection()));
//            dto.setInjectionDate(injectionResult.getInjectionDate());
//            dto.setNextInjectionDate(injectionResult.getNextInjectionDate());
//            dto.setInjectionPlace(injectionResult.getInjectionPlace());
//            return ResponseEntity.ok(dto);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<InjectionResult> updateInjectionResult(@PathVariable String id, @RequestBody CInjectionResultDTO dto) {
//        InjectionResult injectionResult = injectionResultService.findInjectionResultById(id);
//        if (injectionResult == null) {
//            return ResponseEntity.notFound().build();
//        }
//        // Update properties
//        injectionResult.setCustomer(customerRepository.findById(dto.getCustomerId()).orElse(null));
//        injectionResult.setVaccineFromInjectionResult(vaccineRepository.findById(dto.getVaccineName()).orElse(null));
//        injectionResult.setNumberOfInjection((dto.getInjection()));
//        injectionResult.setInjectionDate(dto.getInjectionDate());
//        injectionResult.setNextInjectionDate(dto.getNextInjectionDate());
//        injectionResult.setInjectionPlace(dto.getInjectionPlace());
//
//        // Save updated injectionResult
//        InjectionResult updatedInjectionResult = injectionResultRepository.save(injectionResult);
//        return ResponseEntity.ok(updatedInjectionResult);
//    }

}

