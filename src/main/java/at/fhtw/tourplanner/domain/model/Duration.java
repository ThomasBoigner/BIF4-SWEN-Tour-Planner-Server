package at.fhtw.tourplanner.domain.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
public record Duration(LocalDateTime startTime, LocalDateTime endTime, long duration) {
    public Duration {
        Objects.requireNonNull(startTime, "startTime must not be null");
        Objects.requireNonNull(endTime, "endTime must not be null");
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("start time must be before end time");
        }
    }

    @Builder
    public Duration(LocalDateTime startTime, LocalDateTime endTime) {
        this(startTime,
                endTime,
                startTime != null && endTime != null
                        ? java.time.Duration.between(startTime, endTime).toMinutes()
                        : 0
        );
        log.debug("Created duration: {}", this);
    }
}
