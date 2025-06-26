package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.BackupTourDto;
import at.fhtw.tourplanner.application.service.mappers.BackupTourDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.BackupTourLogDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor

@Slf4j
@Service
@Transactional(readOnly = true)
public class BackupServiceImpl implements BackupService {
    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;
    private final BackupTourDtoMapper backupTourDtoMapper;
    private final BackupTourLogDtoMapper backupTourLogDtoMapper;

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

    @Override
    @Transactional(readOnly = false)
    public void importTour(BackupTourDto backupTourDto) {
        log.debug("Trying to import tour {}", backupTourDto);
        Objects.requireNonNull(backupTourDto, "backupTourDto must not be null");

        if (tourRepository.existsTourByName(backupTourDto.name())) {
            throw new IllegalArgumentException("Tour with name %s already exists!"
                    .formatted(backupTourDto.name()));
        }

        Tour tour = backupTourDtoMapper.toDomainObject(backupTourDto);

        List<TourLog> tourLogs = backupTourDto.tourLogs().stream().map(backupTourLogDto ->
            backupTourLogDtoMapper.toDomainObject(tour, backupTourLogDto)
        ).toList();

        tourRepository.save(tour);
        tourLogs.forEach(tourLogRepository::save);
        log.info("Restored tour {} with {} logs", tour, tourLogs.size());
    }
}
