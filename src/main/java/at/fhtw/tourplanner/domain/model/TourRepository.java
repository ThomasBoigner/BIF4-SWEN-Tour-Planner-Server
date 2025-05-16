package at.fhtw.tourplanner.domain.model;

import java.util.List;

public interface TourRepository {
    List<Tour> findAll();
}
