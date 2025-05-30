package at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper;

import at.fhtw.tourplanner.domain.model.Address;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.AddressEmbeddable;
import org.springframework.stereotype.Component;

@Component
public class AddressEmbeddableMapper extends AbstractEntityMapper<Address, AddressEmbeddable> {
    @Override
    public Address toDomainObject(AddressEmbeddable entity) {
        return Address.builder()
                .country(entity.getCountry())
                .city(entity.getCity())
                .zipCode(entity.getZipCode())
                .streetName(entity.getStreetName())
                .streetNumber(entity.getStreetNumber())
                .build();
    }

    @Override
    public AddressEmbeddable toEntity(Address domainObject) {
        return AddressEmbeddable.builder()
                .country(domainObject.country())
                .city(domainObject.city())
                .zipCode(domainObject.zipCode())
                .streetName(domainObject.streetName())
                .streetNumber(domainObject.streetNumber())
                .build();
    }
}
