package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.domain.model.*;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper.TourEntityMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public List<Tour> findAll() {
        return tourEntityMapper.toDomainObjects(tourEntityRepository.findAll());
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
