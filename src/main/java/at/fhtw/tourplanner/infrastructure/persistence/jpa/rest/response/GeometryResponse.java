package at.fhtw.tourplanner.infrastructure.persistence.jpa.rest.response;

import java.util.List;

public record GeometryResponse(List<Double> coordinates) {
}
