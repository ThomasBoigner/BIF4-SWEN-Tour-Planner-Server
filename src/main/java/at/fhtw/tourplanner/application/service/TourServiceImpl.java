package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.commands.CreateTourCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourCommand;
import at.fhtw.tourplanner.application.service.dto.CoordinateDto;
import at.fhtw.tourplanner.application.service.dto.RouteInformationDto;
import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.application.service.mappers.TourDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
import at.fhtw.tourplanner.domain.util.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Slf4j
@Service
@Transactional(readOnly = true)
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final TourDtoMapper tourDtoMapper;
    private final GeocodeSearchService geocodeSearchService;
    private final RouteService routeService;

    @Override
    public Page<TourDto> getTours(int page, int size) {
        log.debug("Trying to get all tours on page {} with size {}", page, size);
        Page<Tour> tours = tourRepository.findAll(page, size, "name");
        Page<TourDto> dtos = Page.<TourDto>builder()
                .content(tourDtoMapper.toDtos(tours.getContent()))
                .last(tours.isLast())
                .totalPages(tours.getTotalPages())
                .totalElements(tours.getTotalElements())
                .first(tours.isFirst())
                .size(tours.getSize())
                .number(tours.getNumber())
                .numberOfElements(tours.getNumberOfElements())
                .empty(tours.isEmpty())
                .build();
        log.info("Retrieved {} tours of all tours", dtos.getContent().size());
        return dtos;
    }

    @Override
    public Page<TourDto> findToursByName(String name, int page, int size) {
        log.debug(
                "Trying to get all tours with name like {} on page {} with size {}",
                name,
                page,
                size
        );
        Page<Tour> tours = tourRepository
                .findAllByNameLike(name, page, size, "name");

        Page<TourDto> dtos = Page.<TourDto>builder()
                .content(tourDtoMapper.toDtos(tours.getContent()))
                .last(tours.isLast())
                .totalPages(tours.getTotalPages())
                .totalElements(tours.getTotalElements())
                .first(tours.isFirst())
                .size(tours.getSize())
                .number(tours.getNumber())
                .numberOfElements(tours.getNumberOfElements())
                .empty(tours.isEmpty())
                .build();
        log.info("Retrieved {} tours with name like {}", tours.getContent().size(), name);
        return dtos;

    }

    @Override
    public Optional<TourDto> getTour(TourId id) {
        log.debug("Trying to get tour with id {}", id);
        Optional<TourDto> tour = tourRepository.findTourById(id).map(tourDtoMapper::toDto);
        tour.ifPresentOrElse(
                t -> log.info("Found tour {} with id {}", t, id.id()),
                () -> log.info("No tour with id {} found", id.id()));
        return tour;
    }

    @Override
    @Transactional(readOnly = false)
    public TourDto createTour(CreateTourCommand command) {
        log.debug("Trying to create tour with command {}", command);
        Objects.requireNonNull(command, "command must not be null!");
        Objects.requireNonNull(command.from(), "from must not be null!");
        Objects.requireNonNull(command.to(), "to must not be null!");

        if (tourRepository.existsTourByName(command.name())) {
            throw new IllegalArgumentException("Tour with name %s already exists!"
                    .formatted(command.name()));
        }

        CoordinateDto fromCoordinates = geocodeSearchService
                .getCoordinates(String.format("%s %s %s %s %s",
                command.from().streetName(),
                command.from().streetNumber(),
                command.from().zipCode(),
                command.from().city(),
                command.from().country()));

        CoordinateDto toCoordinates = geocodeSearchService
                .getCoordinates(String.format("%s %s %s %s %s",
                        command.to().streetName(),
                        command.to().streetNumber(),
                        command.to().zipCode(),
                        command.to().city(),
                        command.to().country()));

        RouteInformationDto routeInformation = routeService.getRouteInformation(
                fromCoordinates.latitude(),
                fromCoordinates.longitude(),
                toCoordinates.latitude(),
                toCoordinates.longitude(),
                command.transportType()
        );

        Tour tour = Tour.builder()
                .name(command.name())
                .description(command.description())
                .from(Address.builder()
                        .streetName(command.from().streetName())
                        .streetNumber(command.from().streetNumber())
                        .city(command.from().city())
                        .zipCode(command.from().zipCode())
                        .country(command.from().country())
                        .latitude(fromCoordinates.latitude())
                        .longitude(fromCoordinates.longitude())
                        .build())
                .to(Address.builder()
                        .streetName(command.to().streetName())
                        .streetNumber(command.to().streetNumber())
                        .city(command.to().city())
                        .zipCode(command.to().zipCode())
                        .country(command.to().country())
                        .latitude(toCoordinates.latitude())
                        .longitude(toCoordinates.longitude())
                        .build())
                .transportType(command.transportType())
                .distance(routeInformation.distance())
                .estimatedTime(routeInformation.estimatedTime())
                .build();

        tourRepository.save(tour);
        log.info("Created tour {}", tour);
        return tourDtoMapper.toDto(tour);
    }

    @Override
    @Transactional(readOnly = false)
    public TourDto updateTour(TourId id, UpdateTourCommand command) {
        log.debug("Trying to update tour with id {} and command {}", id.id(), command);
        Objects.requireNonNull(command, "command must not be null!");
        Objects.requireNonNull(id, "id must not be null!");
        Objects.requireNonNull(command.from(), "from must not be null!");
        Objects.requireNonNull(command.to(), "to must not be null!");

        Optional<Tour> entity = tourRepository.findTourById(id);

        if (entity.isEmpty()) {
            log.warn("Tour with id {} can not be found!", id.id());
            throw new IllegalArgumentException(
                    "Tour with id %s can not be found!".formatted(id.id()));
        }


        Tour tour = entity.get();

        if (!tour.getName().equals(command.name()) &&
                tourRepository.existsTourByName(command.name())) {
            throw new IllegalArgumentException("Tour with name %s already exists!"
                    .formatted(command.name()));
        }

        CoordinateDto fromCoordinates = geocodeSearchService
                .getCoordinates(String.format("%s %s %s %s %s",
                        command.from().streetName(),
                        command.from().streetNumber(),
                        command.from().zipCode(),
                        command.from().city(),
                        command.from().country()));

        CoordinateDto toCoordinates = geocodeSearchService
                .getCoordinates(String.format("%s %s %s %s %s",
                        command.to().streetName(),
                        command.to().streetNumber(),
                        command.to().zipCode(),
                        command.to().city(),
                        command.to().country()));

        RouteInformationDto routeInformation = routeService.getRouteInformation(
                fromCoordinates.latitude(),
                fromCoordinates.longitude(),
                toCoordinates.latitude(),
                toCoordinates.longitude(),
                command.transportType()
        );

        tour.setName(command.name());
        tour.setDescription(command.description());
        tour.setFrom(Address.builder()
                .streetName(command.from().streetName())
                .streetNumber(command.from().streetNumber())
                .city(command.from().city())
                .zipCode(command.from().zipCode())
                .country(command.from().country())
                .latitude(fromCoordinates.latitude())
                .longitude(fromCoordinates.longitude())
                .build()
        );
        tour.setTo(Address.builder()
                .streetName(command.to().streetName())
                .streetNumber(command.to().streetNumber())
                .city(command.to().city())
                .zipCode(command.to().zipCode())
                .country(command.to().country())
                .latitude(toCoordinates.latitude())
                .longitude(toCoordinates.longitude())
                .build());
        tour.setTransportType(command.transportType());
        tour.setDistance(routeInformation.distance());
        tour.setEstimatedTime(routeInformation.estimatedTime());

        log.info("Successfully updated tour {}", tour);
        return tourDtoMapper.toDto(tourRepository.save(tour));
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteTour(TourId id) {
        tourRepository.deleteTourById(id);
        log.info("Successfully deleted tour with id {}", id.id());
    }
}
