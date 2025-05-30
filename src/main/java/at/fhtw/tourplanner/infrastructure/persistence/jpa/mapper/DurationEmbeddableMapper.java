package at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper;

import at.fhtw.tourplanner.domain.model.Duration;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.model.DurationEmbeddable;
import org.springframework.stereotype.Component;

@Component
public class DurationEmbeddableMapper extends AbstractEntityMapper<Duration, DurationEmbeddable> {
    @Override
    public Duration toDomainObject(DurationEmbeddable entity) {
        return Duration.builder()
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }

    @Override
    public DurationEmbeddable toEntity(Duration domainObject) {
        return DurationEmbeddable.builder()
                .startTime(domainObject.startTime())
                .endTime(domainObject.endTime())
                .build();
    }
}
