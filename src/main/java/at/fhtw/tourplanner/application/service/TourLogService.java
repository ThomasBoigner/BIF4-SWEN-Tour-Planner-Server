package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.commands.CreateTourLogCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourLogCommand;
import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.model.TourLogId;

import java.util.List;

public interface TourLogService {
    List<TourLogDto> getTourLogsOfTour(TourId tourId);
    TourLogDto createTourLog(CreateTourLogCommand command);
    TourLogDto updateTourLog(TourLogId tourLogId, UpdateTourLogCommand command);
    void deleteTourLog(TourLogId id);
}
