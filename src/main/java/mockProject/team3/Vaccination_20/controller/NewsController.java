package mockProject.team3.Vaccination_20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @ApiResponse(responseCode = "200", description = "Ajax HTML code loaded successfully!"),
            @ApiResponse(responseCode = "400", description = "Ajax file name must not be empty!"),
            @ApiResponse(responseCode = "404", description = "Ajax path could not find file!")
    })
    @GetMapping("/getAjax")
    public ResponseEntity<String> getDocument(@RequestParam String filename) throws IOException {
        // Check if the filename is empty or null
        if (filename == null || filename.isEmpty()) {
            return ResponseEntity.badRequest().body("Filename must not be empty!");
        }

        ClassPathResource resource = new ClassPathResource("static/html/news/" + filename);
        Path path = resource.getFile().toPath();

        // Check if the file exists
        if (!Files.exists(path)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HTML file not found!");
        }

        // Return the file content with response 200
        return ResponseEntity.ok(Files.readString(path));
    }

    @Operation(summary = "Add a new news or update an existing one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "string", example = "New news added(updated) success"))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(type = "string", example = "Add(update) failed")))
    })
    @PostMapping("/add")
    public ResponseEntity<NewsResponseDto1> addNews(@Valid @RequestBody NewsRequestDto1 newsRequestDto1) {
        NewsResponseDto1 newsResponseDto1 = newsService.addNews(newsRequestDto1);
        return ResponseEntity.ok(newsResponseDto1);
    }

    @Operation(summary = "find all news and put in a pagination list for display")
    @ApiResponse(responseCode = "200", description = "Pagination list of news found!")
    @GetMapping("/findAllNews")
    public ResponseEntity<Page<NewsResponseDto1>> findAllNews(
            @RequestParam String searchInput,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<NewsResponseDto1> newsResponseDto = newsService.findByTittleOrContent(searchInput, page, size);
        return ResponseEntity.ok(newsResponseDto);
    }
}
