package at.fhtw.tourplanner.infrastructure.rest;

import at.fhtw.tourplanner.application.service.RouteService;
import at.fhtw.tourplanner.application.service.dto.RouteInformationDto;
import at.fhtw.tourplanner.domain.model.TransportType;
import at.fhtw.tourplanner.infrastructure.rest.response.RouteInformationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class OpenRouteServiceRouteService implements RouteService {
    private final RestClient restClient;

    public OpenRouteServiceRouteService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.openrouteservice.org/")
                .build();
    }

    @Override
    public RouteInformationDto getRouteInformation(
            double fromLatitude,
            double formLongitude,
            double toLatitude,
            double toLongitude,
            TransportType transportType
    ) {
        log.debug(
                "Trying to get route information for from latitude {} and longitude {} and " +
                        "to latitude {} and longitude {} and transport type {}",
                fromLatitude,
                formLongitude,
                toLatitude,
                toLongitude,
                transportType
        );

        String profile = switch (transportType) {
            case TransportType.BIKE -> "cycling-regular";
            case TransportType.RUNNING -> "foot-walking";
            case TransportType.HIKE -> "foot-hiking";
            case TransportType.VACATION -> "driving-car";
        };

        RouteInformationResponse response = this.restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(String.format("v2/directions/%s", profile))
                        .queryParam("api_key",
                                "5b3ce3597851110001cf624889f50efba32047fbae372fe1bdf0f950")
                        .queryParam("start", String.format("%s, %s", formLongitude, fromLatitude))
                        .queryParam("end", String.format("%s, %s", toLongitude, toLatitude))
                        .build())
                .retrieve()
                .body(RouteInformationResponse.class);

        if (response == null || response.features() == null || response.features().isEmpty()) {
            throw new IllegalArgumentException("Could not calculate route information");
        }

        RouteInformationDto routeInformationDto = RouteInformationDto.builder()
                .distance(response.features().getFirst().properties().summary().distance())
                .estimatedTime(response.features().getFirst().properties().summary().duration())
                .build();

        log.debug("Retrieved route information: {}", routeInformationDto);

        return routeInformationDto;
    }
}
