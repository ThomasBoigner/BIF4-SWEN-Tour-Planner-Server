package at.fhtw.tourplanner.application.service.dto;

import lombok.Builder;

@Builder
public record RouteInformationDto(double distance, double estimatedTime) {
}
