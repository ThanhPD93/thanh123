package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.InjectionScheduleDto;
import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.SaveRequestInjectionSchedule;
import mockProject.team3.Vaccination_20.model.InjectionSchedule;
import mockProject.team3.Vaccination_20.service.InjectionScheduleService;
import mockProject.team3.Vaccination_20.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/injection-schedule")
public class InjectionScheduleController {
	@Autowired
    private InjectionScheduleService injectionScheduleService;

    @GetMapping("/findAll")
    public Page<InjectionScheduleDto> findAll(@RequestParam String searchInput,
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
    public String add(@RequestBody SaveRequestInjectionSchedule saveRequestInjectionSchedule) {
        int save = injectionScheduleService.save(saveRequestInjectionSchedule);
        if (save == 1) {
            return "add success";
        }
        if (save == -1) {
            return "add fail, no vaccine found for schedule";
        }
        return "add fail";
    }
}
