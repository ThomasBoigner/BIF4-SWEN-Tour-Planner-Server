package at.fhtw.tourplanner.infrastructure.rest;

import at.fhtw.tourplanner.application.service.GeocodeSearchService;
import at.fhtw.tourplanner.application.service.dto.CoordinateDto;
import at.fhtw.tourplanner.infrastructure.rest.response.GeocodeSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class OpenRouteServiceGeocodeSearchService implements GeocodeSearchService {
    private final RestClient restClient;

    public OpenRouteServiceGeocodeSearchService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.openrouteservice.org/")
                .build();
    }

    @Override
    public CoordinateDto getCoordinates(String address) {
        log.debug("Trying to get the coordinate of address {}", address);

        GeocodeSearchResponse response = this.restClient.get().uri(uriBuilder -> uriBuilder
                        .path("geocode/search")
                        .queryParam("api_key",
                                "5b3ce3597851110001cf624889f50efba32047fbae372fe1bdf0f950")
                        .queryParam("text", address)
                        .queryParam("size", 1)
                        .build())
                .retrieve()
                .body(GeocodeSearchResponse.class);

        if (response == null || response.features() == null || response.features().isEmpty()) {
            throw new IllegalArgumentException(String.format("Could not find address %s", address));
        }

        CoordinateDto coordinateDto = CoordinateDto.builder()
                .latitude(response.features().getFirst().geometry().coordinates().get(1))
                .longitude(response.features().getFirst().geometry().coordinates().get(0))
                .build();

        log.debug("Found coordinate {}", coordinateDto);

        return coordinateDto;
    }
}
