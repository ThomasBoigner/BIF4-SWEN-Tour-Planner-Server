package at.fhtw.tourplanner.domain.model;

import java.util.Objects;
import java.util.UUID;

public record TourId(UUID id) {
    public TourId {
        Objects.requireNonNull(id, "tour id must not be null");
    }

    public TourId() {
        this(UUID.randomUUID());
    }
}
