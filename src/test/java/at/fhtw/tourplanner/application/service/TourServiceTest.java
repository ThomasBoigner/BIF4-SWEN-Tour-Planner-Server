package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.commands.CreateAddressCommand;
import at.fhtw.tourplanner.application.service.commands.CreateTourCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourCommand;
import at.fhtw.tourplanner.application.service.dto.CoordinateDto;
import at.fhtw.tourplanner.application.service.dto.RouteInformationDto;
import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.application.service.mappers.AddressDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.TourDtoMapper;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TourServiceTest {
    private TourService tourService;
    private TourDtoMapper tourDtoMapper;
    @Mock
    private TourRepository tourRepository;
    @Mock
    private GeocodeSearchService geocodeSearchService;
    @Mock
    private RouteService routeService;
    private Tour tour;

    @BeforeEach
    void setUp() {
        tourDtoMapper = new TourDtoMapper(new AddressDtoMapper());
        tourService = new TourServiceImpl(
                tourRepository,
                tourDtoMapper,
                geocodeSearchService,
                routeService
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
    }

    @Test
    void ensureGetToursWorksProperly() {
        // Given
        when(tourRepository.findAll()).thenReturn(List.of(tour));

        // When
        List<TourDto> tours = tourService.getTours();

        // Then
        assertThat(tours).contains(tourDtoMapper.toDto(tour));
    }

    @Test
    void ensureGetTourWorksProperly(){
        // Given
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.of(tour));

        // When
        Optional<TourDto> tourDto = tourService.getTour(tour.getId());

        // Then
        assertThat(tourDto.isPresent()).isTrue();
        assertThat(tourDto.get()).isEqualTo(tourDtoMapper.toDto(tour));
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

    @Test
    void ensureCreateTourWorksProperly(){
        // Given
        CreateTourCommand command = CreateTourCommand.builder()
                .name("Tour 1")
                .description("This tour is awesome")
                .from(CreateAddressCommand.builder()
                        .city("Deutsch Wagram")
                        .zipCode(2232)
                        .streetName("Radetzkystraße")
                        .streetNumber("2-6")
                        .country("Austria")
                        .build())
                .to(CreateAddressCommand.builder()
                        .city("Strasshof an der Nordbahn")
                        .zipCode(2231)
                        .streetName("Billroth-Gasse")
                        .streetNumber("5")
                        .country("Austria")
                        .build())
                .transportType(TransportType.BIKE)
                .build();
        when(tourRepository.existsTourByName(eq(command.name()))).thenReturn(false);
        when(geocodeSearchService.getCoordinates(
                eq("Radetzkystraße 2-6 2232 Deutsch Wagram Austria")))
                .thenReturn(new CoordinateDto(40, 50));
        when(geocodeSearchService.getCoordinates(
                eq("Billroth-Gasse 5 2231 Strasshof an der Nordbahn Austria")))
                .thenReturn(new CoordinateDto(20, 30));
        when(routeService.getRouteInformation(
                eq(40d), eq(50d), eq(20d), eq(30d), eq(command.transportType())))
                .thenReturn(new RouteInformationDto(10, 20));
        when(tourRepository.save(any(Tour.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        // When
        TourDto tourDto = tourService.createTour(command);

        // Then
        assertThat(tourDto).isNotNull();
        assertThat(tourDto.id()).isNotNull();
        assertThat(tourDto.name()).isEqualTo(command.name());
        assertThat(tourDto.description()).isEqualTo(command.description());
        assertThat(tourDto.from().streetName()).isEqualTo(command.from().streetName());
        assertThat(tourDto.from().streetNumber()).isEqualTo(command.from().streetNumber());
        assertThat(tourDto.from().city()).isEqualTo(command.from().city());
        assertThat(tourDto.from().zipCode()).isEqualTo(command.from().zipCode());
        assertThat(tourDto.from().country()).isEqualTo(command.from().country());
        assertThat(tourDto.from().latitude()).isEqualTo(40);
        assertThat(tourDto.from().longitude()).isEqualTo(50);
        assertThat(tourDto.to().streetName()).isEqualTo(command.to().streetName());
        assertThat(tourDto.to().streetNumber()).isEqualTo(command.to().streetNumber());
        assertThat(tourDto.to().city()).isEqualTo(command.to().city());
        assertThat(tourDto.to().zipCode()).isEqualTo(command.to().zipCode());
        assertThat(tourDto.to().country()).isEqualTo(command.to().country());
        assertThat(tourDto.to().latitude()).isEqualTo(20);
        assertThat(tourDto.to().longitude()).isEqualTo(30);
        assertThat(tourDto.transportType()).isEqualTo(command.transportType());
        assertThat(tourDto.distance()).isEqualTo(10);
        assertThat(tourDto.estimatedTime()).isEqualTo(20);
    }

    @Test
    void ensureCreateTourThrowsExceptionWhenTourWithNameAlreadyExists() {
        // Given
        CreateTourCommand command = CreateTourCommand.builder()
                .name("Tour 1")
                .description("This tour is awesome")
                .from(CreateAddressCommand.builder()
                        .city("Deutsch Wagram")
                        .zipCode(2232)
                        .streetName("Radetzkystraße")
                        .streetNumber("2-6")
                        .country("Austria")
                        .build())
                .to(CreateAddressCommand.builder()
                        .city("Strasshof an der Nordbahn")
                        .zipCode(2231)
                        .streetName("Billroth-Gasse")
                        .streetNumber("5")
                        .country("Austria")
                        .build())
                .transportType(TransportType.BIKE)
                .build();
        when(tourRepository.existsTourByName(eq(command.name()))).thenReturn(true);

        // When
        assertThrows(IllegalArgumentException.class, () -> tourService.createTour(command));
    }

    @Test
    void ensureUpdateTourWorksProperly(){
        // Given
        UpdateTourCommand command = UpdateTourCommand.builder()
                .name("new Tour 1")
                .description("new this tour is awesome")
                .from(CreateAddressCommand.builder()
                        .city("new Deutsch Wagram")
                        .zipCode(1234)
                        .streetName("new Radetzkystraße")
                        .streetNumber("10")
                        .country("Germany")
                        .build())
                .to(CreateAddressCommand.builder()
                        .city("new strasshof an der Nordbahn")
                        .zipCode(5678)
                        .streetName("new Billroth-Gasse")
                        .streetNumber("12")
                        .country("Austria")
                        .build())
                .transportType(TransportType.BIKE)
                .build();
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.of(tour));
        when(tourRepository.existsTourByName(eq(command.name()))).thenReturn(false);
        when(geocodeSearchService.getCoordinates(
                eq("new Radetzkystraße 10 1234 new Deutsch Wagram Germany")))
                .thenReturn(new CoordinateDto(40, 50));
        when(geocodeSearchService.getCoordinates(
                eq("new Billroth-Gasse 12 5678 new strasshof an der Nordbahn Austria")))
                .thenReturn(new CoordinateDto(20, 30));
        when(routeService.getRouteInformation(
                eq(40d), eq(50d), eq(20d), eq(30d), eq(command.transportType())))
                .thenReturn(new RouteInformationDto(10, 20));
        when(tourRepository.save(any(Tour.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        // When
        TourDto tourDto = tourService.updateTour(tour.getId(), command);

        // Then
        assertThat(tourDto).isNotNull();
        assertThat(tourDto.id()).isEqualTo(tour.getId().id());
        assertThat(tourDto.name()).isEqualTo(command.name());
        assertThat(tourDto.description()).isEqualTo(command.description());
        assertThat(tourDto.from().streetName()).isEqualTo(command.from().streetName());
        assertThat(tourDto.from().streetNumber()).isEqualTo(command.from().streetNumber());
        assertThat(tourDto.from().city()).isEqualTo(command.from().city());
        assertThat(tourDto.from().zipCode()).isEqualTo(command.from().zipCode());
        assertThat(tourDto.from().country()).isEqualTo(command.from().country());
        assertThat(tourDto.from().latitude()).isEqualTo(40);
        assertThat(tourDto.from().longitude()).isEqualTo(50);
        assertThat(tourDto.to().streetName()).isEqualTo(command.to().streetName());
        assertThat(tourDto.to().streetNumber()).isEqualTo(command.to().streetNumber());
        assertThat(tourDto.to().city()).isEqualTo(command.to().city());
        assertThat(tourDto.to().zipCode()).isEqualTo(command.to().zipCode());
        assertThat(tourDto.to().country()).isEqualTo(command.to().country());
        assertThat(tourDto.to().latitude()).isEqualTo(20);
        assertThat(tourDto.to().longitude()).isEqualTo(30);
        assertThat(tourDto.transportType()).isEqualTo(command.transportType());
        assertThat(tourDto.distance()).isEqualTo(tour.getDistance());
        assertThat(tourDto.estimatedTime()).isEqualTo(tour.getEstimatedTime());
        assertThat(tourDto.distance()).isEqualTo(10);
        assertThat(tourDto.estimatedTime()).isEqualTo(20);
    }

    @Test
    void ensureUpdateTourThrowsExceptionWhenTourCannotBeFound(){
        // Given
        UpdateTourCommand command = UpdateTourCommand.builder()
                .name("new Tour 1")
                .description("new this tour is awesome")
                .from(CreateAddressCommand.builder()
                        .city("new Deutsch Wagram")
                        .zipCode(1234)
                        .streetName("new Radetzkystraße")
                        .streetNumber("10")
                        .country("Germany")
                        .build())
                .to(CreateAddressCommand.builder()
                        .city("new strasshof an der Nordbahn")
                        .zipCode(5678)
                        .streetName("new Billroth-Gasse")
                        .streetNumber("12")
                        .country("Austria")
                        .build())
                .transportType(TransportType.BIKE)
                .build();
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.empty());

        // When
        assertThrows(IllegalArgumentException.class,
                () -> tourService.updateTour(tour.getId(), command));
    }

    @Test
    void ensureUpdateTourThrowsExceptionWhenTourWithNameAlreadyExists() {
        // Given
        UpdateTourCommand command = UpdateTourCommand.builder()
                .name("new Tour 1")
                .description("new this tour is awesome")
                .from(CreateAddressCommand.builder()
                        .city("new Deutsch Wagram")
                        .zipCode(1234)
                        .streetName("new Radetzkystraße")
                        .streetNumber("10")
                        .country("Germany")
                        .build())
                .to(CreateAddressCommand.builder()
                        .city("new strasshof an der Nordbahn")
                        .zipCode(5678)
                        .streetName("new Billroth-Gasse")
                        .streetNumber("12")
                        .country("Austria")
                        .build())
                .transportType(TransportType.BIKE)
                .build();
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.of(tour));
        when(tourRepository.existsTourByName(eq(command.name()))).thenReturn(true);

        // When
        assertThrows(IllegalArgumentException.class,
                () -> tourService.updateTour(tour.getId(), command));
    }

    @Test
    void ensureUpdateTourThrowsNoExceptionWhenNameIsChangedToTheSameName() {
        // Given
        UpdateTourCommand command = UpdateTourCommand.builder()
                .name("Tour 1")
                .description("new this tour is awesome")
                .from(CreateAddressCommand.builder()
                        .city("new Deutsch Wagram")
                        .zipCode(1234)
                        .streetName("new Radetzkystraße")
                        .streetNumber("10")
                        .country("Germany")
                        .build())
                .to(CreateAddressCommand.builder()
                        .city("new strasshof an der Nordbahn")
                        .zipCode(5678)
                        .streetName("new Billroth-Gasse")
                        .streetNumber("12")
                        .country("Austria")
                        .build())
                .transportType(TransportType.BIKE)
                .build();
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.of(tour));
        when(geocodeSearchService.getCoordinates(
                eq("new Radetzkystraße 10 1234 new Deutsch Wagram Germany")))
                .thenReturn(new CoordinateDto(40, 50));
        when(geocodeSearchService.getCoordinates(
                eq("new Billroth-Gasse 12 5678 new strasshof an der Nordbahn Austria")))
                .thenReturn(new CoordinateDto(20, 30));
        when(routeService.getRouteInformation(
                eq(40d), eq(50d), eq(20d), eq(30d), eq(command.transportType())))
                .thenReturn(new RouteInformationDto(10, 20));
        when(tourRepository.save(any(Tour.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        // When
        assertDoesNotThrow(() -> tourService.updateTour(tour.getId(), command));
    }

    @Test
    void ensureDeleteTourWorksProperly(){
        tourService.deleteTour(tour.getId());
    }
}
