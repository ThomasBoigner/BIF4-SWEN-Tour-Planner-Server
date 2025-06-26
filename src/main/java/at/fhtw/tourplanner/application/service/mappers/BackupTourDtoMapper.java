package at.fhtw.tourplanner.application.service.mappers;

import at.fhtw.tourplanner.application.service.dto.BackupTourDto;
import at.fhtw.tourplanner.domain.model.Address;
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

    public Tour toDomainObject(BackupTourDto backupTourDto) {
        return Tour.builder()
                .name(backupTourDto.name())
                .description(backupTourDto.description())
                .from(Address.builder()
                        .streetName(backupTourDto.from().streetName())
                        .streetNumber(backupTourDto.from().streetNumber())
                        .city(backupTourDto.from().city())
                        .country(backupTourDto.from().country())
                        .zipCode(backupTourDto.from().zipCode())
                        .longitude(backupTourDto.from().longitude())
                        .latitude(backupTourDto.from().latitude())
                        .build()
                )
                .to(Address.builder()
                        .streetName(backupTourDto.to().streetName())
                        .streetNumber(backupTourDto.to().streetNumber())
                        .city(backupTourDto.to().city())
                        .country(backupTourDto.to().country())
                        .zipCode(backupTourDto.to().zipCode())
                        .longitude(backupTourDto.to().longitude())
                        .latitude(backupTourDto.to().latitude())
                        .build()
                )
                .transportType(backupTourDto.transportType())
                .distance(backupTourDto.distance())
                .estimatedTime(backupTourDto.estimatedTime())
                .build();
    }
}
