package at.fhtw.tourplanner.domain.util;

import org.junit.jupiter.api.Test;

import static at.fhtw.tourplanner.domain.util.EnsurerFactory.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectEnsurerTest {

    @Test
    void verifyObjectEnsurer_isNotNull_goodCases(){
        // Given
        Object value = new Object();
        String name = "B";

        // Then
        assertThat(when(value, name).isNotNull().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyObjectEnsurer_isNotNull_exceptionGetsThrown(){
        // Given
        String value = null;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> when(value).isNotNull());

        // Then
        assertEquals("value must not be null!", exception.getMessage());
    }
}
