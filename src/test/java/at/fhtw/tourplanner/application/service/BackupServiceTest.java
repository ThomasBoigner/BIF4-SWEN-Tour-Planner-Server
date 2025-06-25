package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.BackupTourDto;
import at.fhtw.tourplanner.application.service.mappers.AddressDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.BackupTourDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.BackupTourLogDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.DurationDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BackupServiceTest {
    private BackupService backupService;
    @Mock
    private TourRepository tourRepository;
    @Mock
    private TourLogRepository tourLogRepository;
    private BackupTourDtoMapper backupTourDtoMapper;
    private Tour tour;
    private TourLog tourLog;

    @BeforeEach
    void setUp() {
        backupTourDtoMapper = new BackupTourDtoMapper(
                new AddressDtoMapper(),
                new BackupTourLogDtoMapper(new DurationDtoMapper())
        );

        backupService = new BackupServiceImpl(
                tourRepository,
                tourLogRepository,
                backupTourDtoMapper
        );

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
                        .latitude(50)
                        .longitude(60)
                        .build())
                .to(Address.builder()
                        .streetName("Austria")
                        .city("Strasshof an der Nordbahn")
                        .zipCode(2231)
                        .streetName("Billroth-Gasse")
                        .streetNumber("5")
                        .country("Austria")
                        .latitude(70)
                        .longitude(80)
                        .build())
                .transportType(TransportType.BIKE)
                .distance(20)
                .estimatedTime(120)
                .build();

        tourLog = TourLog.builder()
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
    }

    @Test
    void ensureExportTourWorksProperly() {
        // Given
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.of(tour));
        when(tourLogRepository.findAllByTourId(eq(tour.getId()))).thenReturn(List.of(tourLog));

        // When
        Optional<BackupTourDto> backupTourDto = backupService.exportTour(tour.getId());

        // Then
        assertThat(backupTourDto.isPresent()).isTrue();
        assertThat(backupTourDto.get()).isEqualTo(backupTourDtoMapper.toDto(tour, List.of(tourLog)));
    }

    @Test
    void ensureExportTourWorksProperlyWhenTourCanNotBeFound() {
        // Given
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.empty());
        when(tourLogRepository.findAllByTourId(eq(tour.getId()))).thenReturn(List.of());

        // When
        Optional<BackupTourDto> backupTourDto = backupService.exportTour(tour.getId());

        // Then
        assertThat(backupTourDto.isEmpty()).isTrue();
    }
}
