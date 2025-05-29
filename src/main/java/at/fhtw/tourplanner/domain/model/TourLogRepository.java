package at.fhtw.tourplanner.domain.model;

import java.util.List;

public interface TourLogRepository {
    TourLog save(TourLog tourLog);
    List<TourLog> findAllByTourId(TourId tourId);
}
