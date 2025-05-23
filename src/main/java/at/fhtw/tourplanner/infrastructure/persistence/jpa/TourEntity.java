package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TourId;
import at.fhtw.tourplanner.domain.model.TransportType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor

@Entity
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    private UUID id;
    private String name;
    private String description;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "country", column = @Column(name = "from_country")),
        @AttributeOverride(name = "city", column = @Column(name = "from_city")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "from_zip_code")),
        @AttributeOverride(name = "streetName", column = @Column(name = "from_street_name")),
        @AttributeOverride(name = "streetNumber", column = @Column(name = "from_street_number"))
    })
    private AddressEmbeddable from;
    @Embedded
    private AddressEmbeddable to;
    private TransportType transportType;
    private double distance;
    private double estimatedTime;
    private String imageUrl;

    public TourEntity(Tour tour) {
        this.id = tour.getId().id();
        this.name = tour.getName();
        this.description = tour.getDescription();
        this.from = new AddressEmbeddable(tour.getFrom());
        this.to = new AddressEmbeddable(tour.getTo());
        this.transportType = tour.getTransportType();
        this.distance = tour.getDistance();
        this.estimatedTime = tour.getEstimatedTime();
        this.imageUrl = tour.getImageUrl();
    }

    public Tour toTour() {
        return new Tour(
                new TourId(this.id),
                this.name,
                this.description,
                this.from.toAddress(),
                this.to.toAddress(),
                this.transportType,
                this.distance,
                this.estimatedTime,
                this.imageUrl
        );
    }
}
