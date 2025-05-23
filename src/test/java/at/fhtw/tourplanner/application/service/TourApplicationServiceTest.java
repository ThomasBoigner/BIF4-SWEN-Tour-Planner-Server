package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.Address;
import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TourRepository;
import at.fhtw.tourplanner.domain.model.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TourApplicationServiceTest {
    private TourApplicationService tourService;
    @Mock
    private TourRepository tourRepository;
    private Tour tour;

    @BeforeEach
    void setUp() {
        tourService = new TourApplicationService(tourRepository);

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
                .imageUrl("img")
                .build();
    }

    @Test
    void ensureGetToursWorksProperly() {
        // Given
        when(tourRepository.findAll()).thenReturn(List.of(tour));

        // When
        List<TourDto> tours = tourService.getTours();

        // Then
        assertThat(tours).contains(new TourDto(tour));
    }

    @Test
    void ensureGetTourWorksProperly(){
        // Given
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.of(tour));

        // When
        Optional<TourDto> tourDto = tourService.getTour(tour.getId());

        // Then
        assertThat(tourDto.isPresent()).isTrue();
        assertThat(tourDto.get()).isEqualTo(new TourDto(tour));
    }

    @Test
    void ensureGetTourWorksProperlyWhenTourCanNotBeFound(){
        // Given
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.empty());

        // When
        Optional<TourDto> tourDto = tourService.getTour(tour.getId());

        // Then
        assertThat(tourDto.isPresent()).isFalse();
    }
}
