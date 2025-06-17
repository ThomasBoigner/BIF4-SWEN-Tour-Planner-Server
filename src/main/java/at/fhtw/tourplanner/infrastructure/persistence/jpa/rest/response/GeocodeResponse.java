package at.fhtw.tourplanner.infrastructure.persistence.jpa.rest.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GeocodeResponse(List<FeatureResponse> features) {
}
