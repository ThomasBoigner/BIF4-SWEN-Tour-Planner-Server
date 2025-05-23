package at.fhtw.tourplanner.domain.model;

import java.util.List;
import java.util.Optional;

public interface TourRepository {
    Tour save(Tour tour);
    List<Tour> findAll();
    Optional<Tour> findTourById(TourId id);
}
