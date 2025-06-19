package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.commands.CreateTourCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourCommand;
import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.util.Page;

import java.util.Optional;

public interface TourService {
    Page<TourDto> getTours(int page, int size);
    Page<TourDto> findToursByName(String name, int page, int size);

    Optional<TourDto> getTour(TourId id);

    TourDto createTour(CreateTourCommand command);

    TourDto updateTour(TourId id, UpdateTourCommand command);
    void deleteTour(TourId id);
}
