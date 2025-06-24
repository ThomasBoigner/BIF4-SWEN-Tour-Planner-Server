package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.commands.CreateTourLogCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourLogCommand;
import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import at.fhtw.tourplanner.application.service.mappers.TourLogDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
import at.fhtw.tourplanner.domain.util.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor

@Slf4j
@Service
@Transactional(readOnly = true)
public class TourLogServiceImpl implements TourLogService{
    private final TourLogRepository tourLogRepository;
    private final TourRepository tourRepository;
    private final TourLogDtoMapper tourLogDtoMapper;

    @Override
    public Page<TourLogDto> getTourLogsOfTour(TourId tourId, int page, int size) {
        log.debug(
                "Trying to get all tour logs of tour with id {} on page {} with size {}",
                tourId.id(),
                page,
                size);

        Page<TourLogDto> tourLogs = tourLogDtoMapper.toDtoPage(tourLogRepository
                .findAllByTourId(tourId, page, size, "rating"));

        log.info(
                "Retrieved {} tour logs of tour with id {}",
                tourLogs.getContent().size(),
                tourId.id()
        );
        return  tourLogs;
    }

    @Override
    public Page<TourLogDto> getTourLogsOfTourByComment(TourId tourId, String comment, int page, int size) {
        log.debug(
                "Trying to get all tour logs of tour with id {} with comment like {} on page {} with size {}",
                tourId.id(),
                comment,
                page,
                size
        );
        Page<TourLogDto> tourLogs = tourLogDtoMapper.toDtoPage(tourLogRepository
                .findAllByTourIdAndCommentLike(tourId, comment, page, size, "rating"));

        log.info(
                "Retrieved {} tour logs of tour with id {} with comment like {}",
                tourLogs.getContent().size(),
                tourId.id(),
                comment);
        return tourLogs;
    }

    @Override
    @Transactional(readOnly = false)
    public TourLogDto createTourLog(CreateTourLogCommand command) {
        log.debug("Trying to create tourLog with command {}", command);
        Objects.requireNonNull(command, "command must not be null!");

        Tour tour = tourRepository.findTourById(new TourId(command.tourId()))
                .orElseThrow(() -> new IllegalArgumentException("Tour not found"));

        Duration duration = new Duration(command.startTime(), command.endTime());
        Difficulty difficulty = new Difficulty(command.difficulty());
        Rating rating = new Rating(command.rating());

        TourLog tourLog = TourLog.builder()
                .tour(tour)
                .comment(command.comment())
                .difficulty(difficulty)
                .rating(rating)
                .distance(command.distance())
                .duration(duration)
                .build();

        tourLogRepository.save(tourLog);
        log.info("Created tourLog {}", tourLog);
        return tourLogDtoMapper.toDto(tourLog);
    }

    @Override
    @Transactional(readOnly = false)
    public TourLogDto updateTourLog(TourLogId tourLogId, UpdateTourLogCommand command) {
        log.debug("Trying to update tourLog with id {} and command {}", tourLogId.id(), command);
        Objects.requireNonNull(command, "command must not be null!");
        Objects.requireNonNull(tourLogId, "id must not be null!");

        Optional<TourLog> entity = tourLogRepository.findTourEntityById(tourLogId);

        if (entity.isEmpty()) {
            log.warn("TourLog with id {} can not be found!", tourLogId.id());
            throw new IllegalArgumentException(
                    "Tour with id %s can not be found!".formatted(tourLogId.id()));
        }

        TourLog tourLog = entity.get();

        tourLog.setComment(command.comment());
        tourLog.setDistance(command.distance());
        tourLog.setDifficulty(Difficulty.builder()
            .difficulty(command.difficulty()).build()
        );
        tourLog.setRating(Rating.builder()
                .rating(command.rating()).build()
        );
        tourLog.setDuration(Duration.builder()
                .startTime(command.startTime())
                .endTime(command.endTime())
                .build()
        );

        log.info("Successfully updated tour Log {}", tourLog);
        return tourLogDtoMapper.toDto(tourLogRepository.save(tourLog));
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteTourLog(TourLogId id) {
        tourLogRepository.deleteTourLogById(id);
        log.info("Successfully deleted tour with id {}", id.id());
    }
}
