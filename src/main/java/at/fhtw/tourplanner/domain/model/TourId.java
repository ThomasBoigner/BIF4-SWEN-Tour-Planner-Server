package at.fhtw.tourplanner.domain.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Builder
public record TourId(UUID id) {
    public TourId {
        Objects.requireNonNull(id, "tour id must not be null");
    }

    public TourId() {
        this(UUID.randomUUID());
        log.debug("Created Tour Id: {}", this);
    }
}
