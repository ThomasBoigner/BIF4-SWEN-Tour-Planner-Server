package at.fhtw.tourplanner.domain.util;

public record ObjectEnsurer(Object value, String name) implements Ensurer<ObjectEnsurer, Object> {
    public ObjectEnsurer isNotNull() {
        if (value == null)
            throw new IllegalArgumentException(String.format("%s must not be null!", name));
        return this;
    }
}
