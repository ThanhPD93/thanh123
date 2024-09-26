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
        if (filename == null || filename.isEmpty()) {
            return ResponseEntity.badRequest().body("Filename must not be empty!");
        }
        ClassPathResource resource = new ClassPathResource("static/html/news/" + filename);
        Path path = resource.getFile().toPath();
        if (!Files.exists(path)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HTML file not found!");
        }
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

    @Operation(summary = "Find all news and put in a pagination list for display")
    @ApiResponse(responseCode = "200", description = "Pagination list of news found!")
    @ApiResponse(responseCode = "404", description = "No news found for the given search input")
    @ApiResponse(responseCode = "400", description = "Invalid input provided")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/findAllNews")
    public ResponseEntity<Page<NewsResponseDto1>> findAllNews(
            @RequestParam String searchInput,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            if (searchInput == null || searchInput.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null); // 400 Bad Request
            }

            Page<NewsResponseDto1> newsResponseDto = newsService.findByTittleOrContent(searchInput, page, size);

            if (newsResponseDto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
            }

            return ResponseEntity.ok(newsResponseDto); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
        }
    }

}
