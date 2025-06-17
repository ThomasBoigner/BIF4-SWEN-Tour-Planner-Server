package at.fhtw.tourplanner.infrastructure.rest;

import at.fhtw.tourplanner.application.service.RouteService;
import at.fhtw.tourplanner.application.service.dto.RouteInformationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpenRouteServiceRouteService implements RouteService {
    @Override
    public RouteInformationDto getRouteInformation(double fromLatitude, double formLongitude, double toLatitude, double toLongitude) {
        return null;
    }
}
