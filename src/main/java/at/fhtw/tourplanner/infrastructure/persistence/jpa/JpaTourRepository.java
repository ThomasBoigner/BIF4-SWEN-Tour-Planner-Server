package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.domain.model.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Repository
public class JpaTourRepository implements TourRepository {
    Tour tour1 = Tour.builder()
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

    Tour tour2 = Tour.builder()
            .name("Tour 2")
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
            .transportType(TransportType.RUNNING)
            .distance(40)
            .estimatedTime(240)
            .imageUrl("img")
            .build();

    Tour tour3 = Tour.builder()
            .name("Tour 3")
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
            .distance(30)
            .estimatedTime(60)
            .imageUrl("img")
            .build();

    private final TourEntityRepository tourEntityRepository;

    @Override
    public Tour save(Tour tour) {
        tourEntityRepository.save(new TourEntity(tour));
        return tour;
    }

    @Override
    public boolean existsTourByName(String name) {
        return tourEntityRepository.existsTourByName(name);
    }

    @Override
    public List<Tour> findAll() {
        return tourEntityRepository.findAll().stream().map(TourEntity::toTour).toList();
    }

    @Override
    public Optional<Tour> findTourById(TourId id) {
        return tourEntityRepository.findTourEntityById(id.id()).map(TourEntity::toTour);
    }

    @Override
    public void deleteTourById(TourId id) {
        tourEntityRepository.deleteTourEntityById(id.id());
    }

    @PostConstruct
    void initDB() {
        save(tour1);

        save(tour2);

        save(tour3);
    }
}
