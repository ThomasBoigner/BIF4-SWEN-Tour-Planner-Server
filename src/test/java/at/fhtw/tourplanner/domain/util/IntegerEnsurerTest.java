package at.fhtw.tourplanner.domain.util;

import org.junit.jupiter.api.Test;

import static at.fhtw.tourplanner.domain.util.EnsurerFactory.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerEnsurerTest {
    @Test
    void verifyIntegerEnsurer_isPositive_goodCases(){
        // Given
        int value = 1;
        String name = "A";

        // Then
        assertThat(when(value, name).isPositive().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyIntegerEnsurer_isNegative_goodCases(){
        // Given
        int value = -1;

        // Then
        assertThat(when(value).isNegative().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyIntegerEnsurer_isPositive_exceptionGetsThrown(){
        // Given
        int value = -1;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> when(value).isPositive());

        // Then
        assertEquals("value must be positive but was -1", exception.getMessage());
    }

    @Test
    void verifyIntegerEnsurer_isNegative_exceptionGetsThrown(){
        // Given
        int value = 1;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> when(value).isNegative());

        // Then
        assertEquals("value must be negative but was 1", exception.getMessage());
    }
}
