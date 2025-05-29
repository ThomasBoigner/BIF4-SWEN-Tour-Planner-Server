package at.fhtw.tourplanner.application.service.commands;

import at.fhtw.tourplanner.domain.model.TransportType;
import lombok.Builder;

@Builder
public record UpdateTourCommand(
        String name,
        String description,
        CreateAddressCommand from,
        CreateAddressCommand to,
        TransportType transportType
) {
}
