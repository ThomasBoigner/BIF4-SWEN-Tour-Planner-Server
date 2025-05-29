package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TourEntityRepository extends JpaRepository<TourEntity, UUID> {
    boolean existsTourByName(String name);
    Optional<TourEntity> findTourEntityById(UUID id);
    void deleteTourEntityById(UUID id);
}
