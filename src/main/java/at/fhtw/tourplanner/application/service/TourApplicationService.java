package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.model.TourRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Slf4j
@Service
@Transactional(readOnly = true)
public class TourApplicationService {
    private final TourRepository tourRepository;

    public List<TourDto> getTours() {
        log.debug("Trying to get all tours");
        List<TourDto> tours = tourRepository.findAll().stream().map(TourDto::new).toList();
        log.info("Retrieved all ({}) tours", tours.size());
        return tours;
    }

    public Optional<TourDto> getTour(TourId id) {
        log.debug("Trying to get tour with id {}", id);
        Optional<TourDto> tour = tourRepository.findTourById(id).map(TourDto::new);
        tour.ifPresentOrElse(
                t -> log.info("Found tour {} with id {}", t, id.id()),
                () -> log.info("No tour with id {} found", id.id()));
        return tour;
    }
}
