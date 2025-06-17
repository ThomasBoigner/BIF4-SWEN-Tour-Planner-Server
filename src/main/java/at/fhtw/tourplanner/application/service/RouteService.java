package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.RouteInformationDto;

public interface RouteService {
    RouteInformationDto getRouteInformation(
            double fromLatitude,
            double formLongitude,
            double toLatitude,
            double toLongitude
    );
}
