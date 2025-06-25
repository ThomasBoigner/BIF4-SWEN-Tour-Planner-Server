package at.fhtw.tourplanner.application.service.mapper;

import at.fhtw.tourplanner.application.service.dto.BackupTourDto;
import at.fhtw.tourplanner.application.service.mappers.AddressDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.BackupTourDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.BackupTourLogDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.DurationDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BackupTourDtoMapperTest {
    private BackupTourDtoMapper backupTourDtoMapper;
    private BackupTourLogDtoMapper backupTourLogDtoMapper;

    @BeforeEach
    void setUp() {
        backupTourLogDtoMapper = new BackupTourLogDtoMapper(new DurationDtoMapper());
        backupTourDtoMapper = new BackupTourDtoMapper(new AddressDtoMapper(), backupTourLogDtoMapper);
    }

    @Test
    void ensureBackupTourDtoMapperToDtoWorksProperly() {
        // Given
        Tour tour = Tour.builder()
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
        BackupTourDto backupTourDto = backupTourDtoMapper.toDto(tour, List.of(tourLog));

        // Then
        assertThat(tour.getName()).isEqualTo(backupTourDto.name());
        assertThat(tour.getDescription()).isEqualTo(backupTourDto.description());
        assertThat(tour.getFrom().streetName()).isEqualTo(backupTourDto.from().streetName());
        assertThat(tour.getFrom().city()).isEqualTo(backupTourDto.from().city());
        assertThat(tour.getFrom().zipCode()).isEqualTo(backupTourDto.from().zipCode());
        assertThat(tour.getFrom().streetName()).isEqualTo(backupTourDto.from().streetName());
        assertThat(tour.getFrom().streetNumber()).isEqualTo(backupTourDto.from().streetNumber());
        assertThat(tour.getFrom().country()).isEqualTo(backupTourDto.from().country());
        assertThat(tour.getFrom().latitude()).isEqualTo(backupTourDto.from().latitude());
        assertThat(tour.getFrom().longitude()).isEqualTo(backupTourDto.from().longitude());
        assertThat(tour.getTo().streetName()).isEqualTo(backupTourDto.to().streetName());
        assertThat(tour.getTo().city()).isEqualTo(backupTourDto.to().city());
        assertThat(tour.getTo().zipCode()).isEqualTo(backupTourDto.to().zipCode());
        assertThat(tour.getTo().streetName()).isEqualTo(backupTourDto.to().streetName());
        assertThat(tour.getTo().streetNumber()).isEqualTo(backupTourDto.to().streetNumber());
        assertThat(tour.getTo().country()).isEqualTo(backupTourDto.to().country());
        assertThat(tour.getTo().latitude()).isEqualTo(backupTourDto.to().latitude());
        assertThat(tour.getTo().longitude()).isEqualTo(backupTourDto.to().longitude());
        assertThat(tour.getDistance()).isEqualTo(backupTourDto.distance());
        assertThat(tour.getEstimatedTime()).isEqualTo(backupTourDto.estimatedTime());
        assertThat(backupTourDto.tourLogs()).contains(backupTourLogDtoMapper.toDto(tourLog));
    }
}
