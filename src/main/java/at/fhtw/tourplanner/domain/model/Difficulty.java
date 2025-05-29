package at.fhtw.tourplanner.domain.model;

public record Difficulty(int difficulty) {
    public Difficulty {
        if (difficulty < 1 || difficulty > 5) {
            throw new IllegalArgumentException("Difficulty must be between 1 and 5");
        }
    }
}
