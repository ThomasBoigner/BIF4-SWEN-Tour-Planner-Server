package at.fhtw.tourplanner.application.service.mapper;

import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import at.fhtw.tourplanner.application.service.mappers.DurationDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.TourLogDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TourLogDtoMapperTest {
    private TourLogDtoMapper tourLogDtoMapper;

    @BeforeEach
    void setUp() {
        tourLogDtoMapper = new TourLogDtoMapper(new DurationDtoMapper());
    }

    @Test
    void ensureTourLogDtoMapperToDtoWorksProperly() {
        // Given
        TourLog tourLog = TourLog.builder()
                .tour(Tour.builder()
                        .name("Tour 1")
                        .description("This tour is awesome")
                        .from(Address.builder()
                                .streetName("Austria")
                                .city("Deutsch Wagram")
                                .zipCode(2232)
                                .streetName("Radetzkystraße")
                                .streetNumber("2-6")
                                .country("Austria")
                                .build())
                        .to(Address.builder()
                                .streetName("Austria")
                                .city("Strasshof an der Nordbahn")
                                .zipCode(2231)
                                .streetName("Billroth-Gasse")
                                .streetNumber("5")
                                .country("Austria")
                                .build())
                        .transportType(TransportType.BIKE)
                        .distance(20)
                        .estimatedTime(120)
                        .build())
                .duration(Duration.builder()
                        .startTime(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                        .endTime(LocalDateTime.of(2025, 1, 1, 13, 0, 0))
                        .build())
                .comment("What a nice tour!")
                .difficulty(new Difficulty(3))
                .distance(2)
                .rating(new Rating(5))
                .build();

        // When
        TourLogDto tourLogDto = tourLogDtoMapper.toDto(tourLog);

        // Then
        assertThat(tourLog.getId().id()).isEqualTo(tourLogDto.id());
        assertThat(tourLog.getTour().getId().id()).isEqualTo(tourLogDto.tourId());
        assertThat(tourLog.getDuration().startTime()).isEqualTo(tourLogDto.duration().startTime());
        assertThat(tourLog.getDuration().endTime()).isEqualTo(tourLogDto.duration().endTime());
        assertThat(tourLog.getDuration().duration()).isEqualTo(tourLogDto.duration().duration());
        assertThat(tourLog.getComment()).isEqualTo(tourLogDto.comment());
        assertThat(tourLog.getDifficulty().difficulty()).isEqualTo(tourLogDto.difficulty());
        assertThat(tourLog.getDistance()).isEqualTo(tourLogDto.distance());
        assertThat(tourLog.getRating().rating()).isEqualTo(tourLogDto.rating());
    }
}
