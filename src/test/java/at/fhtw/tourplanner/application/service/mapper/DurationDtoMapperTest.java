package at.fhtw.tourplanner.application.service.mapper;

import at.fhtw.tourplanner.application.service.dto.DurationDto;
import at.fhtw.tourplanner.application.service.mappers.DurationDtoMapper;
import at.fhtw.tourplanner.domain.model.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DurationDtoMapperTest {
    private DurationDtoMapper durationDtoMapper;

    @BeforeEach
    void setUp() {
        durationDtoMapper = new DurationDtoMapper();
    }

    @Test
    void ensureDurationDtoMapperToDtoWorksProperly() {
        // Given
        Duration duration = Duration.builder()
                .startTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .endTime(LocalDateTime.of(2020, 1, 1, 1, 0, 0))
                .build();

        // When
        DurationDto durationDto = durationDtoMapper.toDto(duration);

        // Then
        assertThat(durationDto.startTime()).isEqualTo(duration.startTime());
        assertThat(durationDto.endTime()).isEqualTo(duration.endTime());
        assertThat(durationDto.duration()).isEqualTo(duration.duration());
    }
}
