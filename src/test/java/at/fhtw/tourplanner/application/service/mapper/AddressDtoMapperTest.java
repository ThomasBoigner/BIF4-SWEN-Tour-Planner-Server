package at.fhtw.tourplanner.application.service.mapper;

import at.fhtw.tourplanner.application.service.dto.AddressDto;
import at.fhtw.tourplanner.application.service.mappers.AddressDtoMapper;
import at.fhtw.tourplanner.domain.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressDtoMapperTest {
    private AddressDtoMapper addressDtoMapper;

    @BeforeEach
    void setUp() {
        addressDtoMapper = new AddressDtoMapper();
    }

    @Test
    void ensureAddressDtoMapperToDtoWorksProperly() {
        // Given
        Address address = Address.builder()
                .streetName("Austria")
                .city("Deutsch Wagram")
                .zipCode(2232)
                .streetName("Radetzkystraße")
                .streetNumber("2-6")
                .country("Austria")
                .build();

        // When
        AddressDto addressDto = addressDtoMapper.toDto(address);

        // Then
        assertThat(addressDto.streetName()).isEqualTo(address.streetName());
        assertThat(addressDto.city()).isEqualTo(address.city());
        assertThat(addressDto.zipCode()).isEqualTo(address.zipCode());
        assertThat(addressDto.streetName()).isEqualTo(address.streetName());
        assertThat(addressDto.streetNumber()).isEqualTo(address.streetNumber());
        assertThat(addressDto.country()).isEqualTo(address.country());
    }
}
