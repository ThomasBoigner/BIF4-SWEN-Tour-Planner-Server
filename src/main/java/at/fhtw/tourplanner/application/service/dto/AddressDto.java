package at.fhtw.tourplanner.application.service.dto;

import lombok.Builder;

@Builder
public record AddressDto(
        String country,
        String city,
        int zipCode,
        String streetName,
        String streetNumber) { }
