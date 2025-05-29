package at.fhtw.tourplanner.domain.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Builder
public record Duration(LocalDateTime startTime, LocalDateTime endTime, long duration) {
    public Duration {
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }

    public Duration(LocalDateTime startTime, LocalDateTime endTime) {
        this(startTime, endTime, java.time.Duration.between(startTime, endTime).toMinutes());
        log.debug("Created duration: {}", this);
    }
}
