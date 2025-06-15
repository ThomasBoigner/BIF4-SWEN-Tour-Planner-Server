package at.fhtw.tourplanner.infrastructure.persistence.jpa.model;

import at.fhtw.tourplanner.domain.model.TransportType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class TourEntity {
    @Id
    private UUID id;
    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "tour"
    )
    private List<TourLogEntity> tourLogs;
    private String name;
    private String description;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "country", column = @Column(name = "from_country")),
        @AttributeOverride(name = "city", column = @Column(name = "from_city")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "from_zip_code")),
        @AttributeOverride(name = "streetName", column = @Column(name = "from_street_name")),
        @AttributeOverride(name = "streetNumber", column = @Column(name = "from_street_number")),
        @AttributeOverride(name = "latitude", column = @Column(name = "from_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "from_longitude"))
    })
    private AddressEmbeddable from;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "country", column = @Column(name = "to_country")),
        @AttributeOverride(name = "city", column = @Column(name = "to_city")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "to_zip_code")),
        @AttributeOverride(name = "streetName", column = @Column(name = "to_street_name")),
        @AttributeOverride(name = "streetNumber", column = @Column(name = "to_street_number")),
        @AttributeOverride(name = "latitude", column = @Column(name = "to_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "to_longitude"))
    })
    private AddressEmbeddable to;
    private TransportType transportType;
    private double distance;
    private double estimatedTime;
    private String imageUrl;
}
