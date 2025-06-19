package at.fhtw.tourplanner.domain.model;

import at.fhtw.tourplanner.domain.util.Page;

import java.util.Optional;

public interface TourRepository {
    Tour save(Tour tour);
    boolean existsTourByName(String name);
    Page<Tour> findAll(int page, int size, String sortBy);
    Page<Tour> findAllByNameLike(String name, int page, int size, String sortBy);
    Optional<Tour> findTourById(TourId id);
    void deleteTourById(TourId id);
}
