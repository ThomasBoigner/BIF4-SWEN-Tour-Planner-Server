package at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper;

import at.fhtw.tourplanner.domain.model.Address;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.AddressEmbeddable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressEmbeddableMapperTest {
    private AddressEmbeddableMapper addressEmbeddableMapper;

    @BeforeEach
    void setUp() {
        addressEmbeddableMapper = new AddressEmbeddableMapper();
    }

    @Test
    void ensureAddressEmbeddableMapperToDomainObjectWorksProperly() {
        // Given
        AddressEmbeddable addressEmbeddable = AddressEmbeddable.builder()
                .streetName("Austria")
                .city("Deutsch Wagram")
                .zipCode(2232)
                .streetName("Radetzkystraße")
                .streetNumber("2-6")
                .country("Austria")
                .latitude(50)
                .longitude(60)
                .build();

        // When
        Address address = addressEmbeddableMapper.toDomainObject(addressEmbeddable);

        // Then
        assertThat(addressEmbeddable.getStreetName()).isEqualTo(address.streetName());
        assertThat(addressEmbeddable.getCity()).isEqualTo(address.city());
        assertThat(addressEmbeddable.getZipCode()).isEqualTo(address.zipCode());
        assertThat(addressEmbeddable.getStreetName()).isEqualTo(address.streetName());
        assertThat(addressEmbeddable.getStreetNumber()).isEqualTo(address.streetNumber());
        assertThat(addressEmbeddable.getCountry()).isEqualTo(address.country());
        assertThat(addressEmbeddable.getLatitude()).isEqualTo(address.latitude());
        assertThat(addressEmbeddable.getLongitude()).isEqualTo(address.longitude());
    }

    @Test
    void ensureAddressEmbeddableMapperToEntityWorksProperly(){
        // Given
        Address address = Address.builder()
                .streetName("Austria")
                .city("Deutsch Wagram")
                .zipCode(2232)
                .streetName("Radetzkystraße")
                .streetNumber("2-6")
                .country("Austria")
                .latitude(50)
                .longitude(60)
                .build();

        // When
        AddressEmbeddable addressEmbeddable = addressEmbeddableMapper.toEntity(address);

        // Then
        assertThat(addressEmbeddable.getStreetName()).isEqualTo(address.streetName());
        assertThat(addressEmbeddable.getCity()).isEqualTo(address.city());
        assertThat(addressEmbeddable.getZipCode()).isEqualTo(address.zipCode());
        assertThat(addressEmbeddable.getStreetName()).isEqualTo(address.streetName());
        assertThat(addressEmbeddable.getStreetNumber()).isEqualTo(address.streetNumber());
        assertThat(addressEmbeddable.getCountry()).isEqualTo(address.country());
        assertThat(addressEmbeddable.getLatitude()).isEqualTo(address.latitude());
        assertThat(addressEmbeddable.getLongitude()).isEqualTo(address.longitude());
    }
}
