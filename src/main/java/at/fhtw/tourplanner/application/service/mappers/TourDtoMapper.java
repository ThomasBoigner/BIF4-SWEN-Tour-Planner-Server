package at.fhtw.tourplanner.application.service.mappers;

import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.Tour;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class TourDtoMapper extends AbstractDtoMapper<Tour, TourDto> {
    private final AddressDtoMapper addressDtoMapper;

    @Override
    public TourDto toDto(Tour domainObject) {
        return TourDto.builder()
                .id(domainObject.getId().id())
                .name(domainObject.getName())
                .description(domainObject.getDescription())
                .from(addressDtoMapper.toDto(domainObject.getFrom()))
                .to(addressDtoMapper.toDto(domainObject.getTo()))
                .transportType(domainObject.getTransportType())
                .distance(domainObject.getDistance())
                .estimatedTime(domainObject.getEstimatedTime())
                .imageUrl(domainObject.getImageUrl())
                .build();
    }
}
