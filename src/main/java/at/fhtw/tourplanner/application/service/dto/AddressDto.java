package at.fhtw.tourplanner.application.service.dto;

import at.fhtw.tourplanner.domain.model.Address;

public record AddressDto(
        String country,
        String city,
        int zipCode,
        String streetName,
        String streetNumber) {

    public AddressDto(Address address) {
        this(address.country(),
                address.city(),
                address.zipCode(),
                address.streetName(),
                address.streetNumber()
        );
    }
}
