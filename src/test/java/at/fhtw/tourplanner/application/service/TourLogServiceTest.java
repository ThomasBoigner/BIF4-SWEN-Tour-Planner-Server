package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import at.fhtw.tourplanner.application.service.mappers.DurationDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.TourLogDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TourLogServiceTest {
    private TourLogService tourLogService;
    private TourLogDtoMapper tourLogDtoMapper;
    @Mock
    private TourLogRepository tourLogRepository;
    @Mock
    private TourRepository tourRepository;
    private Tour tour;
    private TourLog tourLog;

    @BeforeEach
    void setUp() {
        tourLogDtoMapper = new TourLogDtoMapper(new DurationDtoMapper());
        tourLogService = new TourLogServiceImpl(tourLogRepository, tourRepository, tourLogDtoMapper);

        tour = Tour.builder()
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
                .build();

        tourLog = TourLog.builder()
                .tour(tour)
                .duration(Duration.builder()
                        .startTime(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                        .endTime(LocalDateTime.of(2025, 1, 1, 13, 0, 0))
                        .build())
                .comment("What a nice tour!")
                .difficulty(new Difficulty(3))
                .distance(2)
                .rating(new Rating(5))
                .build();
    }

    @Test
    void ensureGetTourLogsOfTourWorksProperly() {
        // Given
        when(tourLogRepository.findAllByTourId(eq(tour.getId()))).thenReturn(List.of(tourLog));

        // When
        List<TourLogDto> tourLogs = tourLogService.getTourLogsOfTour(tour.getId());

        // Then
        assertThat(tourLogs).contains(tourLogDtoMapper.toDto(tourLog));
    }
}
