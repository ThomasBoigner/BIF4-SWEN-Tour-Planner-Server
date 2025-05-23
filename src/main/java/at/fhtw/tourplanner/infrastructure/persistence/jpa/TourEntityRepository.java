package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TourEntityRepository extends JpaRepository<TourEntity, Long> {
}
