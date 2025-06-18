package at.fhtw.tourplanner.infrastructure.rest.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GeometryResponse(List<Double> coordinates) {
}
