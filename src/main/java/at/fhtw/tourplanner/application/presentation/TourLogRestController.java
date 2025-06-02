package at.fhtw.tourplanner.application.presentation;

import at.fhtw.tourplanner.application.service.TourLogService;
import at.fhtw.tourplanner.application.service.commands.CreateTourLogCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourLogCommand;
import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.model.TourLogId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
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

    @PostMapping({"", PATH_INDEX})
    public HttpEntity<TourLogDto> createTourLog(@RequestBody CreateTourLogCommand command) {
        log.debug("Incoming Http POST request with create tour Log command {} received", command);
        TourLogDto tour = tourLogService.createTourLog(command);
        return ResponseEntity.created(createSelfLink(tour)).body(tour);
    }

    @PutMapping(PATH_VAR_ID)
    public HttpEntity<TourLogDto> updateTour(@PathVariable UUID id,
                                          @RequestBody UpdateTourLogCommand command) {
        log.debug("Incoming Http PUT request for id {} with update tour Log command {} received",
                id,
                command);
        return ResponseEntity.ok(tourLogService.updateTourLog(new TourLogId(id), command));
    }

    @DeleteMapping(PATH_VAR_ID)
    public HttpEntity<Void> deleteTourLog(@PathVariable UUID id) {
        log.debug("Incoming Http DELETE request to delete tour Log with id {} received", id);
        tourLogService.deleteTourLog(new TourLogId(id));
        return ResponseEntity.ok().build();
    }

    private URI createSelfLink(TourLogDto tourLog) {
        URI selfLink = UriComponentsBuilder.fromPath(PATH_VAR_TOUR_ID)
                .uriVariables(Map.of("id", tourLog.id()))
                .build().toUri();
        log.trace("Created self link {} for tourLog {}", selfLink, tourLog);
        return selfLink;
    }
}
