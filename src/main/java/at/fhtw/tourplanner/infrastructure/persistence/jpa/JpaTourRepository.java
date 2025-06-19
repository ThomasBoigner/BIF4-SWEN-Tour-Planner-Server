package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.domain.model.*;
import at.fhtw.tourplanner.domain.util.Page;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper.TourEntityMapper;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor

@Repository
public class JpaTourRepository implements TourRepository {
    private Tour tour2 = Tour.builder()
            .name("Tour 2")
            .description("This tour is awesome")
            .from(Address.builder()
                    .streetName("Austria")
                    .city("Deutsch Wagram")
                    .zipCode(2232)
                    .streetName("Radetzkystraße")
                    .streetNumber("2-6")
                    .country("Austria")
                    .latitude(48.360310)
                    .longitude(16.600938)
                    .build())
            .to(Address.builder()
                    .streetName("Austria")
                    .city("Strasshof an der Nordbahn")
                    .zipCode(2231)
                    .streetName("Billroth-Gasse")
                    .streetNumber("5")
                    .country("Austria")
                    .latitude(48.567379)
                    .longitude(16.572229)
                    .build())
            .transportType(TransportType.RUNNING)
            .distance(40)
            .estimatedTime(240)
            .build();

    private Tour tour3 = Tour.builder()
            .name("Tour 3")
            .description("This tour is awesome")
            .from(Address.builder()
                    .streetName("Austria")
                    .city("Deutsch Wagram")
                    .zipCode(2232)
                    .streetName("Radetzkystraße")
                    .streetNumber("2-6")
                    .country("Austria")
                    .latitude(48.384689)
                    .longitude(16.515160)
                    .build())
            .to(Address.builder()
                    .streetName("Austria")
                    .city("Strasshof an der Nordbahn")
                    .zipCode(2231)
                    .streetName("Billroth-Gasse")
                    .streetNumber("5")
                    .country("Austria")
                    .latitude(48.527710)
                    .longitude(16.361530)
                    .build())
            .transportType(TransportType.BIKE)
            .distance(30)
            .estimatedTime(60)
            .build();

    private final TourEntityRepository tourEntityRepository;
    private final TourEntityMapper tourEntityMapper;

    @Override
    public Tour save(Tour tour) {
        tourEntityRepository.save(tourEntityMapper.toEntity(tour));
        return tour;
    }

    @Override
    public boolean existsTourByName(String name) {
        return tourEntityRepository.existsTourByName(name);
    }

    @Override
    public Page<Tour> findAll(int page, int size, String sortBy) {
        org.springframework.data.domain.Page<TourEntity> result = tourEntityRepository
                .findAll(PageRequest.of(page, size, Sort.by(Sort.Order.asc(sortBy))));

        return Page.<Tour>builder()
                .content(tourEntityMapper.toDomainObjects(result.getContent()))
                .last(result.isLast())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .first(result.isFirst())
                .size(result.getSize())
                .number(result.getNumber())
                .numberOfElements(result.getNumberOfElements())
                .empty(result.isEmpty())
                .build();
    }

    @Override
    public Page<Tour> findAllByNameLike(String name, int page, int size, String sortBy) {
        org.springframework.data.domain.Page<TourEntity> result = tourEntityRepository
                .findAllByNameLike(
                        "%%%s%%".formatted(name),
                        PageRequest.of(
                                page,
                                size,
                                Sort.by(Sort.Order.asc(sortBy)
                        )));

        return Page.<Tour>builder()
                .content(tourEntityMapper.toDomainObjects(result.getContent()))
                .last(result.isLast())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .first(result.isFirst())
                .size(result.getSize())
                .number(result.getNumber())
                .numberOfElements(result.getNumberOfElements())
                .empty(result.isEmpty())
                .build();
    }

    @Override
    public Optional<Tour> findTourById(TourId id) {
        return tourEntityRepository
                .findTourEntityById(id.id())
                .map(tourEntityMapper::toDomainObject);
    }

    @Override
    public void deleteTourById(TourId id) {
        tourEntityRepository.deleteTourEntityById(id.id());
    }

    @PostConstruct
    void initDB() {
        save(tour2);

        save(tour3);
    }
}
