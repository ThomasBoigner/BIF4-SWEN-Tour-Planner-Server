package at.fhtw.tourplanner.infrastructure.rest.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GeocodeResponse(List<FeatureResponse> features) {
}
