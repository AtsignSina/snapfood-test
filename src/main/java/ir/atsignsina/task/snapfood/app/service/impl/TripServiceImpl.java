package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.api.dto.GenerateTripRequestBody;
import ir.atsignsina.task.snapfood.api.dto.UpdateTripStatusRequestBody;
import ir.atsignsina.task.snapfood.app.service.OrderService;
import ir.atsignsina.task.snapfood.app.service.TripService;
import ir.atsignsina.task.snapfood.domain.order.Order;
import ir.atsignsina.task.snapfood.domain.trip.Trip;
import ir.atsignsina.task.snapfood.domain.trip.TripRepository;
import ir.atsignsina.task.snapfood.domain.trip.TripStatus;
import ir.atsignsina.task.snapfood.domain.trip.error.TripAlreadyExistsException;
import ir.atsignsina.task.snapfood.domain.trip.error.TripNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the TripService interface.
 */
@Component
@AllArgsConstructor
public class TripServiceImpl implements TripService {
    private final OrderService orderService;
    private final TripRepository tripRepository;

    @Override
    public Trip generateTrip(GenerateTripRequestBody body) {
        Order order = orderService.get(body.getOrderId());
        Optional<Trip> trip = tripRepository.findByOrder(order);
        if (trip.isPresent()) {
            throw new TripAlreadyExistsException()
                    .id(trip.get().getId())
                    .orderId(body.getOrderId());
        }
        Trip t = new Trip();
        t.setOrder(order);
        t.setStatus(TripStatus.ASSIGNED);
        return tripRepository.save(t);
    }

    @Override
    public Trip updateTripStatus(UUID tripId, UpdateTripStatusRequestBody body) {
        Trip trip = get(tripId);
        trip.setStatus(body.getStatus());
        tripRepository.save(trip);
        return trip;
    }

    public Trip get(UUID id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException().tripId(id));
    }
}
