package at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper;

import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.util.Page;
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
                entity.getEstimatedTime()
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
                .build();
    }

    public Page<Tour> toDomainPage(org.springframework.data.domain.Page<TourEntity> entityPage) {
        return Page.<Tour>builder()
                        .content(toDomainObjects(entityPage.getContent()))
                        .last(entityPage.isLast())
                        .totalPages(entityPage.getTotalPages())
                        .totalElements(entityPage.getTotalElements())
                        .first(entityPage.isFirst())
                        .size(entityPage.getSize())
                        .number(entityPage.getNumber())
                        .numberOfElements(entityPage.getNumberOfElements())
                        .empty(entityPage.isEmpty())
                        .build();
    }
}
