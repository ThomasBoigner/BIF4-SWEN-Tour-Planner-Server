package at.fhtw.tourplanner.application.presentation;

import at.fhtw.tourplanner.application.service.BackupService;
import at.fhtw.tourplanner.application.service.dto.BackupTourDto;
import at.fhtw.tourplanner.domain.model.TourId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor

@Slf4j
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = HttpHeaders.CONTENT_DISPOSITION)
@RestController
@RequestMapping(value = BackupController.BASE_URL)
public class BackupController {
    public final BackupService backupService;
    public final ObjectMapper objectMapper;

    public static final String BASE_URL = "/api/backup";
    public static final String EXPORT_URL = "/export/{id}";
    public static final String IMPORT_URL = "/import";

    @GetMapping(value = EXPORT_URL, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody HttpEntity<byte[]> exportTour(@PathVariable UUID id)  {
        log.debug("Incoming Http GET request to export tour with id {} received", id);
        Optional<BackupTourDto> optional = backupService.exportTour(new TourId(id));

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BackupTourDto tour = optional.get();

        try {
            return ResponseEntity.ok().header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=%s.json", tour.name())
            ).body(objectMapper.writeValueAsBytes(tour));
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = IMPORT_URL)
    public HttpEntity<Void> importTour(@RequestParam("file") MultipartFile file) {
        log.debug("Incoming Http POST request to import tour received");

        BackupTourDto backupTourDto = null;
        try {
            backupTourDto = objectMapper.readValue(
                    new String(file.getBytes()),
                    BackupTourDto.class
            );
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        backupService.importTour(backupTourDto);
        return ResponseEntity.ok().build();
    }
}
