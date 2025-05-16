package at.fhtw.tourplanner.domain.util;

public record IntegerEnsurer(Integer value, String name)
        implements Ensurer<IntegerEnsurer, Integer>{

    public IntegerEnsurer isNotNull() {
        if (value == null)
            throw new IllegalArgumentException(String.format("%s must not be null!", name));
        return this;
    }

    public IntegerEnsurer isPositive(){
        if (value < 0)
            throw new IllegalArgumentException(
                    String.format("%s must be positive but was %d", name, value)
            );
        return this;
    }

    public IntegerEnsurer isNegative(){
        if (value > 0)
            throw new IllegalArgumentException(
                    String.format("%s must be negative but was %d", name, value)
            );
        return this;
    }
}
