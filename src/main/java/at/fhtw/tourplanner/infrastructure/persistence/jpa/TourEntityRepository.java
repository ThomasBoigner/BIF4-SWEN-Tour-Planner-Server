package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TourEntityRepository extends JpaRepository<TourEntity, UUID> {
    Slice<TourEntity> findAllByNameLike(String name, Pageable pageable);
    boolean existsTourByName(String name);
    Optional<TourEntity> findTourEntityById(UUID id);
    void deleteTourEntityById(UUID id);
}
