package at.fhtw.tourplanner.application.service.dto;

import lombok.Builder;

@Builder
public record BackupTourLogDto(
        DurationDto duration,
        String comment,
        int difficulty,
        double distance,
        int rating) { }
