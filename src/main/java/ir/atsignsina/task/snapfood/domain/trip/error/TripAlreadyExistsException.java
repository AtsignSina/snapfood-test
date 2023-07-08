package ir.atsignsina.task.snapfood.domain.trip.error;

import ir.atsignsina.task.snapfood.domain.SnappfoodException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.CONFLICT)
public class TripAlreadyExistsException extends SnappfoodException {
    private static final String MESSAGE = "trip.alreadyExists";

    public TripAlreadyExistsException() {
        super(MESSAGE);
    }

    public TripAlreadyExistsException id(UUID id) {
        this.param("tripId", id);
        return this;
    }

    public TripAlreadyExistsException orderId(UUID id) {
        this.param("orderId", id);
        return this;
    }
}
