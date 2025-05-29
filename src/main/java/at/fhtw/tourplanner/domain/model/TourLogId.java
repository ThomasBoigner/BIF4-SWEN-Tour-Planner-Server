package at.fhtw.tourplanner.domain.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Builder
public record TourLogId(UUID id) {
    public TourLogId {
        Objects.requireNonNull(id, "tour log id must not be null");
    }

    public TourLogId() {
        this(UUID.randomUUID());
        log.debug("Created tour log id: {}", this);
    }
}
