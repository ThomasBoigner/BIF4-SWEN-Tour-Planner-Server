package at.fhtw.tourplanner.application.presentation;

import at.fhtw.tourplanner.application.service.TourApplicationService;
import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.TourId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = TourRestController.BASE_URL)
public class TourRestController {
    private final TourApplicationService tourService;

    public static final String BASE_URL = "/api/tour";
    public static final String PATH_INDEX = "/";
    public static final String PATH_VAR_ID = "/{id}";

    @GetMapping({"", PATH_INDEX})
    public HttpEntity<List<TourDto>> getTours() {
        log.debug("Incoming Http GET all tours request received");
        List<TourDto> tours = tourService.getTours();
        return (tours.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(tours);
    }

    @GetMapping(PATH_VAR_ID)
    public HttpEntity<TourDto> getTour(@PathVariable UUID id) {
        log.debug("Incoming Http GET tour with id {} request received", id);
        return tourService.getTour(new TourId(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
