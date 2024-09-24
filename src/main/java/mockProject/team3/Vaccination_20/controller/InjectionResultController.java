package mockProject.team3.Vaccination_20.controller;


import jakarta.validation.Valid;
import mockProject.team3.Vaccination_20.dto.injectionResultDto.*;
import mockProject.team3.Vaccination_20.service.CustomerService;
import mockProject.team3.Vaccination_20.service.InjectionResultService;
import mockProject.team3.Vaccination_20.service.VaccineService;
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
@RequestMapping("/api/injection-result")
public class InjectionResultController {

    @Autowired
    private InjectionResultService injectionResultService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VaccineService vaccineService;

    //call
    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/injectionresult/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @GetMapping("/displayDropdown")
    public ResponseEntity<InjectionResultResponseDto2> displayDropdown() {
        return ResponseEntity.ok(injectionResultService.displayDropdown());
    }

    @GetMapping("/findBySearch")
    public ResponseEntity<Page<InjectionResultResponseDto3>> findBySearch(@RequestParam String searchInput,
                                                                          @RequestParam int page,
                                                                          @RequestParam int size) {
        return ResponseEntity.ok(injectionResultService.findBySearch(searchInput,page,size));
    }

    //load data from file injection place
    @GetMapping("/places")
    public ResponseEntity<List<String>> getAllInjectionPlaces() {
        List<String> places = injectionResultService.getAllInjectionPlaces();
        return new ResponseEntity<>(places, HttpStatus.OK);
    }

    //add
    @PostMapping("/add")
    public ResponseEntity<String> addInjectionResult(@Valid @RequestBody InjectionResultRequestDto1 injectionResultDto) {
        int result = injectionResultService.addInjectionResult(injectionResultDto);
        if (result == 0) {
            return ResponseEntity.badRequest().body("an exception was thrown during service operation");
        } else {
            return ResponseEntity.ok("add injection result success!");
        }
    }

    //-detail
    @GetMapping("/detail/{injectionResultId}")
    public ResponseEntity<InjectionResultResponseDto3> getInjectionResult(@PathVariable String injectionResultId) {
        InjectionResultResponseDto3 injectionResult = injectionResultService.getInjectionResultById(injectionResultId);
        return ResponseEntity.ok(injectionResult);
    }

    //update
    @PutMapping("/update/{injectionResultId}")
    public ResponseEntity<UInjectionResultDTO> updateInjectionResult(@PathVariable String injectionResultId, @RequestBody UInjectionResultDTO uInjectionResultDTO) {
        UInjectionResultDTO updateInjectionResult = injectionResultService.updateInjectionResult(injectionResultId, uInjectionResultDTO);
        return ResponseEntity.ok(updateInjectionResult);
    }

    //delete
    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteInjectionResults(@RequestBody List<String> ids) {
        try {
            injectionResultService.deleteInjectionResults(ids);
            return ResponseEntity.ok(new ApiResponse<>(200, "Injection results deleted successfully.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "Error deleting injection results.", null));
        }
    }

}

