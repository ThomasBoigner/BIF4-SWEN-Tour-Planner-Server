package at.fhtw.tourplanner.infrastructure.rest;

import at.fhtw.tourplanner.application.service.dto.CoordinateDto;
import at.fhtw.tourplanner.infrastructure.rest.response.GeocodeFeatureResponse;
import at.fhtw.tourplanner.infrastructure.rest.response.GeocodeSearchResponse;
import at.fhtw.tourplanner.infrastructure.rest.response.GeometryResponse;
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

@RestClientTest(OpenRouteServiceGeocodeSearchService.class)
public class OpenRouteServiceGeocodeSearchServiceTest {
    @Autowired
    private OpenRouteServiceGeocodeSearchService openRouteServiceGeocodeSearchService;
    @Autowired
    private MockRestServiceServer server;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void ensureGetCoordinatesWorksProperly() throws JsonProcessingException {
        // Given
        String address = "Address";
        GeocodeSearchResponse geocodeResponse = GeocodeSearchResponse.builder()
                .features(List.of(GeocodeFeatureResponse.builder()
                                .geometry(GeometryResponse.builder()
                                        .coordinates(List.of(20d, 10d))
                                        .build())
                        .build()))
                .build();


        this.server.expect(
                requestTo("https://api.openrouteservice.org/geocode/search?api_key=" +
                        "5b3ce3597851110001cf624889f50efba32047fbae372fe1bdf0f950&text=Address" +
                        "&size=1"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(geocodeResponse),
                        MediaType.APPLICATION_JSON));

        // When
        CoordinateDto result = openRouteServiceGeocodeSearchService.getCoordinates(address);

        // Then
        assertThat(result.latitude()).isEqualTo(10d);
        assertThat(result.longitude()).isEqualTo(20d);
    }

    @Test
    void ensureGetCoordinatesThrowsErrorIfAddressCanNotBeFound() throws JsonProcessingException {
        // Given
        String address = "Address";


        this.server.expect(
                        requestTo("https://api.openrouteservice.org/geocode/search?api_key=" +
                                "5b3ce3597851110001cf624889f50efba32047fbae372fe1bdf0f950" +
                                "&text=Address&size=1"))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        // When
        assertThrows(IllegalArgumentException.class, () ->
                openRouteServiceGeocodeSearchService.getCoordinates(address));
    }
}
