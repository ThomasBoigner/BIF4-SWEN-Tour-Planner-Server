package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.CoordinateDto;

public interface GeocodeSearchService {
    CoordinateDto getCoordinates(String address);
}
