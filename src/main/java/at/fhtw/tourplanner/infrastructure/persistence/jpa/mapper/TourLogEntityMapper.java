package at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper;

import at.fhtw.tourplanner.domain.model.Difficulty;
import at.fhtw.tourplanner.domain.model.Rating;
import at.fhtw.tourplanner.domain.model.TourLog;
import at.fhtw.tourplanner.domain.model.TourLogId;
import at.fhtw.tourplanner.domain.util.Page;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.TourLogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class TourLogEntityMapper extends AbstractEntityMapper<TourLog, TourLogEntity> {
    private final TourEntityMapper tourEntityMapper;
    private final DurationEmbeddableMapper durationEmbeddableMapper;


    @Override
    public TourLog toDomainObject(TourLogEntity entity) {
        return new TourLog(
                new TourLogId(entity.getId()),
                tourEntityMapper.toDomainObject(entity.getTour()),
                durationEmbeddableMapper.toDomainObject(entity.getDuration()),
                entity.getComment(),
                new Difficulty(entity.getDifficulty()),
                entity.getDistance(),
                new Rating(entity.getRating())
        );
    }

    @Override
    public TourLogEntity toEntity(TourLog domainObject) {
        return TourLogEntity.builder()
                .id(domainObject.getId().id())
                .tour(tourEntityMapper.toEntity(domainObject.getTour()))
                .duration(durationEmbeddableMapper.toEntity(domainObject.getDuration()))
                .comment(domainObject.getComment())
                .difficulty(domainObject.getDifficulty().difficulty())
                .distance(domainObject.getDistance())
                .rating(domainObject.getRating().rating())
                .build();
    }

    public Page<TourLog> toDomainPage(
            org.springframework.data.domain.Page<TourLogEntity> entityPage) {
        return Page.<TourLog>builder()
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
