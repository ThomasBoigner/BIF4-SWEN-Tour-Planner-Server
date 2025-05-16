package at.fhtw.tourplanner.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Tour {
    private TourId id;
    private String name;
    private String description;

}
