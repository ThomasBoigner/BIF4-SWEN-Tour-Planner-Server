package at.fhtw.tourplanner.application.service.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DurationDto(LocalDateTime startTime, LocalDateTime endTime, long duration) { }
