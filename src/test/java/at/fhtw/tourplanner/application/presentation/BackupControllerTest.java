package at.fhtw.tourplanner.application.presentation;

import at.fhtw.tourplanner.application.service.BackupService;
import at.fhtw.tourplanner.application.service.dto.BackupTourDto;
import at.fhtw.tourplanner.application.service.mappers.AddressDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.BackupTourDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.BackupTourLogDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.DurationDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BackupControllerTest {
    private MockMvc mockMvc;

    @Mock
    private BackupService backupService;
    private ObjectMapper objectMapper;
    private BackupTourDtoMapper backupTourDtoMapper;

    private Tour tour;
    private TourLog tourLog;
    private BackupTourDto backupTourDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(new BackupController(backupService, objectMapper)).build();
        backupTourDtoMapper = new BackupTourDtoMapper(
                new AddressDtoMapper(),
                new BackupTourLogDtoMapper(new DurationDtoMapper())
        );

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

        backupTourDto = backupTourDtoMapper.toDto(tour, List.of(tourLog));
    }

    @Test
    void ensureExportTourWorksProperly() throws Exception {
        // Given
        when(backupService.exportTour(eq(tour.getId()))).thenReturn(Optional.of(backupTourDto));

        // Perform
        mockMvc.perform(get("/api/backup/export/%s".formatted(tour.getId().id()))
                .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(backupTourDto)));
    }

    @Test
    void ensureExportTourReturnsNotFoundWhenTourCanNotBeFound() throws Exception {
        // Given
        when(backupService.exportTour(eq(tour.getId()))).thenReturn(Optional.empty());

        // Perform
        mockMvc.perform(get("/api/backup/export/%s".formatted(tour.getId().id()))
                        .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
