package at.fhtw.tourplanner.application.presentation;

import at.fhtw.tourplanner.application.service.TourApplicationService;
import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.Address;
import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.model.TransportType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TourRestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TourApplicationService tourService;

    private Tour tour;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TourRestController(tourService)).build();

        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        tour = Tour.builder()
                .name("Tour 1")
                .description("This tour is awesome")
                .from(Address.builder()
                        .streetName("Austria")
                        .city("Deutsch Wagram")
                        .zipCode(2232)
                        .streetName("Radetzkystraße")
                        .streetNumber("2-6")
                        .country("Austria")
                        .build())
                .to(Address.builder()
                        .streetName("Austria")
                        .city("Strasshof an der Nordbahn")
                        .zipCode(2231)
                        .streetName("Billroth-Gasse")
                        .streetNumber("5")
                        .country("Austria")
                        .build())
                .transportType(TransportType.BIKE)
                .distance(20)
                .estimatedTime(120)
                .imageUrl("img")
                .build();
    }

    @Test
    void ensureGetToursWorksProperly() throws Exception {
        // Given
        when(tourService.getTours()).thenReturn(List.of(new TourDto(tour)));

        // Perform
        mockMvc.perform(get("/api/tour").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(new TourDto(tour)))));
    }

    @Test
    void ensureGetToursReturnsNoContentWhenListIsEmpty() throws Exception {
        // Given
        when(tourService.getTours()).thenReturn(List.of());

        // Perform
        mockMvc.perform(get("/api/tour").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void ensureGetTourWorksProperly() throws Exception {
        // When
        when(tourService.getTour(eq(tour.getId()))).thenReturn(Optional.of(new TourDto(tour)));

        // Perform
        mockMvc.perform(get("/api/tour/%s".formatted(tour.getId().id())).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(new TourDto(tour))));
    }

    @Test
    void ensureGetTourReturnsNotFoundWhenTourCanNotBeFound() throws Exception {
        // When
        when(tourService.getTour(eq(tour.getId()))).thenReturn(Optional.empty());

        // Perform
        mockMvc.perform(get("/api/tour/%s".formatted(tour.getId().id())).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
