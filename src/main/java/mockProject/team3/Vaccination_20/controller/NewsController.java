package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {
	@Autowired
    private NewsService newsService;
}
