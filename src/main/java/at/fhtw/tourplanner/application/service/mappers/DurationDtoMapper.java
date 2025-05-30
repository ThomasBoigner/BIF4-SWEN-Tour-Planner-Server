package at.fhtw.tourplanner.application.service.mappers;

import at.fhtw.tourplanner.application.service.dto.DurationDto;
import at.fhtw.tourplanner.domain.model.Duration;
import org.springframework.stereotype.Component;

@Component
public class DurationDtoMapper extends AbstractDtoMapper<Duration, DurationDto> {
    @Override
    public DurationDto toDto(Duration domainObject) {
        return DurationDto.builder()
                .startTime(domainObject.startTime())
                .endTime(domainObject.endTime())
                .duration(domainObject.duration())
                .build();
    }
}
