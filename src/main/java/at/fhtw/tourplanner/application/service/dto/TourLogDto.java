package at.fhtw.tourplanner.application.service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TourLogDto(UUID id,
                         UUID tourId,
                         DurationDto duration,
                         String comment,
                         int difficulty,
                         double distance,
                         int rating) { }
