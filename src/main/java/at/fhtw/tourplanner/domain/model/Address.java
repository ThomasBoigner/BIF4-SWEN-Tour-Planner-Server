package at.fhtw.tourplanner.domain.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import static at.fhtw.tourplanner.domain.util.EnsurerFactory.when;

@Slf4j
@Builder
public record Address(
        String country,
        String city,
        int zipCode,
        String streetName,
        String streetNumber) {

    public Address {
        when(country, "country").isNotNull().and().isNotEmpty().and().isNotBlank().thenAssign();
        when(city, "city").isNotNull().and().isNotEmpty().and().isNotBlank().thenAssign();
        when(zipCode, "zip code").isPositive().thenAssign();
        when(streetName, "street name")
                .isNotNull().and()
                .isNotEmpty().and()
                .isNotBlank().thenAssign();
        when(streetNumber, "street number")
                .isNotNull().and()
                .isNotEmpty().and()
                .isNotBlank().thenAssign();

        log.debug("Created Address {}", this);
    }
}
