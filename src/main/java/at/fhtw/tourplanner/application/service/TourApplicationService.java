package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.TourRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
