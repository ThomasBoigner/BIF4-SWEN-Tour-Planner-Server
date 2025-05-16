package at.fhtw.tourplanner.application.presentation;

import at.fhtw.tourplanner.application.service.TourApplicationService;
import at.fhtw.tourplanner.application.service.dto.TourDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@Slf4j
@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = TourRestController.BASE_URL)
public class TourRestController {
    private final TourApplicationService tourService;

    public static final String BASE_URL = "/api/tour";
    public static final String PATH_INDEX = "/";

    @GetMapping({"", PATH_INDEX})
    public HttpEntity<List<TourDto>> getTours() {
        log.debug("Incoming Http GET all tours request received");
        List<TourDto> tours = tourService.getTours();
        return (tours.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(tours);
    }
}
