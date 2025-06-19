package at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper;

import at.fhtw.tourplanner.domain.model.Address;
import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TransportType;
import at.fhtw.tourplanner.domain.util.Page;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.AddressEmbeddable;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TourEntityMapperTest {
    private TourEntityMapper tourEntityMapper;

    @BeforeEach
    void setUp() {
        tourEntityMapper = new TourEntityMapper(new AddressEmbeddableMapper());
    }

    @Test
    void ensureTourEntityMapperToDomainObjectWorksProperly(){
        // Given
        TourEntity tourEntity = TourEntity.builder()
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
                .build();

        // When
        Tour tour = tourEntityMapper.toDomainObject(tourEntity);

        // Then
        assertThat(tour.getId().id()).isEqualTo(tourEntity.getId());
        assertThat(tour.getName()).isEqualTo(tourEntity.getName());
        assertThat(tour.getDescription()).isEqualTo(tourEntity.getDescription());
        assertThat(tour.getFrom().streetName()).isEqualTo(tourEntity.getFrom().getStreetName());
        assertThat(tour.getFrom().city()).isEqualTo(tourEntity.getFrom().getCity());
        assertThat(tour.getFrom().zipCode()).isEqualTo(tourEntity.getFrom().getZipCode());
        assertThat(tour.getFrom().streetName()).isEqualTo(tourEntity.getFrom().getStreetName());
        assertThat(tour.getFrom().streetNumber()).isEqualTo(tourEntity.getFrom().getStreetNumber());
        assertThat(tour.getFrom().country()).isEqualTo(tourEntity.getFrom().getCountry());
        assertThat(tour.getTo().streetName()).isEqualTo(tourEntity.getTo().getStreetName());
        assertThat(tour.getTo().city()).isEqualTo(tourEntity.getTo().getCity());
        assertThat(tour.getTo().zipCode()).isEqualTo(tourEntity.getTo().getZipCode());
        assertThat(tour.getTo().streetName()).isEqualTo(tourEntity.getTo().getStreetName());
        assertThat(tour.getTo().streetNumber()).isEqualTo(tourEntity.getTo().getStreetNumber());
        assertThat(tour.getTo().country()).isEqualTo(tourEntity.getTo().getCountry());
        assertThat(tour.getTransportType()).isEqualTo(tourEntity.getTransportType());
        assertThat(tour.getDistance()).isEqualTo(tourEntity.getDistance());
        assertThat(tour.getEstimatedTime()).isEqualTo(tourEntity.getEstimatedTime());
    }

    @Test
    void ensureTourEntityMapperToEntityWorksProperly() {
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

        // When
        TourEntity tourEntity = tourEntityMapper.toEntity(tour);

        // Then
        assertThat(tour.getId().id()).isEqualTo(tourEntity.getId());
        assertThat(tour.getName()).isEqualTo(tourEntity.getName());
        assertThat(tour.getDescription()).isEqualTo(tourEntity.getDescription());
        assertThat(tour.getFrom().streetName()).isEqualTo(tourEntity.getFrom().getStreetName());
        assertThat(tour.getFrom().city()).isEqualTo(tourEntity.getFrom().getCity());
        assertThat(tour.getFrom().zipCode()).isEqualTo(tourEntity.getFrom().getZipCode());
        assertThat(tour.getFrom().streetName()).isEqualTo(tourEntity.getFrom().getStreetName());
        assertThat(tour.getFrom().streetNumber()).isEqualTo(tourEntity.getFrom().getStreetNumber());
        assertThat(tour.getFrom().country()).isEqualTo(tourEntity.getFrom().getCountry());
        assertThat(tour.getTo().streetName()).isEqualTo(tourEntity.getTo().getStreetName());
        assertThat(tour.getTo().city()).isEqualTo(tourEntity.getTo().getCity());
        assertThat(tour.getTo().zipCode()).isEqualTo(tourEntity.getTo().getZipCode());
        assertThat(tour.getTo().streetName()).isEqualTo(tourEntity.getTo().getStreetName());
        assertThat(tour.getTo().streetNumber()).isEqualTo(tourEntity.getTo().getStreetNumber());
        assertThat(tour.getTo().country()).isEqualTo(tourEntity.getTo().getCountry());
        assertThat(tour.getTransportType()).isEqualTo(tourEntity.getTransportType());
        assertThat(tour.getDistance()).isEqualTo(tourEntity.getDistance());
        assertThat(tour.getEstimatedTime()).isEqualTo(tourEntity.getEstimatedTime());
    }

    @Test
    void ensureToDomainPageWorksProperly() {
        // Given
        TourEntity tourEntity = TourEntity.builder()
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
                .build();

        org.springframework.data.domain.Page<TourEntity> entityPage = new PageImpl<TourEntity>(List.of(tourEntity));

        // When
        Page<Tour> tourPage = tourEntityMapper.toDomainPage(entityPage);

        // Then
        assertThat(tourPage.getContent()).contains(tourEntityMapper.toDomainObject(tourEntity));
        assertThat(tourPage.isLast()).isEqualTo(entityPage.isLast());
        assertThat(tourPage.getTotalPages()).isEqualTo(entityPage.getTotalPages());
        assertThat(tourPage.getTotalElements()).isEqualTo(entityPage.getTotalElements());
        assertThat(tourPage.isFirst()).isEqualTo(entityPage.isFirst());
        assertThat(tourPage.getSize()).isEqualTo(entityPage.getSize());
        assertThat(tourPage.getNumber()).isEqualTo(entityPage.getNumber());
        assertThat(tourPage.getNumberOfElements()).isEqualTo(entityPage.getNumberOfElements());
        assertThat(tourPage.isEmpty()).isEqualTo(entityPage.isEmpty());
    }
}
