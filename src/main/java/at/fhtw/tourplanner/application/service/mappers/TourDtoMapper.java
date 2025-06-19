package at.fhtw.tourplanner.application.service.mappers;

import at.fhtw.tourplanner.application.service.dto.TourDto;
import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.util.Page;
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
                .build();
    }

    public Page<TourDto> toDtoPage(Page<Tour> domainPage) {
        return Page.<TourDto>builder()
                .content(toDtos(domainPage.getContent()))
                .last(domainPage.isLast())
                .totalPages(domainPage.getTotalPages())
                .totalElements(domainPage.getTotalElements())
                .first(domainPage.isFirst())
                .size(domainPage.getSize())
                .number(domainPage.getNumber())
                .numberOfElements(domainPage.getNumberOfElements())
                .empty(domainPage.isEmpty())
                .build();
    }
}
