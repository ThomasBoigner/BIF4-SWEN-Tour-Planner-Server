package at.fhtw.tourplanner.infrastructure.persistence.jpa.rest.response;

import lombok.Builder;

@Builder
public record FeatureResponse(GeometryResponse geometry) { }
