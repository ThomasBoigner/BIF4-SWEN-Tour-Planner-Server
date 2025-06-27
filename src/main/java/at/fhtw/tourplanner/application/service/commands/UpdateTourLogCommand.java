package at.fhtw.tourplanner.application.service.commands;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UpdateTourLogCommand(
        LocalDateTime startTime,
        LocalDateTime endTime,
        String comment,
        int difficulty,
        double distance,
        int rating
) { }
