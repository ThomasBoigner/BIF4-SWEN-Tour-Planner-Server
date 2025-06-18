package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.dto.RouteInformationDto;
import at.fhtw.tourplanner.domain.model.TransportType;

public interface RouteService {
    RouteInformationDto getRouteInformation(
            double fromLatitude,
            double formLongitude,
            double toLatitude,
            double toLongitude,
            TransportType transportType
    );
}
