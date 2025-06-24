package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourEntity;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TourLogEntityRepository extends JpaRepository<TourLogEntity, UUID> {
    Page<TourLogEntity> findAllByTourIdAndCommentLike(
            UUID tourId,
            String comment,
            Pageable pageable
    );
    Page<TourLogEntity> findAllByTourId(UUID tourId, Pageable pageable);
    UUID tour(TourEntity tour);
    void deleteTourLogEntityById(UUID id);
}
