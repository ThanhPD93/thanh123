package mockProject.team3.Vaccination_20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineRequestDto1;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto3;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto4;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto5;
import mockProject.team3.Vaccination_20.service.VaccineService;
import mockProject.team3.Vaccination_20.utils.MSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/vaccine")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;

    @Operation(summary = "Using ajax to load content dynamically")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ajax html code loaded successfully"),
            @ApiResponse(responseCode = "400", description = "ajax file name must not be empty!"),
            @ApiResponse(responseCode = "404", description = "ajax path could not find file!")
    })
    @GetMapping("/getAjax")
    public ResponseEntity<String> getDocument(@RequestParam String filename) throws IOException {
        try{
            if (filename == null || filename.isEmpty()) {
                return ResponseEntity.badRequest().body(MSG.MSG31.getMessage());
            }
            ClassPathResource resource = new ClassPathResource("static/html/vaccine/" + filename);
            Path path = resource.getFile().toPath();
            return ResponseEntity.ok(Files.readString(path));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MSG.MSG32.getMessage());

        }
    }

    @Operation(summary = "Add a new vaccine or update an existing one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "string", example = "New vaccine added(updated) sucessfully"))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(type = "string", example = "Add(update) failed!")))
    })
    @PostMapping("/add")
    public ResponseEntity<String> createVaccine(@Valid @RequestBody VaccineRequestDto1 vaccineRequestDto1) {
        int result = vaccineService.createVaccine(vaccineRequestDto1);
        if (result == 0) {
            System.out.println("Failed to add/updated vaccine ");
            return ResponseEntity.badRequest().body("cannot add new vaccine");
        }
        System.out.println("Vaccine added/updated successfully");
        return ResponseEntity.ok("add new vaccine success!");
    }

    @Operation(summary = "find all vaccine and put in a pagination list for display")
    @ApiResponse(responseCode = "200", description = "Pagination list of vaccine found!")
    @GetMapping("/search")
    public ResponseEntity<Page<VaccineResponseDto3>> getVaccineListBySearchInput(
            @RequestParam("searchInput") String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<VaccineResponseDto3> vaccinePage = vaccineService.getVaccineListBySearchInput(searchInput, page, size);
        return ResponseEntity.ok(vaccinePage);
    }

    @Operation(summary = "fetch a vaccine from database by vaccine Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found a vaccine by vaccine ID provided"),
            @ApiResponse(responseCode = "404", description = "vaccine not found by vaccine ID provided")
    })
    @GetMapping("/detail/{vaccineId}")
    public ResponseEntity<VaccineResponseDto5> getVaccineById(@PathVariable String vaccineId) {
        VaccineResponseDto5 vaccineResponseDto5 = vaccineService.getVaccineById(vaccineId);
        if(vaccineResponseDto5 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vaccineResponseDto5);
    }

    @Operation(summary = "change status of selected vaccines from active to inactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Change status successfully!"),
            @ApiResponse(responseCode = "400", description = "Failed to change vaccine status!")
    })
    @PutMapping("/change-status")
    public ResponseEntity<String> updateVaccineStatus(@RequestBody List<String> vaccineIds) {
        int result = vaccineService.changeStatusVaccine(vaccineIds);
        if (result == -2) return ResponseEntity.badRequest().body("Could not find a vaccine with the given IDs.");
        if (result == -1) return ResponseEntity.badRequest().body("Please select only vaccines with inactive status.");
        if (result == 0) return ResponseEntity.badRequest().body("No vaccine was selected.");

        return ResponseEntity.ok("Successfully changed " + result + " vaccines' status to inactive.");
    }

    @Operation(summary = "Export excel template!")
    @GetMapping("/export/excel")
    public void exportTemplate(HttpServletResponse response) throws IOException {
        vaccineService.exportTemplate(response);
    }

    @Operation(summary = "Import vaccine through excel file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Import success!"),
            @ApiResponse(responseCode = "400", description = "File import is empty!"),
            @ApiResponse(responseCode = "500", description = "Failed to upload and import file")
    })
    @PostMapping("/import/excel")
    public ResponseEntity<String> importFromExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a valid Excel file.");
        }
        try {
            List<String> notifications = vaccineService.importVaccineFromExcel(file);

            StringBuilder responseMessage = new StringBuilder("File uploaded and data imported successfully.");
            if (!notifications.isEmpty()) {
                responseMessage.append(" However, the following issues were found:\n");
                for (String notification : notifications) {
                    responseMessage.append(notification).append("\n");
                }
            }

            return ResponseEntity.ok(responseMessage.toString());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload and import file.");
        }
    }

    @Operation(summary = "find all vaccine")
    @ApiResponse(responseCode = "200", description = "List of vaccine is found")
    @GetMapping("/findAllVaccineName")
    public ResponseEntity<List<VaccineResponseDto4>> findAllVaccineName() {
        return ResponseEntity.ok(vaccineService.findAllVaccineName());
    }
}
