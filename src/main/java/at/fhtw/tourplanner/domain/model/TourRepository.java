package at.fhtw.tourplanner.domain.model;

import java.util.List;
import java.util.Optional;

public interface TourRepository {
    Tour save(Tour tour);
    boolean existsTourByName(String name);
    List<Tour> findAll(int page, int size, String sortBy);
    List<Tour> findAllByNameLike(String name, int page, int size, String sortBy);
    Optional<Tour> findTourById(TourId id);
    void deleteTourById(TourId id);
}
