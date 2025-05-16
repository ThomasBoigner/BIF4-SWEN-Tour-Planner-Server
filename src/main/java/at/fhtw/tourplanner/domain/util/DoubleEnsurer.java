package at.fhtw.tourplanner.domain.util;

public record DoubleEnsurer(Double value, String name) implements Ensurer<DoubleEnsurer, Double> {

    public DoubleEnsurer isNotNull() {
        if (value == null)
            throw new IllegalArgumentException(String.format("%s must not be null!", name));
        return this;
    }

    public DoubleEnsurer isPositive() {
        if (value < 0)
            throw new IllegalArgumentException(
                    String.format("%s must be positive but was %f", name, value)
            );
        return this;
    }

    public DoubleEnsurer isNegative() {
        if (value > 0)
            throw new IllegalArgumentException(
                    String.format("%s must be negative but was %f", name, value)
            );
        return this;
    }
}
