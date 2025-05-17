package at.fhtw.tourplanner.infrastructure.persistence.jpa;

import at.fhtw.tourplanner.domain.model.Address;
import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TourRepository;
import at.fhtw.tourplanner.domain.model.TransportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Repository
public class JpaTourRepository implements TourRepository {
    private List<Tour> tours = new ArrayList<>(List.of(
            Tour.builder()
                    .name("Tour 1")
                    .description("This tour is awesome")
                    .from(Address.builder()
                            .streetName("Austria")
                            .city("Deutsch Wagram")
                            .zipCode(2232)
                            .streetName("Radetzkystraße")
                            .streetNumber("2-6")
                            .country("Austria")
                            .build())
                    .to(Address.builder()
                            .streetName("Austria")
                            .city("Strasshof an der Nordbahn")
                            .zipCode(2231)
                            .streetName("Billroth-Gasse")
                            .streetNumber("5")
                            .country("Austria")
                            .build())
                    .transportType(TransportType.BIKE)
                    .distance(20)
                    .estimatedTime(120)
                    .imageUrl("img")
                    .build(),
            Tour.builder()
                    .name("Tour 2")
                    .description("This tour is awesome")
                    .from(Address.builder()
                            .streetName("Austria")
                            .city("Deutsch Wagram")
                            .zipCode(2232)
                            .streetName("Radetzkystraße")
                            .streetNumber("2-6")
                            .country("Austria")
                            .build())
                    .to(Address.builder()
                            .streetName("Austria")
                            .city("Strasshof an der Nordbahn")
                            .zipCode(2231)
                            .streetName("Billroth-Gasse")
                            .streetNumber("5")
                            .country("Austria")
                            .build())
                    .transportType(TransportType.RUNNING)
                    .distance(40)
                    .estimatedTime(240)
                    .imageUrl("img")
                    .build(),
            Tour.builder()
                    .name("Tour 3")
                    .description("This tour is awesome")
                    .from(Address.builder()
                            .streetName("Austria")
                            .city("Deutsch Wagram")
                            .zipCode(2232)
                            .streetName("Radetzkystraße")
                            .streetNumber("2-6")
                            .country("Austria")
                            .build())
                    .to(Address.builder()
                            .streetName("Austria")
                            .city("Strasshof an der Nordbahn")
                            .zipCode(2231)
                            .streetName("Billroth-Gasse")
                            .streetNumber("5")
                            .country("Austria")
                            .build())
                    .transportType(TransportType.BIKE)
                    .distance(30)
                    .estimatedTime(60)
                    .imageUrl("img")
                    .build()
    ));

    @Override
    public List<Tour> findAll() {
        return tours;
    }
}
