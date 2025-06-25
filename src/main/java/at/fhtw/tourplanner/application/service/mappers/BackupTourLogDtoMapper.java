package at.fhtw.tourplanner.application.service.mappers;

import at.fhtw.tourplanner.application.service.dto.BackupTourLogDto;
import at.fhtw.tourplanner.domain.model.TourLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class BackupTourLogDtoMapper extends AbstractDtoMapper<TourLog, BackupTourLogDto> {
    private final DurationDtoMapper durationDtoMapper;

    @Override
    public BackupTourLogDto toDto(TourLog domainObject) {
        return BackupTourLogDto.builder()
                .duration(durationDtoMapper.toDto(domainObject.getDuration()))
                .comment(domainObject.getComment())
                .difficulty(domainObject.getDifficulty().difficulty())
                .distance(domainObject.getDistance())
                .rating(domainObject.getRating().rating())
                .build();
    }
}
