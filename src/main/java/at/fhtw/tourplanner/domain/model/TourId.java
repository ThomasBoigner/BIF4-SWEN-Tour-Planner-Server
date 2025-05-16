package at.fhtw.tourplanner.domain.model;

import lombok.Builder;

import java.util.Objects;
import java.util.UUID;

@Builder
public record TourId(UUID id) {
    public TourId {
        Objects.requireNonNull(id, "tour id must not be null");
    }

    public TourId() {
        this(UUID.randomUUID());
    }
}
