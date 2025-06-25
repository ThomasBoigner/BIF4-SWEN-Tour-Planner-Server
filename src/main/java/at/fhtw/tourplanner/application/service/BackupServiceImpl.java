package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.BackupTourDto;
import at.fhtw.tourplanner.application.service.mappers.BackupTourDtoMapper;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.model.TourLog;
import at.fhtw.tourplanner.domain.model.TourLogRepository;
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
public class BackupServiceImpl implements BackupService {
    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;
    private final BackupTourDtoMapper backupTourDtoMapper;

    @Override
    public Optional<BackupTourDto> exportTour(TourId tourId) {
        log.debug("Trying to export tour with id {}", tourId);

        List<TourLog> tourLogs = tourLogRepository.findAllByTourId(tourId);
        Optional<BackupTourDto> backupTourDto = tourRepository.findTourById(tourId).map(
                tour -> backupTourDtoMapper.toDto(tour, tourLogs));
        backupTourDto.ifPresentOrElse(
                t -> log.info("Exported tour {} with id {}", t, tourId.id()),
                () -> log.info("No tour with id {} found", tourId.id()));
        return backupTourDto;
    }
}
