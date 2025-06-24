package at.fhtw.tourplanner.domain.model;

import at.fhtw.tourplanner.domain.util.Page;

import java.util.Optional;

public interface TourLogRepository {
    TourLog save(TourLog tourLog);
    Page<TourLog> findAllByTourId(TourId tourId, int page, int size, String sortBy);
    Page<TourLog> findAllByTourIdAndCommentLike(
            TourId tourId,
            String comment,
            int page,
            int size,
            String sortBy
    );
    Optional<TourLog> findTourEntityById(TourLogId id);
    void deleteTourLogById(TourLogId id);
}
