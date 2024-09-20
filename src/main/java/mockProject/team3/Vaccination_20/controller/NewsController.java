package mockProject.team3.Vaccination_20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mockProject.team3.Vaccination_20.dto.newsDto.NewsAddRequestDto;
import mockProject.team3.Vaccination_20.dto.newsDto.NewsResponseDto;
import mockProject.team3.Vaccination_20.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Operation(summary = "Add a new news or update an existing one")
    @ApiResponses()
    @PostMapping("/add")
    ResponseEntity<NewsResponseDto> addNews(@Valid @RequestBody NewsAddRequestDto newsAddRequestDto) {
        NewsResponseDto newsResponseDto = newsService.addNews(newsAddRequestDto);
        return ResponseEntity.ok(newsResponseDto);
    }

    @GetMapping("/findAllNews")
    ResponseEntity<Page<NewsResponseDto>> findAllNews(
            @RequestParam String searchInput,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        Page<NewsResponseDto> newsResponseDto = newsService.findByTittleOrContent(searchInput, page, size);
        return ResponseEntity.ok(newsResponseDto);
    }
}
