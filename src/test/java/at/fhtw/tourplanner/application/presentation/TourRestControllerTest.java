package at.fhtw.tourplanner.application.presentation;

import at.fhtw.tourplanner.application.service.TourService;
import at.fhtw.tourplanner.application.service.commands.CreateAddressCommand;
import at.fhtw.tourplanner.application.service.commands.CreateTourCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourCommand;
import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.Address;
import at.fhtw.tourplanner.domain.model.Tour;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TourRestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TourService tourService;

    private Tour tour;
    private TourDto tourDto;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TourRestController(tourService)).build();

        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        tour = Tour.builder()
                .name("Tour 1")
                .description("This tour is awesome")
                .from(Address.builder()
                        .city("Deutsch Wagram")
                        .zipCode(2232)
                        .streetName("Radetzkystraße")
                        .streetNumber("2-6")
                        .country("Austria")
                        .build())
                .to(Address.builder()
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

        tourDto = new TourDto(tour);
    }

    @Test
    void ensureGetToursWorksProperly() throws Exception {
        // Given
        when(tourService.getTours()).thenReturn(List.of(tourDto));

        // Perform
        mockMvc.perform(get("/api/tour").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(tourDto))));
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
        when(tourService.getTour(eq(tour.getId()))).thenReturn(Optional.of(tourDto));

        // Perform
        mockMvc.perform(get("/api/tour/%s".formatted(tour.getId().id()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(tourDto)));
    }

    @Test
    void ensureGetTourReturnsNotFoundWhenTourCanNotBeFound() throws Exception {
        // When
        when(tourService.getTour(eq(tour.getId()))).thenReturn(Optional.empty());

        // Perform
        mockMvc.perform(get("/api/tour/%s".formatted(tour.getId().id()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void ensureCreateTourWorksProperly() throws Exception {
        // Given
        CreateTourCommand command = CreateTourCommand.builder()
                .name("Tour 1")
                .description("This tour is awesome")
                .from(CreateAddressCommand.builder()
                        .city("Deutsch Wagram")
                        .zipCode(2232)
                        .streetName("Radetzkystraße")
                        .streetNumber("2-6")
                        .country("Austria")
                        .build())
                .to(CreateAddressCommand.builder()
                        .city("Strasshof an der Nordbahn")
                        .zipCode(2231)
                        .streetName("Billroth-Gasse")
                        .streetNumber("5")
                        .country("Austria")
                        .build())
                .transportType(TransportType.BIKE)
                .build();
        when(tourService.createTour(eq(command))).thenReturn(tourDto);

        // Perform
        mockMvc.perform(post("/api/tour").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/api/tour/" + tourDto.id()))
                .andExpect(content().string(objectMapper.writeValueAsString(tourDto)));
    }

    @Test
    void ensureUpdateTourWorksProperly() throws Exception {
        // When
        UpdateTourCommand command = UpdateTourCommand.builder()
                .name("Tour 1")
                .description("This tour is awesome")
                .from(CreateAddressCommand.builder()
                        .city("Deutsch Wagram")
                        .zipCode(2232)
                        .streetName("Radetzkystraße")
                        .streetNumber("2-6")
                        .country("Austria")
                        .build())
                .to(CreateAddressCommand.builder()
                        .city("Strasshof an der Nordbahn")
                        .zipCode(2231)
                        .streetName("Billroth-Gasse")
                        .streetNumber("5")
                        .country("Austria")
                        .build())
                .transportType(TransportType.BIKE)
                .build();

        when(tourService.updateTour(eq(tour.getId()), eq(command))).thenReturn(tourDto);

        // Perform
        mockMvc.perform(put("/api/tour/%s".formatted(tour.getId().id()))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(tourDto)));
    }

    @Test
    void ensureDeleteTourWorksProperly() throws Exception {
        // Perform
        mockMvc.perform(delete("/api/tour/%s".formatted(tour.getId().id()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
