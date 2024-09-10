package mockProject.team3.Vaccination_20.controller;


import mockProject.team3.Vaccination_20.dto.CInjectionResultDTO;
import mockProject.team3.Vaccination_20.dto.InjectionResultDTO;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import mockProject.team3.Vaccination_20.service.InjectionResultService;
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

    //load data from file prevention
    @GetMapping("/preventions")
    public ResponseEntity<List<String>> getAllPreventions() {
        List<String> preventions = injectionResultService.getAllPreventions();
        return new ResponseEntity<>(preventions, HttpStatus.OK);
    }
    //load data from file injection place
    @GetMapping("/places")
    public ResponseEntity<List<String>> getAllInjectionPlaces() {
        List<String> places = injectionResultService.getAllInjectionPlaces();
        return new ResponseEntity<>(places, HttpStatus.OK);
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<?> addInjectionResult(@RequestBody CInjectionResultDTO injectionResultDto) {
        try {
            InjectionResult result = injectionResultService.addInjectionResult(injectionResultDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Log the error message and return a meaningful response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

