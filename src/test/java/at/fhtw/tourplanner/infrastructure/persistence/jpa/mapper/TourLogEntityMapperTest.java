package at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper;

import at.fhtw.tourplanner.domain.model.*;
import at.fhtw.tourplanner.domain.util.Page;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.AddressEmbeddable;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.DurationEmbeddable;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourEntity;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourLogEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TourLogEntityMapperTest {
    private TourLogEntityMapper tourLogEntityMapper;

    @BeforeEach
    void setUp() {
        tourLogEntityMapper = new TourLogEntityMapper(
                new TourEntityMapper(new AddressEmbeddableMapper()),
                new DurationEmbeddableMapper());
    }

    @Test
    void ensureTourLogEntityMapperToDomainObjectWorksProperly(){
        // Given
        TourLogEntity tourLogEntity = TourLogEntity.builder()
                .id(UUID.randomUUID())
                .tour(TourEntity.builder()
                        .id(UUID.randomUUID())
                        .name("Tour 1")
                        .description("This tour is awesome")
                        .from(AddressEmbeddable.builder()
                                .streetName("Austria")
                                .city("Deutsch Wagram")
                                .zipCode(2232)
                                .streetName("Radetzkystraße")
                                .streetNumber("2-6")
                                .country("Austria")
                                .build())
                        .to(AddressEmbeddable.builder()
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
                .duration(DurationEmbeddable.builder()
                        .startTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                        .endTime(LocalDateTime.of(2020, 1, 1, 1, 0, 0))
                        .build())
                .comment("What a nice tour!")
                .difficulty(3)
                .distance(2)
                .rating(5)
                .build();

        // When
        TourLog tourLog = tourLogEntityMapper.toDomainObject(tourLogEntity);

        // Then
        assertThat(tourLog.getId().id()).isEqualTo(tourLogEntity.getId());
        assertThat(tourLog.getTour().getId().id()).isEqualTo(tourLogEntity.getTour().getId());
        assertThat(tourLog.getDuration().startTime())
                .isEqualTo(tourLogEntity.getDuration().getStartTime());
        assertThat(tourLog.getDuration().endTime())
                .isEqualTo(tourLogEntity.getDuration().getEndTime());
        assertThat(tourLog.getComment()).isEqualTo(tourLogEntity.getComment());
        assertThat(tourLog.getDifficulty().difficulty()).isEqualTo(tourLogEntity.getDifficulty());
        assertThat(tourLog.getDistance()).isEqualTo(tourLogEntity.getDistance());
        assertThat(tourLog.getRating().rating()).isEqualTo(tourLogEntity.getRating());
    }

    @Test
    void ensureTourLogEntityMapperToEntityWorksProperly(){
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
        TourLogEntity tourLogEntity = tourLogEntityMapper.toEntity(tourLog);

        // Then
        assertThat(tourLog.getId().id()).isEqualTo(tourLogEntity.getId());
        assertThat(tourLog.getTour().getId().id()).isEqualTo(tourLogEntity.getTour().getId());
        assertThat(tourLog.getDuration().startTime())
                .isEqualTo(tourLogEntity.getDuration().getStartTime());
        assertThat(tourLog.getDuration().endTime())
                .isEqualTo(tourLogEntity.getDuration().getEndTime());
        assertThat(tourLog.getComment()).isEqualTo(tourLogEntity.getComment());
        assertThat(tourLog.getDifficulty().difficulty()).isEqualTo(tourLogEntity.getDifficulty());
        assertThat(tourLog.getDistance()).isEqualTo(tourLogEntity.getDistance());
        assertThat(tourLog.getRating().rating()).isEqualTo(tourLogEntity.getRating());
    }

    @Test
    void ensureToDomainPageWorksProperly(){
        // Given
        TourLogEntity tourLogEntity = TourLogEntity.builder()
                .id(UUID.randomUUID())
                .tour(TourEntity.builder()
                        .id(UUID.randomUUID())
                        .name("Tour 1")
                        .description("This tour is awesome")
                        .from(AddressEmbeddable.builder()
                                .streetName("Austria")
                                .city("Deutsch Wagram")
                                .zipCode(2232)
                                .streetName("Radetzkystraße")
                                .streetNumber("2-6")
                                .country("Austria")
                                .build())
                        .to(AddressEmbeddable.builder()
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
                .duration(DurationEmbeddable.builder()
                        .startTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                        .endTime(LocalDateTime.of(2020, 1, 1, 1, 0, 0))
                        .build())
                .comment("What a nice tour!")
                .difficulty(3)
                .distance(2)
                .rating(5)
                .build();

        org.springframework.data.domain.Page<TourLogEntity> entityPage =
                new PageImpl<TourLogEntity>(List.of(tourLogEntity));

        // When
        Page<TourLog> tourLogPage = tourLogEntityMapper.toDomainPage(entityPage);

        // Then
        assertThat(tourLogPage.getContent()).contains(
                tourLogEntityMapper.toDomainObject(tourLogEntity)
        );
        assertThat(tourLogPage.isLast()).isEqualTo(entityPage.isLast());
        assertThat(tourLogPage.getTotalPages()).isEqualTo(entityPage.getTotalPages());
        assertThat(tourLogPage.getTotalElements()).isEqualTo(entityPage.getTotalElements());
        assertThat(tourLogPage.isFirst()).isEqualTo(entityPage.isFirst());
        assertThat(tourLogPage.getSize()).isEqualTo(entityPage.getSize());
        assertThat(tourLogPage.getNumber()).isEqualTo(entityPage.getNumber());
        assertThat(tourLogPage.getNumberOfElements()).isEqualTo(entityPage.getNumberOfElements());
        assertThat(tourLogPage.isEmpty()).isEqualTo(entityPage.isEmpty());
    }
}
