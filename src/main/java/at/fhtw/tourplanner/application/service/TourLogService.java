package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.commands.CreateTourLogCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourLogCommand;
import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.model.TourLogId;
import at.fhtw.tourplanner.domain.util.Page;

import java.util.Optional;

public interface TourLogService {
    Page<TourLogDto> getTourLogsOfTour(TourId tourId, int page, int size);
    Page<TourLogDto> getTourLogsOfTourByComment(TourId tourId, String comment, int page, int size);
    Optional<TourLogDto> getTourLog(TourLogId tourLogId);
    TourLogDto createTourLog(CreateTourLogCommand command);
    TourLogDto updateTourLog(TourLogId tourLogId, UpdateTourLogCommand command);
    void deleteTourLog(TourLogId id);
}
