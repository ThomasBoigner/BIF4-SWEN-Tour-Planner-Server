package at.fhtw.tourplanner.domain.util;

import org.junit.jupiter.api.Test;

import static at.fhtw.tourplanner.domain.util.EnsurerFactory.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringEnsurerTest {

    @Test
    void verifyStringEnsurer_isNotNull_goodCases(){
        // Given
        String value = "A";
        String name = "B";

        // Then
        assertThat(when(value, name).isNotNull().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyStringEnsurer_isNotEmpty_goodCases(){
        // Given
        String value = "A";

        // Then
        assertThat(when(value).isNotEmpty().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyStringEnsurer_isNotBlank_goodCases(){
        // Given
        String value = "A";

        // Then
        assertThat(when(value).isNotBlank().thenAssign()).isSameAs(value);
    }

    @Test
    void verifyStringEnsurer_contains_goodCases(){
        // Given
        String value = "ABC";
        String partial = "A";

        // Then
        assertThat(when(value).contains(partial).thenAssign()).isSameAs(value);
    }

    @Test
    void verifyStringEnsurer_isNotNull_exceptionGetsThrown(){
        // Given
        String value = null;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> when(value).isNotNull());

        // Then
        assertEquals("value must not be null!", exception.getMessage());
    }



    @Test
    void verifyStringEnsurer_isNotEmpty_exceptionGetsThrown(){
        // Given
        String value = "";

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> when(value).isNotEmpty());

        // Then
        assertEquals("value must not be empty!", exception.getMessage());
    }

    @Test
    void verifyStringEnsurer_isNotBlank_exceptionGetsThrown(){
        // Given
        String value = " ";

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> when(value).isNotBlank());

        // Then
        assertEquals("value must not be blank!", exception.getMessage());
    }

    @Test
    void verifyStringEnsurer_contains_exceptionGetsThrown() {
        // Given
        String value = "ABC";
        String partial = "D";

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> when(value).contains(partial));

        // Then
        assertEquals("value should contain 'D'", exception.getMessage());
    }

    @Test
    void verifyStringEnsurer_contains_PartialNullExceptionGetsThrown() {
        // Given
        String value = "ABC";
        String partial = null;

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> when(value).contains(partial));

        // Then
        assertEquals("Partial must not be null!", exception.getMessage());
    }

}
