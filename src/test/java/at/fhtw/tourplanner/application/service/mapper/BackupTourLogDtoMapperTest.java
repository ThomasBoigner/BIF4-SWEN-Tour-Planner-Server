package at.fhtw.tourplanner.application.service.mapper;

import at.fhtw.tourplanner.application.service.dto.BackupTourLogDto;
import at.fhtw.tourplanner.application.service.dto.DurationDto;
import at.fhtw.tourplanner.application.service.mappers.BackupTourLogDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.DurationDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class BackupTourLogDtoMapperTest {
    private BackupTourLogDtoMapper backupTourLogDtoMapper;

    @BeforeEach
    void setUp() {
        backupTourLogDtoMapper = new BackupTourLogDtoMapper(new DurationDtoMapper());
    }

    @Test
    void ensureBackupTourLogDtoMapperToDtoWorksProperly() {
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
        BackupTourLogDto backupTourLogDto = backupTourLogDtoMapper.toDto(tourLog);

        // Then
        assertThat(tourLog.getDuration().startTime())
                .isEqualTo(backupTourLogDto.duration().startTime());
        assertThat(tourLog.getDuration().endTime())
                .isEqualTo(backupTourLogDto.duration().endTime());
        assertThat(tourLog.getDuration().duration())
                .isEqualTo(backupTourLogDto.duration().duration());
        assertThat(tourLog.getComment()).isEqualTo(backupTourLogDto.comment());
        assertThat(tourLog.getDifficulty().difficulty()).isEqualTo(backupTourLogDto.difficulty());
        assertThat(tourLog.getDistance()).isEqualTo(backupTourLogDto.distance());
        assertThat(tourLog.getRating().rating()).isEqualTo(backupTourLogDto.rating());
    }

    @Test
    void ensureBackupTourLogToDomainObjectWorksProperly() {
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

        BackupTourLogDto backupTourLogDto = BackupTourLogDto.builder()
                .duration(DurationDto.builder()
                        .startTime(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                        .endTime(LocalDateTime.of(2025, 1, 1, 13, 0, 0))
                        .duration(60)
                        .build())
                .comment("What a nice tour!")
                .difficulty(3)
                .distance(2)
                .rating(5)
                .build();

        // When
        TourLog tourLog = backupTourLogDtoMapper.toDomainObject(tour, backupTourLogDto);

        // Then
        assertThat(tourLog.getTour()).isEqualTo(tour);
        assertThat(tourLog.getDuration().startTime())
                .isEqualTo(backupTourLogDto.duration().startTime());
        assertThat(tourLog.getDuration().endTime())
                .isEqualTo(backupTourLogDto.duration().endTime());
        assertThat(tourLog.getDuration().duration())
                .isEqualTo(backupTourLogDto.duration().duration());
        assertThat(tourLog.getComment()).isEqualTo(backupTourLogDto.comment());
        assertThat(tourLog.getDifficulty().difficulty()).isEqualTo(backupTourLogDto.difficulty());
        assertThat(tourLog.getDistance()).isEqualTo(backupTourLogDto.distance());
        assertThat(tourLog.getRating().rating()).isEqualTo(backupTourLogDto.rating());
    }
}
