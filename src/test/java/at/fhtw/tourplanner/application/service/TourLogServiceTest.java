package at.fhtw.tourplanner.application.service;

import at.fhtw.tourplanner.application.service.commands.CreateTourLogCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourCommand;
import at.fhtw.tourplanner.application.service.commands.UpdateTourLogCommand;
import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import at.fhtw.tourplanner.application.service.mappers.DurationDtoMapper;
import at.fhtw.tourplanner.application.service.mappers.TourLogDtoMapper;
import at.fhtw.tourplanner.domain.model.*;
import at.fhtw.tourplanner.domain.util.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TourLogServiceTest {
    private TourLogService tourLogService;
    private TourLogDtoMapper tourLogDtoMapper;
    @Mock
    private TourLogRepository tourLogRepository;
    @Mock
    private TourRepository tourRepository;
    private Tour tour;
    private TourLog tourLog;

    @BeforeEach
    void setUp() {
        tourLogDtoMapper = new TourLogDtoMapper(new DurationDtoMapper());
        tourLogService = new TourLogServiceImpl(
                tourLogRepository,
                tourRepository,
                tourLogDtoMapper
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
    }

    @Test
    void ensureGetTourLogsOfTourWorksProperly() {
        // Given
        when(tourLogRepository.findAllByTourId(eq(tour.getId()), eq(0), eq(5), eq("comment")))
                .thenReturn(Page.<TourLog>builder().content(List.of(tourLog)).build());

        // When
        Page<TourLogDto> tourLogs = tourLogService.getTourLogsOfTour(tour.getId(), 0, 5);

        // Then
        assertThat(tourLogs.getContent()).contains(tourLogDtoMapper.toDto(tourLog));
    }

    @Test
    void ensureGetTourLogsOfTourByCommentWorksProperly() {
        // Given
        when(tourLogRepository.findAllByTourIdAndCommentLike(
                eq(tour.getId()),
                eq("comment"),
                eq(0),
                eq(5),
                eq("comment"))
        ).thenReturn(Page.<TourLog>builder().content(List.of(tourLog)).build());

        // When
        Page<TourLogDto> tourLogs = tourLogService.getTourLogsOfTourByComment(
                tour.getId(), "comment", 0, 5);

        // Then
        assertThat(tourLogs.getContent()).contains(tourLogDtoMapper.toDto(tourLog));
    }

    @Test
    void ensureGetTourLogWorksProperly() {
        // Given
        when(tourLogRepository.findTourEntityById(eq(tourLog.getId()))).thenReturn(Optional.of(tourLog));

        // When
        Optional<TourLogDto> tourLogDto = tourLogService.getTourLog(tourLog.getId());

        // Then
        assertThat(tourLogDto.isPresent()).isTrue();
        assertThat(tourLogDto.get()).isEqualTo(tourLogDtoMapper.toDto(tourLog));
    }

    @Test
    void ensureGetTourLogWorksProperlyWhenTourLogCanNotBeFound() {
        // Given
        when(tourLogRepository.findTourEntityById(eq(tourLog.getId()))).thenReturn(Optional.empty());

        // When
        Optional<TourLogDto> tourLogDto = tourLogService.getTourLog(tourLog.getId());

        // Then
        assertThat(tourLogDto.isPresent()).isFalse();
    }

    @Test
    void ensureCreateTourLogWorksProperly() {
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
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.of(tour));

        // When
        TourLogDto tourLogDto = tourLogService.createTourLog(command);

        // Then
        assertThat(tourLogDto).isNotNull();
        assertThat(tourLogDto.id()).isNotNull();
        assertThat(tourLogDto.duration().startTime()).isEqualTo(command.startTime());
        assertThat(tourLogDto.duration().endTime()).isEqualTo(command.endTime());
        assertThat(tourLogDto.duration().duration()).isEqualTo(60);
        assertThat(tourLogDto.comment()).isEqualTo(command.comment());
        assertThat(tourLogDto.distance()).isEqualTo(command.distance());
        assertThat(tourLogDto.rating()).isEqualTo(command.rating());
        assertThat(tourLogDto.difficulty()).isEqualTo(command.difficulty());
    }

    @Test
    void ensureCreateTourLogThrowsExceptionWhenTourLogCanNotBeFound() {
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
        when(tourRepository.findTourById(eq(tour.getId()))).thenReturn(Optional.empty());

        // When
        assertThrows(IllegalArgumentException.class, () -> tourLogService.createTourLog(command));
    }

    @Test
    void ensureUpdateTourLogWorksProperly() {
        // Given
        UpdateTourLogCommand command = UpdateTourLogCommand.builder()
                .startTime(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                .endTime(LocalDateTime.of(2025, 1, 1, 13, 0, 0))
                .comment("What a nice tour!")
                .distance(20)
                .rating(5)
                .difficulty(5)
                .build();
        when(tourLogRepository.findTourEntityById(eq(tourLog.getId()))).thenReturn(Optional.of(tourLog));
        when(tourLogRepository.save(any(TourLog.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        // When
        TourLogDto tourLogDto = tourLogService.updateTourLog(tourLog.getId(), command);

        // Then
        assertThat(tourLogDto).isNotNull();
        assertThat(tourLogDto.id()).isNotNull();
        assertThat(tourLogDto.duration().startTime()).isEqualTo(command.startTime());
        assertThat(tourLogDto.duration().endTime()).isEqualTo(command.endTime());
        assertThat(tourLogDto.duration().duration()).isEqualTo(60);
        assertThat(tourLogDto.comment()).isEqualTo(command.comment());
        assertThat(tourLogDto.distance()).isEqualTo(command.distance());
        assertThat(tourLogDto.rating()).isEqualTo(command.rating());
        assertThat(tourLogDto.difficulty()).isEqualTo(command.difficulty());
    }

    @Test
    void ensureUpdateTourLogThrowsExceptionWhenTourLogCanNotBeFound() {
        // Given
        UpdateTourLogCommand command = UpdateTourLogCommand.builder()
                .startTime(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                .endTime(LocalDateTime.of(2025, 1, 1, 13, 0, 0))
                .comment("What a nice tour!")
                .distance(20)
                .rating(5)
                .difficulty(5)
                .build();
        when(tourLogRepository.findTourEntityById(eq(tourLog.getId()))).thenReturn(Optional.empty());

        // When
        assertThrows(IllegalArgumentException.class, () -> tourLogService.updateTourLog(tourLog.getId(), command));
    }

    @Test
    void ensureDeleteTourLogWorksProperly() {
        tourLogService.deleteTourLog(tourLog.getId());
    }
}
