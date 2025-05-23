package at.fhtw.tourplanner;

import at.fhtw.tourplanner.domain.model.Address;
import at.fhtw.tourplanner.domain.model.Tour;
import at.fhtw.tourplanner.domain.model.TourRepository;
import at.fhtw.tourplanner.domain.model.TransportType;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Bif4SwenTourPlannerServerApplication {

    @Autowired
    TourRepository tourRepository;

    Tour tour1 = Tour.builder()
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
            .build();

    Tour tour2 = Tour.builder()
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
            .build();

    Tour tour3 = Tour.builder()
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
            .build();

    public static void main(String[] args) {
        SpringApplication.run(Bif4SwenTourPlannerServerApplication.class, args);
    }

    @PostConstruct
    void initDB() {
        tourRepository.save(tour1);

        tourRepository.save(tour2);

        tourRepository.save(tour3);
    }
}
