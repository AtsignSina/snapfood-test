package ir.atsignsina.task.snapfood.domain.order.error;

import ir.atsignsina.task.snapfood.domain.SnappfoodException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderEstimatedDeliveryTimeIsNotPassedYetException extends SnappfoodException {
    private static final String MESSAGE = "order.estimatedIsNotPassed";

    public OrderEstimatedDeliveryTimeIsNotPassedYetException() {
        super(MESSAGE);
    }

    public OrderEstimatedDeliveryTimeIsNotPassedYetException orderId(UUID id) {
        this.param("orderId", id);
        return this;
    }
}
