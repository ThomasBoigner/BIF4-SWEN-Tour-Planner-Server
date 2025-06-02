package at.fhtw.tourplanner.application.service.commands;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CreateTourLogCommand(
        UUID tourId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String comment,
        int difficulty,
        double distance,
        int rating
) { }
