package ir.atsignsina.task.snapfood.domain.trip.error;

import ir.atsignsina.task.snapfood.domain.SnappfoodException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TripNotFoundException extends SnappfoodException {
    private static final String MESSAGE = "trip.NotFound";

    public TripNotFoundException() {
        super(MESSAGE);
    }

    public TripNotFoundException tripId(UUID id) {
        this.param("tripId", id);
        return this;
    }
}
