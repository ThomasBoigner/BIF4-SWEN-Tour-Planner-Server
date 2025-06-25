package at.fhtw.tourplanner.application.service.dto;

import at.fhtw.tourplanner.domain.model.TransportType;
import lombok.Builder;

import java.util.List;

@Builder
public record BackupTourDto(
        String name,
        String description,
        AddressDto from,
        AddressDto to,
        TransportType transportType,
        double distance,
        double estimatedTime,
        List<BackupTourLogDto> tourLogs
) { }
