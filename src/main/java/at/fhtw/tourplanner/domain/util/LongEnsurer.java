package at.fhtw.tourplanner.domain.util;

public record LongEnsurer(Long value, String name) implements Ensurer<LongEnsurer, Long> {

    public LongEnsurer isNotNull() {
        if (value == null)
            throw new IllegalArgumentException(String.format("%s must not be null!", name));
        return this;
    }

    public LongEnsurer isPositive() {
        if (value < 0)
            throw new IllegalArgumentException(
                    String.format("%s must be positive but was %d", name, value)
            );
        return this;
    }

    public LongEnsurer isNegative() {
        if (value > 0)
            throw new IllegalArgumentException(
                    String.format("%s must be negative but was %d", name, value)
            );
        return this;
    }
}
