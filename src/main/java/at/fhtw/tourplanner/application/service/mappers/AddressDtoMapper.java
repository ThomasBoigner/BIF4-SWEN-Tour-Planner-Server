package at.fhtw.tourplanner.application.service.mappers;

import at.fhtw.tourplanner.application.service.dto.AddressDto;
import at.fhtw.tourplanner.domain.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressDtoMapper extends AbstractDtoMapper<Address, AddressDto> {

    @Override
    public AddressDto toDto(Address domainObject) {
        return AddressDto.builder()
                .country(domainObject.country())
                .city(domainObject.city())
                .zipCode(domainObject.zipCode())
                .streetName(domainObject.streetName())
                .streetNumber(domainObject.streetNumber())
                .build();
    }
}
