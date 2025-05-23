package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor

@Repository
public class JpaTourRepository implements TourRepository {
    private final TourEntityRepository tourEntityRepository;

    @Override
    public Tour save(Tour tour) {
        tourEntityRepository.save(new TourEntity(tour));
        return tour;
    }

    @Override
    public List<Tour> findAll() {
        return tourEntityRepository.findAll().stream().map(TourEntity::toTour).toList();
    }
}
