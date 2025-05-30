package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import at.fhtw.tourplanner.application.service.mappers.TourLogDtoMapper;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.model.TourLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor

@Slf4j
@Service
@Transactional(readOnly = true)
public class TourLogService {
    private final TourLogRepository tourLogRepository;
    private final TourLogDtoMapper tourLogDtoMapper;

    public List<TourLogDto> getTourLogsOfTour(TourId tourId) {
        log.debug("Trying to get all tour logs of tour with id {}", tourId.id());
        List<TourLogDto> tourLogs = tourLogRepository.findAllByTourId(tourId)
                .stream().map(tourLogDtoMapper::toDto).toList();
        log.info("Retrieved all ({}) tour logs of tour with id {}", tourLogs.size(), tourId.id());
        return  tourLogs;
    }
}
