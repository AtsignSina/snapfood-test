package ir.atsignsina.task.snapfood.domain.order.error;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderNotFoundExceptionTest {

    @Test
    void constructor_WithId_SetsIdInParameters() {
        UUID id = UUID.randomUUID();

        OrderNotFoundException exception = new OrderNotFoundException().orderId(id);

        assertNotNull(exception.getParameters());
        assertEquals(id, exception.getParameters().get("orderId"));
    }
}

