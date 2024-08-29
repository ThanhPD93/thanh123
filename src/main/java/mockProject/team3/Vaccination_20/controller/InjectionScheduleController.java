package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.service.InjectionScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/injection-schedule")
public class InjectionScheduleController {
	@Autowired
    private InjectionScheduleService injectionScheduleService;

}
