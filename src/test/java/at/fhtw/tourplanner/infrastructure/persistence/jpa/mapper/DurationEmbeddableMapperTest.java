package at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper;

import at.fhtw.tourplanner.domain.model.Duration;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.DurationEmbeddable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DurationEmbeddableMapperTest {
    private DurationEmbeddableMapper durationEmbeddableMapper;

    @BeforeEach
    void setUp() {
        durationEmbeddableMapper = new DurationEmbeddableMapper();
    }

    @Test
    void ensureDurationEmbeddableMapperToDomainObjectWorksProperly() {
        // Given
        DurationEmbeddable durationEmbeddable = DurationEmbeddable.builder()
                .startTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .endTime(LocalDateTime.of(2020, 1, 1, 1, 0, 0))
                .build();

        // When
        Duration duration = durationEmbeddableMapper.toDomainObject(durationEmbeddable);

        // Then
        assertThat(duration.startTime()).isEqualTo(durationEmbeddable.getStartTime());
        assertThat(duration.endTime()).isEqualTo(durationEmbeddable.getEndTime());
    }

    @Test
    void ensureDurationEmbeddableMapperToEntityWorksProperly() {
        // Given
        Duration duration = Duration.builder()
                .startTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .endTime(LocalDateTime.of(2020, 1, 1, 1, 0, 0))
                .build();

        // When
        DurationEmbeddable durationEmbeddable = durationEmbeddableMapper.toEntity(duration);

        // Then
        assertThat(durationEmbeddable.getStartTime()).isEqualTo(duration.startTime());
        assertThat(durationEmbeddable.getEndTime()).isEqualTo(duration.endTime());
    }
}
