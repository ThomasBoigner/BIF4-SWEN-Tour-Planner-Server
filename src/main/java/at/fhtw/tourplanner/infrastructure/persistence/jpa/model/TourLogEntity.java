package at.fhtw.tourplanner.infrastructure.persistence.jpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class TourLogEntity {
    @Id
    private UUID id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_tour"))
    private TourEntity tour;
    @Embedded
    private DurationEmbeddable duration;
    private String comment;
    private int difficulty;
    private double distance;
    private int rating;
}
