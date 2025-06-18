package at.fhtw.tourplanner.infrastructure.rest;

import at.fhtw.tourplanner.application.service.dto.RouteInformationDto;
import at.fhtw.tourplanner.infrastructure.rest.response.PropertiesResponse;
import at.fhtw.tourplanner.infrastructure.rest.response.RouteInformationFeatureResponse;
import at.fhtw.tourplanner.infrastructure.rest.response.RouteInformationResponse;
import at.fhtw.tourplanner.infrastructure.rest.response.SummaryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(OpenRouteServiceRouteService.class)
public class OpenRouteServiceRouteServiceTest {
    @Autowired
    private OpenRouteServiceRouteService openRouteServiceRouteService;
    @Autowired
    private MockRestServiceServer server;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void ensureGetRouteInformationWorksProperly() throws JsonProcessingException {
        // Given
        double fromLatitude = 10;
        double fromLongitude = 20;
        double toLatitude = 30;
        double toLongitude = 40;

        RouteInformationResponse routeInformationResponse = RouteInformationResponse.builder()
                .features(List.of(
                        RouteInformationFeatureResponse.builder()
                                .properties(PropertiesResponse.builder()
                                        .summary(SummaryResponse.builder()
                                                .distance(10)
                                                .duration(20)
                                                .build())
                                        .build())
                                .build()
                ))
                .build();

        this.server.expect(requestTo("https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf624889f50efba32047fbae372fe1bdf0f950&start=20.0,%2010.0&end=40.0,%2030.0")).andRespond(withSuccess(objectMapper.writeValueAsString(routeInformationResponse), MediaType.APPLICATION_JSON));

        // When
        RouteInformationDto result = openRouteServiceRouteService.getRouteInformation(fromLatitude, fromLongitude, toLatitude, toLongitude);

        // Then
        assertThat(result.distance()).isEqualTo(10);
        assertThat(result.estimatedTime()).isEqualTo(20);
    }

    @Test
    void ensureGetRouteInformationThrowsErrorIfInformationCanNotBeRetrieved() {
        // Given
        double fromLatitude = 10;
        double fromLongitude = 20;
        double toLatitude = 30;
        double toLongitude = 40;

        RouteInformationResponse routeInformationResponse = RouteInformationResponse.builder()
                .features(List.of(
                        RouteInformationFeatureResponse.builder()
                                .properties(PropertiesResponse.builder()
                                        .summary(SummaryResponse.builder()
                                                .distance(10)
                                                .duration(20)
                                                .build())
                                        .build())
                                .build()
                ))
                .build();

        this.server.expect(requestTo("https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf624889f50efba32047fbae372fe1bdf0f950&start=20.0,%2010.0&end=40.0,%2030.0")).andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        // When
        assertThrows(IllegalArgumentException.class, () -> openRouteServiceRouteService.getRouteInformation(fromLatitude, fromLongitude, toLatitude, toLongitude));
    }
}
