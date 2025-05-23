package at.fhtw.tourplanner.application.service.dto;

import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TransportType;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public record TourDto(UUID id,
                      String name,
                      String description,
                      AddressDto from,
                      AddressDto to,
                      TransportType transportType,
                      double distance,
                      double estimatedTime,
                      String imageUrl) {
    public TourDto(Tour tour) {
        this(tour.getId().id(),
                tour.getName(),
                tour.getDescription(),
                new AddressDto(tour.getFrom()),
                new AddressDto(tour.getTo()),
                tour.getTransportType(),
                tour.getDistance(),
                tour.getEstimatedTime(),
                tour.getImageUrl()
        );
        log.debug("Created TourDto {}", this);
    }
}
