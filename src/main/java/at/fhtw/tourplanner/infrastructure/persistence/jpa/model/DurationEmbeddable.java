package at.fhtw.tourplanner.infrastructure.persistence.jpa.model;

import at.fhtw.tourplanner.domain.model.Duration;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

@Embeddable
public class DurationEmbeddable {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public DurationEmbeddable(Duration duration) {
        this.startTime = duration.startTime();
        this.endTime = duration.endTime();
    }

    public Duration toDuration() {
        return Duration.builder()
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
