package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.commands.CreateTourCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourCommand;
import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.application.service.mappers.TourDtoMapper;
import at.fhtw.tourplanner.domain.model.Address;
import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.model.TourRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor

@Slf4j
@Service
@Transactional(readOnly = true)
public class TourService {
    private final TourRepository tourRepository;
    private final TourDtoMapper tourDtoMapper;

    public List<TourDto> getTours() {
        log.debug("Trying to get all tours");
        List<TourDto> tours = tourRepository.findAll().stream().map(tourDtoMapper::toDto).toList();
        log.info("Retrieved all ({}) tours", tours.size());
        return tours;
    }

    public Optional<TourDto> getTour(TourId id) {
        log.debug("Trying to get tour with id {}", id);
        Optional<TourDto> tour = tourRepository.findTourById(id).map(tourDtoMapper::toDto);
        tour.ifPresentOrElse(
                t -> log.info("Found tour {} with id {}", t, id.id()),
                () -> log.info("No tour with id {} found", id.id()));
        return tour;
    }

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

        Tour tour = Tour.builder()
                .name(command.name())
                .description(command.description())
                .from(Address.builder()
                        .streetName(command.from().streetName())
                        .streetNumber(command.from().streetNumber())
                        .city(command.from().city())
                        .zipCode(command.from().zipCode())
                        .country(command.from().country())
                        .latitude(0)
                        .longitude(0)
                        .build())
                .to(Address.builder()
                        .streetName(command.to().streetName())
                        .streetNumber(command.to().streetNumber())
                        .city(command.to().city())
                        .zipCode(command.to().zipCode())
                        .country(command.to().country())
                        .latitude(0)
                        .longitude(0)
                        .build())
                .transportType(command.transportType())
                .distance(0)
                .estimatedTime(0)
                .imageUrl("/img")
                .build();

        tourRepository.save(tour);
        log.info("Created tour {}", tour);
        return tourDtoMapper.toDto(tour);
    }

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

        tour.setName(command.name());
        tour.setDescription(command.description());
        tour.setFrom(Address.builder()
                .streetName(command.from().streetName())
                .streetNumber(command.from().streetNumber())
                .city(command.from().city())
                .zipCode(command.from().zipCode())
                .country(command.from().country())
                .build()
        );
        tour.setTo(Address.builder()
                .streetName(command.to().streetName())
                .streetNumber(command.to().streetNumber())
                .city(command.to().city())
                .zipCode(command.to().zipCode())
                .country(command.to().country())
                .build());
        tour.setTransportType(command.transportType());

        log.info("Successfully updated tour {}", tour);
        return tourDtoMapper.toDto(tourRepository.save(tour));
    }

    @Transactional(readOnly = false)
    public void deleteTour(TourId id) {
        tourRepository.deleteTourById(id);
        log.info("Successfully deleted tour with id {}", id.id());
    }
}
