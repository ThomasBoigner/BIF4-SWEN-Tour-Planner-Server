package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.commands.CreateTourCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourCommand;
import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.TourId;

import java.util.List;
import java.util.Optional;

public interface TourService {
    List<TourDto> getTours(int page, int size);
    List<TourDto> findToursByName(String name, int page, int size);

    Optional<TourDto> getTour(TourId id);

    TourDto createTour(CreateTourCommand command);

    TourDto updateTour(TourId id, UpdateTourCommand command);
    void deleteTour(TourId id);
}
