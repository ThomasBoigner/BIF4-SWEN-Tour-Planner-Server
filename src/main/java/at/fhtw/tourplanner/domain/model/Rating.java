package at.fhtw.tourplanner.domain.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record Rating(int rating) {
    public Rating {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        log.debug("Created Rating {}", this);
    }
}
