package at.fhtw.tourplanner.application.presentation;

import at.fhtw.tourplanner.application.service.TourService;
import at.fhtw.tourplanner.application.service.commands.CreateTourCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourCommand;
import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.TourId;
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
@RequestMapping(value = TourRestController.BASE_URL)
public class TourRestController {
    private final TourService tourService;

    public static final String BASE_URL = "/api/tour";
    public static final String PATH_INDEX = "/";
    public static final String PATH_VAR_ID = "/{id}";
    public static final String ROUTE_ID = BASE_URL + PATH_VAR_ID;

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

    @PostMapping({"", PATH_INDEX})
    public HttpEntity<TourDto> createTour(@RequestBody CreateTourCommand command) {
        log.debug("Incoming Http POST request with create tour command {} received", command);
        TourDto tour = tourService.createTour(command);
        return ResponseEntity.created(createSelfLink(tour)).body(tour);
    }

    @PutMapping(PATH_VAR_ID)
    public HttpEntity<TourDto> updateTour(@PathVariable UUID id,
                                          @RequestBody UpdateTourCommand command) {
        log.debug("Incoming Http PUT request for id {} with update tour command {} received",
                id,
                command);
        return ResponseEntity.ok(tourService.updateTour(new TourId(id), command));
    }

    @DeleteMapping(PATH_VAR_ID)
    public HttpEntity<Void> deleteTour(@PathVariable UUID id) {
        log.debug("Incoming Http DELETE request to delete tour with id {} received", id);
        tourService.deleteTour(new TourId(id));
        return ResponseEntity.ok().build();
    }

    private URI createSelfLink(TourDto tour) {
        URI selfLink = UriComponentsBuilder.fromPath(ROUTE_ID)
                .uriVariables(Map.of("id", tour.id()))
                .build().toUri();
        log.trace("Created self link {} for tour {}", selfLink, tour);
        return selfLink;
    }
}
