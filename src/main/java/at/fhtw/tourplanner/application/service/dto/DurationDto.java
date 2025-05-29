package at.fhtw.tourplanner.application.service.dto;

import at.fhtw.tourplanner.domain.model.Duration;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public record DurationDto(LocalDateTime startTime, LocalDateTime endTime, long duration) {
    public DurationDto(Duration duration){
        this(duration.startTime(),
                duration.endTime(),
                duration.duration()
        );
        log.debug("Created TourDto {}", this);
    }
}
