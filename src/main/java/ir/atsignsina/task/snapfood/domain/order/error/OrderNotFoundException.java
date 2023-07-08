package ir.atsignsina.task.snapfood.domain.order.error;

import ir.atsignsina.task.snapfood.domain.SnappfoodException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends SnappfoodException {
    private static final String MESSAGE = "order.NotFound";

    public OrderNotFoundException() {
        super(MESSAGE);
    }

    public OrderNotFoundException orderId(UUID id) {
        this.param("orderId", id);
        return this;
    }
}
