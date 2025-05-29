package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TourLogEntityRepository extends JpaRepository<TourLogEntity, UUID> {
}
