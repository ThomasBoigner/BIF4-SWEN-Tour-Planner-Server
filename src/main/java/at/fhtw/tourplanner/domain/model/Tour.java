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
public class Tour {
    private TourId id;
    private String name;
    private String description;
    private Address from;
    private Address to;
    private TransportType transportType;
    private double distance;
    private double estimatedTime;
    private String imageUrl;

    @Builder
    public Tour(String name,
                String description,
                Address from,
                Address to,
                TransportType transportType,
                double distance,
                double estimatedTime,
                String imageUrl) {
        this.id = new TourId();
        setName(name);
        setDescription(description);
        setFrom(from);
        setTo(to);
        setTransportType(transportType);
        this.distance = when(distance, "distance").isPositive().thenAssign();
        this.estimatedTime = when(estimatedTime, "estimated time").isPositive().thenAssign();
        this.imageUrl = when(imageUrl)
                .isNotNull().and()
                .isNotEmpty().and()
                .isNotBlank().thenAssign();

        log.debug("Created tour {}", this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(id, tour.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void setName(String name) {
        this.name = when(name, "tour name")
                .isNotNull().and()
                .isNotEmpty().and()
                .isNotBlank().thenAssign();
    }

    public void setDescription(String description) {
        this.description = when(description, "tour description")
                .isNotNull().and()
                .isNotEmpty().and()
                .isNotBlank().thenAssign();
    }

    public void setFrom(Address from) {
        Objects.requireNonNull(from, "from must not be null");
        this.from = from;
    }

    public void setTo(Address to) {
        Objects.requireNonNull(to, "to must not be null");
        this.to = to;
    }

    public void setTransportType(TransportType transportType) {
        Objects.requireNonNull(transportType, "transport type must not be null");
        this.transportType = transportType;
    }
}
