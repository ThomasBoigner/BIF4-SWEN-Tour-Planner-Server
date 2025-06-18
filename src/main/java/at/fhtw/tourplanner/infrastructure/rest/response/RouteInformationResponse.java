package at.fhtw.tourplanner.infrastructure.rest.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RouteInformationResponse(List<RouteInformationFeatureResponse> features) { }
