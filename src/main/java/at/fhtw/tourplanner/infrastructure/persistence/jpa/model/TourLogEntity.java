package at.fhtw.tourplanner.infrastructure.persistence.jpa.model;

import at.fhtw.tourplanner.domain.model.Difficulty;
import at.fhtw.tourplanner.domain.model.Rating;
import at.fhtw.tourplanner.domain.model.TourLog;
import at.fhtw.tourplanner.domain.model.TourLogId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor

@Entity
public class TourLogEntity {
    @Id
    private UUID id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private TourEntity tour;
    @Embedded
    private DurationEmbeddable duration;
    private String comment;
    private int difficulty;
    private double distance;
    private int rating;

    public TourLogEntity(TourLog tourLog) {
        this.id = tourLog.getId().id();
        this.tour = new TourEntity(tourLog.getTour());
        this.duration = new DurationEmbeddable(tourLog.getDuration());
        this.comment = tourLog.getComment();
        this.difficulty = tourLog.getDifficulty().difficulty();
        this.distance = tourLog.getDistance();
        this.rating = tourLog.getRating().rating();
    }

    public TourLog tourLog() {
        return new TourLog(
                new TourLogId(this.id),
                this.tour.toTour(),
                this.duration.toDuration(),
                this.comment,
                new Difficulty(this.difficulty),
                this.distance,
                new Rating(this.rating)
        );
    }
}
