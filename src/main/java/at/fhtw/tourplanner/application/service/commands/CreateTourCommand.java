package at.fhtw.tourplanner.application.service.commands;

import at.fhtw.tourplanner.domain.model.TransportType;
import lombok.Builder;

@Builder
public record CreateTourCommand(
        String name,
        String description,
        CreateAddressCommand from,
        CreateAddressCommand to,
        TransportType transportType
) { }
