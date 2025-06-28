package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.domain.model.*;
import at.fhtw.tourplanner.domain.util.Page;
import at.fhtw.tourplanner.infrastructure.persistence.jpa.mapper.TourEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor

@Repository
public class JpaTourRepository implements TourRepository {
    private final TourEntityRepository tourEntityRepository;
    private final TourEntityMapper tourEntityMapper;

    @Override
    public Tour save(Tour tour) {
        tourEntityRepository.save(tourEntityMapper.toEntity(tour));
        return tour;
    }

    @Override
    public boolean existsTourByName(String name) {
        return tourEntityRepository.existsTourByName(name);
    }

    @Override
    public Page<Tour> findAll(int page, int size, String sortBy) {
        return tourEntityMapper.toDomainPage(tourEntityRepository
                .findAll(PageRequest.of(page, size, Sort.by(Sort.Order.asc(sortBy)))));
    }

    @Override
    public Page<Tour> findAllByNameLike(String name, int page, int size, String sortBy) {
        return tourEntityMapper.toDomainPage(tourEntityRepository
                .findAllByNameLike(
                        "%%%s%%".formatted(name),
                        PageRequest.of(
                                page,
                                size,
                                Sort.by(Sort.Order.asc(sortBy)
                        ))));
    }

    @Override
    public Optional<Tour> findTourById(TourId id) {
        return tourEntityRepository
                .findTourEntityById(id.id())
                .map(tourEntityMapper::toDomainObject);
    }

    @Override
    public void deleteTourById(TourId id) {
        tourEntityRepository.deleteTourEntityById(id.id());
    }
}
