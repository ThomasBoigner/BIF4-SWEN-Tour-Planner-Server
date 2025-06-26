package at.fhtw.tourplanner.application.service.mappers;

import at.fhtw.tourplanner.application.service.dto.BackupTourLogDto;
import at.fhtw.tourplanner.domain.model.*;
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

    public TourLog toDomainObject(Tour tour, BackupTourLogDto backupTourLogDto) {
        return TourLog.builder()
                .tour(tour)
               .duration(Duration.builder()
                       .startTime(backupTourLogDto.duration().startTime())
                       .endTime(backupTourLogDto.duration().endTime())
                       .build())
               .comment(backupTourLogDto.comment())
               .difficulty(new Difficulty(backupTourLogDto.difficulty()))
               .distance(backupTourLogDto.distance())
               .rating(new Rating(backupTourLogDto.rating()))
               .build();
    }
}
