package at.fhtw.tourplanner.application.service.dto;

import lombok.Builder;

@Builder
public record CoordinateDto(double latitude, double longitude) {
}
