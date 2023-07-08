package ir.atsignsina.task.snapfood.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnappfoodExceptionTest {

    @Test
    void constructor_WithMessage_SetsMessage() {
        String message = "Test";

        SnappfoodException exception = new SnappfoodException(message) {
        };

        assertEquals(message, exception.getMessage());
    }
}
