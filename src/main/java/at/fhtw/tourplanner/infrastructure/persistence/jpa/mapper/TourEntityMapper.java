package at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper;

import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class TourEntityMapper extends AbstractEntityMapper<Tour, TourEntity> {
    private final AddressEmbeddableMapper addressEmbeddableMapper;

    @Override
    public Tour toDomainObject(TourEntity entity) {
        return new Tour(
                new TourId(entity.getId()),
                entity.getName(),
                entity.getDescription(),
                addressEmbeddableMapper.toDomainObject(entity.getFrom()),
                addressEmbeddableMapper.toDomainObject(entity.getTo()),
                entity.getTransportType(),
                entity.getDistance(),
                entity.getEstimatedTime(),
                entity.getImageUrl()
        );
    }

    @Override
    public TourEntity toEntity(Tour domainObject) {
        return TourEntity.builder()
                .id(domainObject.getId().id())
                .name(domainObject.getName())
                .description(domainObject.getDescription())
                .from(addressEmbeddableMapper.toEntity(domainObject.getFrom()))
                .to(addressEmbeddableMapper.toEntity(domainObject.getTo()))
                .transportType(domainObject.getTransportType())
                .distance(domainObject.getDistance())
                .estimatedTime(domainObject.getEstimatedTime())
                .imageUrl(domainObject.getImageUrl())
                .build();
    }
}
