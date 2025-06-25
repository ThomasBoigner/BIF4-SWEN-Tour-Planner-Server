package at.fhtw.tourplanner.application.service.mappers;

import at.fhtw.tourplanner.application.service.dto.BackupTourDto;
import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TourLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor

@Component
public class BackupTourDtoMapper {
    private final AddressDtoMapper addressDtoMapper;
    private final BackupTourLogDtoMapper backupTourLogDtoMapper;

    public BackupTourDto toDto(Tour tour, List<TourLog> tourLogs) {
        return BackupTourDto.builder()
                .name(tour.getName())
                .description(tour.getDescription())
                .from(addressDtoMapper.toDto(tour.getFrom()))
                .to(addressDtoMapper.toDto(tour.getTo()))
                .transportType(tour.getTransportType())
                .distance(tour.getDistance())
                .estimatedTime(tour.getEstimatedTime())
                .tourLogs(backupTourLogDtoMapper.toDtos(tourLogs))
                .build();
    }
}
