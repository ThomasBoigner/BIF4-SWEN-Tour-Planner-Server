package at.fhtw.tourplanner.application.service.dto;

import at.fhtw.tourplanner.domain.model.TourLog;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public record TourLogDto(UUID id,
                         UUID tourId,
                         DurationDto duration,
                         String comment,
                         int difficulty,
                         double distance,
                         int rating) {
    public TourLogDto(TourLog tourLog) {
        this(tourLog.getId().id(),
                tourLog.getTour().getId().id(),
                new DurationDto(tourLog.getDuration()),
                tourLog.getComment(),
                tourLog.getDifficulty().difficulty(),
                tourLog.getDistance(),
                tourLog.getRating().rating()
        );
        log.debug("Created TourDto {}", this);
    }
}
