package at.fhtw.tourplanner.application.presentation;

import at.fhtw.tourplanner.application.service.TourLogService;
import at.fhtw.tourplanner.application.service.dto.TourLogDto;
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
@RequestMapping(value = TourLogRestController.BASE_URL)
public class TourLogRestController {
    private final TourLogService tourLogService;

    public static final String BASE_URL = "/api/tourLog";
    public static final String PATH_INDEX = "/";
    public static final String PATH_VAR_ID = "/{id}";
    public static final String PATH_VAR_TOUR_ID = "tour/{tourId}";

    @GetMapping(value = PATH_VAR_TOUR_ID)
    public HttpEntity<List<TourLogDto>> getTourLogsOfTour(@PathVariable UUID tourId) {
        log.debug("Incoming Http GET tour logs with tour id {} request received", tourId);
        List<TourLogDto> tourLogs = tourLogService.getTourLogsOfTour(new TourId(tourId));
        return (tourLogs.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(tourLogs);
    }
}
