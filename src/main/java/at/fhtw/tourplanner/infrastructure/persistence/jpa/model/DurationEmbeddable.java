package at.fhtw.tourplanner.infrastructure.persistence.jpa.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Embeddable
public class DurationEmbeddable {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
