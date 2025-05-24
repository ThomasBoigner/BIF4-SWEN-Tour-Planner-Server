package at.fhtw.tourplanner.domain.util;

import org.junit.jupiter.api.Test;

import static at.fhtw.tourplanner.domain.util.EnsurerFactory.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerEnsurerTest {
    @Test
    void verifyIntegerEnsurerIsNotNullGoodCases(){
        // Given
        Integer value = 1;
        String name = "B";

        // Then
        assertThat(when(value, name).isNotNull().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyIntegerEnsurerIsPositiveGoodCases(){
        // Given
        int value = 1;
        String name = "A";

        // Then
        assertThat(when(value, name).isPositive().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyIntegerEnsurerIsNegativeGoodCases(){
        // Given
        int value = -1;

        // Then
        assertThat(when(value).isNegative().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyLongEnsurerIsNotNullExceptionGetsThrown(){
        // Given
        Integer value = null;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> when(value).isNotNull());

        // Then
        assertEquals("value must not be null!", exception.getMessage());
    }

    @Test
    void verifyIntegerEnsurerIsPositiveExceptionGetsThrown(){
        // Given
        int value = -1;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> when(value).isPositive());

        // Then
        assertEquals("value must be positive but was -1", exception.getMessage());
    }

    @Test
    void verifyIntegerEnsurerIsNegativeExceptionGetsThrown(){
        // Given
        int value = 1;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> when(value).isNegative());

        // Then
        assertEquals("value must be negative but was 1", exception.getMessage());
    }
}
