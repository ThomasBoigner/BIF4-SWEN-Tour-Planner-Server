package at.fhtw.tourplanner.application.service.mappers;

import at.fhtw.tourplanner.application.service.dto.TourLogDto;
import at.fhtw.tourplanner.domain.model.TourLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class TourLogDtoMapper extends AbstractDtoMapper<TourLog, TourLogDto> {
    private final DurationDtoMapper durationDtoMapper;

    @Override
    public TourLogDto toDto(TourLog domainObject) {
        return TourLogDto.builder()
                .id(domainObject.getId().id())
                .tourId(domainObject.getTour().getId().id())
                .duration(durationDtoMapper.toDto(domainObject.getDuration()))
                .comment(domainObject.getComment())
                .difficulty(domainObject.getDifficulty().difficulty())
                .distance(domainObject.getDistance())
                .rating(domainObject.getRating().rating())
                .build();
    }
}
