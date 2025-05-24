package at.fhtw.tourplanner.domain.model;

import java.util.List;
import java.util.Optional;

public interface TourRepository {
    Tour save(Tour tour);
    boolean existsTourByName(String name);
    List<Tour> findAll();
    Optional<Tour> findTourById(TourId id);
    void deleteTourById(TourId id);
}
