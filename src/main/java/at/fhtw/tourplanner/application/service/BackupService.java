package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.BackupTourDto;
import at.fhtw.tourplanner.domain.model.TourId;

import java.util.Optional;

public interface BackupService {
    Optional<BackupTourDto> exportTour(TourId tourId);
}
