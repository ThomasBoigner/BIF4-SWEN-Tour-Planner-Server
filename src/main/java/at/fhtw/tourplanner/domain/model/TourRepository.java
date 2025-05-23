package at.fhtw.tourplanner.domain.model;

import java.util.List;

public interface TourRepository {
    Tour save(Tour tour);
    List<Tour> findAll();
}
