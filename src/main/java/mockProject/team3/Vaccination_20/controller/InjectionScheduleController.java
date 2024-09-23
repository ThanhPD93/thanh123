package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.InjectionScheduleResponseDto1;
import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.InjectionScheduleRequestDto1;
import mockProject.team3.Vaccination_20.service.InjectionScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/injection-schedule")
public class InjectionScheduleController {
	@Autowired
    private InjectionScheduleService injectionScheduleService;

    @GetMapping("/findAll")
    public Page<InjectionScheduleResponseDto1> findAll(@RequestParam String searchInput,
                                                       @RequestParam int page,
                                                       @RequestParam int size){
        return injectionScheduleService.findBySearch(searchInput, page, size);
    }

    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/injection-schedule/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @PostMapping("/add")
    public String add(@RequestBody InjectionScheduleRequestDto1 injectionScheduleRequestDto1) {
        int save = injectionScheduleService.save(injectionScheduleRequestDto1);
        if (save == 1) {
            return "add success";
        }
        if (save == -1) {
            return "add fail, no vaccine found for schedule";
        }
        return "add fail";
    }

    @GetMapping("/findById")
    public ResponseEntity<InjectionScheduleResponseDto1> findById(@RequestParam String id) {
        return ResponseEntity.ok(injectionScheduleService.findByInjectionScheduleId(id));
    }
}
