package at.fhtw.tourplanner.application.service.dto;

import at.fhtw.tourplanner.domain.model.TransportType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TourDto(UUID id,
                      String name,
                      String description,
                      AddressDto from,
                      AddressDto to,
                      TransportType transportType,
                      double distance,
                      double estimatedTime,
                      String imageUrl) { }
