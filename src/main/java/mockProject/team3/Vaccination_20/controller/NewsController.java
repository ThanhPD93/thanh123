package mockProject.team3.Vaccination_20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mockProject.team3.Vaccination_20.dto.newsDto.NewsRequestDto1;
import mockProject.team3.Vaccination_20.dto.newsDto.NewsResponseDto1;
import mockProject.team3.Vaccination_20.service.NewsService;
import mockProject.team3.Vaccination_20.utils.MSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Operation(summary = "Using ajax to load content dynamically")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ajax html code loaded successfully!"),
            @ApiResponse(responseCode = "400", description = "ajax file name must not be empty!"),
            @ApiResponse(responseCode = "404", description = "ajax path could not find file!")
    })
    @GetMapping("/getAjax")
    public ResponseEntity<String> getDocument(@RequestParam String filename) throws IOException {
        // if user input filename that is empty or null -> return response 400 and appropriate message
        if(filename == null || filename.isEmpty()) {
            return ResponseEntity.badRequest().body(MSG.MSG31.getMessage());
        }
        ClassPathResource resource = new ClassPathResource("static/html/news/" + filename);
        Path path = resource.getFile().toPath();
        // if the path to the file cannot find the file -> return 404
        if (!Files.exists(path)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MSG.MSG32.getMessage());
        } else {
            // if file found, return response 200 and the file
            return ResponseEntity.ok(Files.readString(path));
        }
    }

    @Operation(summary = "Add a new news or update an existing one")
    @PostMapping("/add")
    public ResponseEntity<NewsResponseDto1> addNews(@Valid @RequestBody NewsRequestDto1 newsRequestDto1) {
        NewsResponseDto1 newsResponseDto1 = newsService.addNews(newsRequestDto1);
        return ResponseEntity.ok(newsResponseDto1);
    }

    @GetMapping("/findAllNews")
    public ResponseEntity<Page<NewsResponseDto1>> findAllNews(
            @RequestParam String searchInput,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        Page<NewsResponseDto1> newsResponseDto = newsService.findByTittleOrContent(searchInput, page, size);
        return ResponseEntity.ok(newsResponseDto);
    }
}
