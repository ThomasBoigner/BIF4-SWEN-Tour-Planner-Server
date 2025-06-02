package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.domain.model.*;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper.TourLogEntityMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Repository
public class JpaTourLogRepository implements TourLogRepository {
    private Tour tour1 = Tour.builder()
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

    private TourLog tourlog1 = TourLog.builder()
            .tour(tour1)
            .duration(Duration.builder()
                    .startTime(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                    .endTime(LocalDateTime.of(2025, 1, 1, 13, 0, 0))
                    .build())
            .comment("What a nice tour!")
            .difficulty(new Difficulty(3))
            .distance(2)
            .rating(new Rating(5))
            .build();

    private TourLog tourlog2 = TourLog.builder()
            .tour(tour1)
            .duration(Duration.builder()
                    .startTime(LocalDateTime.of(2025, 2, 1, 8, 0, 0))
                    .endTime(LocalDateTime.of(2025, 2, 1, 10, 0, 0))
                    .build())
            .comment("Super cool!")
            .difficulty(new Difficulty(2))
            .distance(1.5)
            .rating(new Rating(4))
            .build();

    private final TourLogEntityRepository tourLogEntityRepository;
    private final TourLogEntityMapper tourLogEntityMapper;

    @Override
    public TourLog save(TourLog tourLog) {
        tourLogEntityRepository.save(tourLogEntityMapper.toEntity(tourLog));
        return tourLog;
    }

    @Override
    public List<TourLog> findAllByTourId(TourId tourId) {
        return tourLogEntityMapper.toDomainObjects(
                tourLogEntityRepository.findAllByTourId(tourId.id())
        );
    }

    @Override
    public Optional<TourLog> findTourEntityById(TourLogId id) {
        return tourLogEntityRepository
                .findById(id.id())
                .map(tourLogEntityMapper::toDomainObject);
    }

    @Override
    public void deleteTourLogById(TourLogId id) {
        tourLogEntityRepository.deleteTourLogEntityById(id.id());
    }

    @PostConstruct
    void initDB() {
        save(tourlog1);

        save(tourlog2);
    }
}
