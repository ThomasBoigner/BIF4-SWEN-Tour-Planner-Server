package at.fhtw.tourplanner.domain.util;

import org.junit.jupiter.api.Test;

import static at.fhtw.tourplanner.domain.util.EnsurerFactory.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoubleEnsurerTest {
    @Test
    void verifyDoubleEnsurerIsNotNullGoodCases(){
        // Given
        Double value = 1.0;
        String name = "B";

        // Then
        assertThat(when(value, name).isNotNull().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyDoubleEnsurerIsPositiveGoodCases(){
        // Given
        Double value = 1.0;
        String name = "A";

        // Then
        assertThat(when(value, name).isPositive().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyDoubleEnsurerIsNegativeGoodCases(){
        // Given
        Double value = -1.0;

        // Then
        assertThat(when(value).isNegative().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyDoubleEnsurerIsNotNullExceptionGetsThrown(){
        // Given
        Double value = null;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> when(value).isNotNull());

        // Then
        assertEquals("value must not be null!", exception.getMessage());
    }

    @Test
    void verifyDoubleEnsurerIsPositiveExceptionGetsThrown(){
        // Given
        Double value = -1.0;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> when(value).isPositive());

        // Then
        assertEquals("value must be positive but was -1.000000", exception.getMessage());
    }

    @Test
    void verifyDoubleEnsurerIsNegativeExceptionGetsThrown(){
        // Given
        Double value = 1.0;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> when(value).isNegative());

        // Then
        assertEquals("value must be negative but was 1.000000", exception.getMessage());
    }
}
