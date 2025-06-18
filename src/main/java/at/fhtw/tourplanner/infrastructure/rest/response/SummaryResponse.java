package at.fhtw.tourplanner.infrastructure.rest.response;

import lombok.Builder;

@Builder
public record SummaryResponse(double distance, double duration) {
}
