package ir.atsignsina.task.snapfood.app.service;

import ir.atsignsina.task.snapfood.api.dto.GenerateTripRequestBody;
import ir.atsignsina.task.snapfood.api.dto.UpdateTripStatusRequestBody;
import ir.atsignsina.task.snapfood.domain.trip.Trip;

import java.util.UUID;

/**
 * The TripService interface provides methods for managing trips.
 */
public interface TripService extends DomainService<Trip> {
    /**
     * Generates a trip based on the specified request body.
     *
     * @param body The request body containing trip details.
     * @return The generated trip.
     */
    Trip generateTrip(GenerateTripRequestBody body);

    /**
     * Updates the status of the specified trip.
     *
     * @param tripId The ID of the trip to update.
     * @param body   The request body containing the updated status.
     * @return The updated trip.
     */
    Trip updateTripStatus(UUID tripId, UpdateTripStatusRequestBody body);
}