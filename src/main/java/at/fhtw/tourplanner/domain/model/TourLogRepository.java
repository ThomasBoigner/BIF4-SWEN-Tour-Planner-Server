package at.fhtw.tourplanner.domain.model;

import java.util.List;
import java.util.Optional;

public interface TourLogRepository {
    TourLog save(TourLog tourLog);
    List<TourLog> findAllByTourId(TourId tourId);
    Optional<TourLog> findTourEntityById(TourLogId id);
    void deleteTourLogById(TourLogId id);
}
