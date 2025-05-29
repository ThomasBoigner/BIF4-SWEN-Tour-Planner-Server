package at.fhtw.tourplanner.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static at.fhtw.tourplanner.domain.util.EnsurerFactory.when;


@Slf4j
@Getter
@ToString
@AllArgsConstructor
public class TourLog {
    private TourLogId id;
    private Tour tour;
    private Duration duration;
    private String comment;
    private Difficulty difficulty;
    private double distance;
    private Rating rating;

    @Builder
    public TourLog(Tour tour,
                   Duration duration,
                   String comment,
                   Difficulty difficulty,
                   double distance,
                   Rating rating) {
        this.id = new TourLogId();
        setTour(tour);
        setDuration(duration);
        setComment(comment);
        setDifficulty(difficulty);
        setDistance(distance);
        setRating(rating);

        log.debug("Created tour log {}", this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TourLog tourLog = (TourLog) o;
        return Objects.equals(id, tourLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void setTour(Tour tour) {
        Objects.requireNonNull(tour, "tour must not be null");
        this.tour = tour;
    }

    public void setDuration(Duration duration) {
        Objects.requireNonNull(duration, "duration must not be null");
        this.duration = duration;
    }

    public void setComment(String comment) {
        this.comment = when(comment, "comment")
                .isNotNull().and()
                .isNotEmpty().and()
                .isNotBlank().thenAssign();
    }

    public void setDifficulty(Difficulty difficulty) {
        Objects.requireNonNull(difficulty, "difficulty must not be null");
        this.difficulty = difficulty;
    }

    public void setDistance(double distance) {
        this.distance = when(distance, "distance").isPositive().thenAssign();
    }

    public void setRating(Rating rating) {
        Objects.requireNonNull(rating, "rating must not be null");
        this.rating = rating;
    }
}
