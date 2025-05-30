package at.fhtw.tourplanner.infrastructure.persistence.jpa.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Embeddable
public class AddressEmbeddable {
    private String country;
    private String city;
    private int zipCode;
    private String streetName;
    private String streetNumber;
}
