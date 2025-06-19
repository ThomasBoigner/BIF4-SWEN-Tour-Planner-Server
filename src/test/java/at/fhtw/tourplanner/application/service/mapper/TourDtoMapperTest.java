package at.fhtw.tourplanner.application.service.mapper;

import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.application.service.mappers.AddressDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.TourDtoMapper;
import at.fhtw.tourplanner.domain.model.Address;
import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TransportType;
import at.fhtw.tourplanner.domain.util.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TourDtoMapperTest {
    private TourDtoMapper tourDtoMapper;

    @BeforeEach
    void setUp() {
        tourDtoMapper = new TourDtoMapper(new AddressDtoMapper());
    }

    @Test
    void ensureTourDtoMapperToDtoWorksProperly() {
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

        // When
        TourDto tourDto = tourDtoMapper.toDto(tour);

        // Then
        assertThat(tour.getId().id()).isEqualTo(tourDto.id());
        assertThat(tour.getName()).isEqualTo(tourDto.name());
        assertThat(tour.getDescription()).isEqualTo(tourDto.description());
        assertThat(tour.getFrom().streetName()).isEqualTo(tourDto.from().streetName());
        assertThat(tour.getFrom().city()).isEqualTo(tourDto.from().city());
        assertThat(tour.getFrom().zipCode()).isEqualTo(tourDto.from().zipCode());
        assertThat(tour.getFrom().streetName()).isEqualTo(tourDto.from().streetName());
        assertThat(tour.getFrom().streetNumber()).isEqualTo(tourDto.from().streetNumber());
        assertThat(tour.getFrom().country()).isEqualTo(tourDto.from().country());
        assertThat(tour.getFrom().latitude()).isEqualTo(tourDto.from().latitude());
        assertThat(tour.getFrom().longitude()).isEqualTo(tourDto.from().longitude());
        assertThat(tour.getTo().streetName()).isEqualTo(tourDto.to().streetName());
        assertThat(tour.getTo().city()).isEqualTo(tourDto.to().city());
        assertThat(tour.getTo().zipCode()).isEqualTo(tourDto.to().zipCode());
        assertThat(tour.getTo().streetName()).isEqualTo(tourDto.to().streetName());
        assertThat(tour.getTo().streetNumber()).isEqualTo(tourDto.to().streetNumber());
        assertThat(tour.getTo().country()).isEqualTo(tourDto.to().country());
        assertThat(tour.getTo().latitude()).isEqualTo(tourDto.to().latitude());
        assertThat(tour.getTo().longitude()).isEqualTo(tourDto.to().longitude());
        assertThat(tour.getDistance()).isEqualTo(tourDto.distance());
        assertThat(tour.getEstimatedTime()).isEqualTo(tourDto.estimatedTime());
    }

    @Test
    void ensureToDtoPageWorksProperly() {
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

        Page<Tour> tourPage = Page.<Tour>builder()
                .content(List.of(tour))
                .last(false)
                .totalPages(2)
                .totalElements(2)
                .first(true)
                .size(1)
                .number(0)
                .numberOfElements(2)
                .empty(false)
                .build();

        // When
        Page<TourDto> tourDtoPage = tourDtoMapper.toDtoPage(tourPage);

        // Then
        assertThat(tourDtoPage.getContent()).contains(tourDtoMapper.toDto(tour));
        assertThat(tourDtoPage.isLast()).isEqualTo(tourPage.isLast());
        assertThat(tourDtoPage.getTotalPages()).isEqualTo(tourPage.getTotalPages());
        assertThat(tourDtoPage.getTotalElements()).isEqualTo(tourPage.getTotalElements());
        assertThat(tourDtoPage.isFirst()).isEqualTo(tourPage.isFirst());
        assertThat(tourDtoPage.getSize()).isEqualTo(tourPage.getSize());
        assertThat(tourDtoPage.getNumber()).isEqualTo(tourPage.getNumber());
        assertThat(tourDtoPage.getNumberOfElements()).isEqualTo(tourPage.getNumberOfElements());
        assertThat(tourDtoPage.isEmpty()).isEqualTo(tourPage.isEmpty());
    }
}
