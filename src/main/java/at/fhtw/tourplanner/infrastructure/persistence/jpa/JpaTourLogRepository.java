package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.domain.model.*;
import at.fhtw.tourplanner.domain.util.Page;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper.TourLogEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Repository
public class JpaTourLogRepository implements TourLogRepository {
    private final TourLogEntityRepository tourLogEntityRepository;
    private final TourLogEntityMapper tourLogEntityMapper;

    @Override
    public TourLog save(TourLog tourLog) {
        tourLogEntityRepository.save(tourLogEntityMapper.toEntity(tourLog));
        return tourLog;
    }

    @Override
    public Page<TourLog> findAllByTourId(TourId tourId, int page, int size, String sortBy) {
        return tourLogEntityMapper.toDomainPage(tourLogEntityRepository.findAllByTourId(
                tourId.id(),
                PageRequest.of(page, size, Sort.by(Sort.Order.asc(sortBy))))
        );
    }

    @Override
    public List<TourLog> findAllByTourId(TourId tourId) {
        return tourLogEntityMapper.toDomainObjects(
                tourLogEntityRepository.findAllByTourId(tourId.id())
        );
    }

    @Override
    public Page<TourLog> findAllByTourIdAndCommentLike(
            TourId tourId, String comment, int page, int size, String sortBy) {
        return tourLogEntityMapper.toDomainPage(tourLogEntityRepository
                .findAllByTourIdAndCommentLike(
                        tourId.id(),
                        "%%%s%%".formatted(comment),
                        PageRequest.of(page, size, Sort.by(Sort.Order.asc(sortBy)))
                ));
    }


    @Override
    public Optional<TourLog> findTourEntityById(TourLogId id) {
        return tourLogEntityRepository
                .findById(id.id())
                .map(tourLogEntityMapper::toDomainObject);
    }

    @Override
    public void deleteTourLogById(TourLogId id) {
        tourLogEntityRepository.deleteTourLogEntityById(id.id());
    }
}
