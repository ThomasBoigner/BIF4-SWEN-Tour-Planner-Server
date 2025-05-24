package at.fhtw.tourplanner.domain.util;

import org.junit.jupiter.api.Test;

import static at.fhtw.tourplanner.domain.util.EnsurerFactory.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LongEnsurerTest {

    @Test
    void verifyLongEnsurerIsNotNullGoodCases(){
        // Given
        Long value = 1L;
        String name = "B";

        // Then
        assertThat(when(value, name).isNotNull().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyLongEnsurerIsPositiveGoodCases(){
        // Given
        Long value = 1L;
        String name = "A";

        // Then
        assertThat(when(value, name).isPositive().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyLongEnsurerIsNegativeGoodCases(){
        // Given
        long value = -1L;

        // Then
        assertThat(when(value).isNegative().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyLongEnsurerIsNotNullExceptionGetsThrown(){
        // Given
        Long value = null;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> when(value).isNotNull());

        // Then
        assertEquals("value must not be null!", exception.getMessage());
    }

    @Test
    void verifyLongEnsurerIsPositiveExceptionGetsThrown(){
        // Given
        long value = -1;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> when(value).isPositive());

        // Then
        assertEquals("value must be positive but was -1", exception.getMessage());
    }

    @Test
    void verifyLongEnsurerIsNegativeExceptionGetsThrown(){
        // Given
        long value = 1;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> when(value).isNegative());

        // Then
        assertEquals("value must be negative but was 1", exception.getMessage());
    }
}
