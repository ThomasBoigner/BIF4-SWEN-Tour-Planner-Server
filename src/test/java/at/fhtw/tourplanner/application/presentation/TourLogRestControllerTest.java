package at.fhtw.tourplanner.application.presentation;

import at.fhtw.tourplanner.application.service.TourLogService;
import at.fhtw.tourplanner.application.service.commands.CreateTourLogCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourLogCommand;
import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import at.fhtw.tourplanner.application.service.mappers.DurationDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.TourLogDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
import at.fhtw.tourplanner.domain.util.Page;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TourLogRestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TourLogService tourLogService;
    private TourLogDtoMapper tourLogDtoMapper;

    private Tour tour;
    private TourLog tourLog;
    private TourLogDto tourLogDto;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TourLogRestController(tourLogService)).build();

        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        tourLogDtoMapper = new TourLogDtoMapper(new DurationDtoMapper());

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
                .build();

        tourLog = TourLog.builder()
                .tour(tour)
                .duration(Duration.builder()
                        .startTime(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                        .endTime(LocalDateTime.of(2025, 1, 1, 13, 0, 0))
                        .build())
                .comment("What a nice tour!")
                .difficulty(new Difficulty(3))
                .distance(2)
                .rating(new Rating(5))
                .build();

        tourLogDto = tourLogDtoMapper.toDto(tourLog);
    }

    @Test
    void ensureGetTourLogsOfTourWorksProperly() throws Exception {
        // Given
        Page<TourLogDto> page = Page.<TourLogDto>builder().content(List.of(tourLogDto)).build();
        when(tourLogService.getTourLogsOfTour(eq(tour.getId()), eq(0), eq(5)))
                .thenReturn(page);

        // Perform
        mockMvc.perform(get("/api/tourLog/tour/%s".formatted(tour.getId().id()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Test
    void ensureGetTourLogsOfTourWorksProperlyWithQueryString() throws Exception {
        // Given
        Page<TourLogDto> page = Page.<TourLogDto>builder().content(List.of(tourLogDto)).build();
        when(tourLogService.getTourLogsOfTourByComment(
                eq(tour.getId()),
                eq("comment"),
                eq(0),
                eq(5))
        ).thenReturn(page);

        // Perform
        mockMvc.perform(get("/api/tourLog/tour/%s?comment=comment".formatted(tour.getId().id()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Test
    void ensureGetTourLogsOfTourReturnsNoContentWhenListIsEmpty() throws Exception {
        // Given
        when(tourLogService.getTourLogsOfTour(eq(tour.getId()), eq(0), eq(5)))
                .thenReturn(Page.<TourLogDto>builder().empty(true).build());

        // Perform
        mockMvc.perform(get("/api/tourLog/tour/%s".formatted(tour.getId().id()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void ensureGetTourLogWorksProperly() throws Exception {
        // Given
        when(tourLogService.getTourLog(eq(tourLog.getId()))).thenReturn(Optional.of(tourLogDto));

        // Perform
        mockMvc.perform(get("/api/tourLog/%s".formatted(tourLog.getId().id()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(tourLogDto)));
    }

    @Test
    void ensureGetTourLogReturnsNotFoundWhenTourLogCanNotBeFound() throws Exception {
        // Given
        when(tourLogService.getTourLog(eq(tourLog.getId()))).thenReturn(Optional.empty());

        // Perform
        mockMvc.perform(get("/api/tourLog/%s".formatted(tourLog.getId().id()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void ensureCreateTourLogWorksProperly() throws Exception {
        // Given
        CreateTourLogCommand command = CreateTourLogCommand.builder()
                .tourId(tour.getId().id())
                .startTime(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                .endTime(LocalDateTime.of(2025, 1, 1, 13, 0, 0))
                .comment("What a nice tour!")
                .distance(20)
                .rating(5)
                .difficulty(5)
                .build();
        when(tourLogService.createTourLog(eq(command))).thenReturn(tourLogDto);

        // Perform
        mockMvc.perform(post("/api/tourLog").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl("/api/tourLog/" + tourLogDto.id()))
                .andExpect(content().string(objectMapper.writeValueAsString(tourLogDto)));
    }

    @Test
    void ensureUpdateTourLogWorksProperly() throws Exception {
        // Given
        UpdateTourLogCommand command = UpdateTourLogCommand.builder()
                .startTime(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                .endTime(LocalDateTime.of(2025, 1, 1, 13, 0, 0))
                .comment("What a nice tour!")
                .distance(20)
                .rating(5)
                .difficulty(5)
                .build();
        when(tourLogService.updateTourLog(eq(tourLog.getId()), eq(command))).thenReturn(tourLogDto);

        // Perform
        mockMvc.perform(put("/api/tourLog/%s".formatted(tourLog.getId().id()))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(tourLogDto)));
    }

    @Test
    void ensureDeleteTourLogWorksProperly() throws Exception {
        // Perform
        mockMvc.perform(delete("/api/tourLog/%s".formatted(tour.getId().id()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
