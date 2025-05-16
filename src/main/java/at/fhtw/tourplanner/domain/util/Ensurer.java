package at.fhtw.tourplanner.domain.util;

public interface Ensurer<E extends Ensurer<E, V>, V> {

    V value();

    default E and(){
        return (E) this;
    }

    default V thenAssign(){
        return value();
    }
}
