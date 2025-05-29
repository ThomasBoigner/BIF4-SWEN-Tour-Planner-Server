package at.fhtw.tourplanner.application.service.commands;

import lombok.Builder;

@Builder
public record UpdateAddressCommand(
        String country,
        String city,
        int zipCode,
        String streetName,
        String streetNumber
) { }
