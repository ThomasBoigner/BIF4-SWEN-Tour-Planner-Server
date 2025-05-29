package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourEntity;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TourLogEntityRepository extends JpaRepository<TourLogEntity, UUID> {
    List<TourLogEntity> findAllByTourId(UUID tourId);

    UUID tour(TourEntity tour);
}
