package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.domain.model.Address;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

@Embeddable
public class AddressEmbeddable {
    private String country;
    private String city;
    private int zipCode;
    private String streetName;
    private String streetNumber;

    public AddressEmbeddable(Address address) {
        this.country = address.country();
        this.city = address.city();
        this.zipCode = address.zipCode();
        this.streetName = address.streetName();
        this.streetNumber = address.streetNumber();
    }

    public Address toAddress() {
        return Address.builder()
                .country(country)
                .city(city)
                .zipCode(zipCode)
                .streetName(streetName)
                .streetNumber(streetNumber)
                .build();
    }
}
