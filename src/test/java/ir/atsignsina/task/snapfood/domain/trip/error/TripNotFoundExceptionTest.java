package ir.atsignsina.task.snapfood.domain.trip.error;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TripNotFoundExceptionTest {

    @Test
    void constructor_WithId_SetsIdInParameters() {
        UUID id = UUID.randomUUID();

        TripNotFoundException exception = new TripNotFoundException().tripId(id);

        assertNotNull(exception.getParameters());
        assertEquals(id, exception.getParameters().get("tripId"));
    }
}
