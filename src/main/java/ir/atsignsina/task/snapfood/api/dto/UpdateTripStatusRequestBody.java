package ir.atsignsina.task.snapfood.api.dto;

import ir.atsignsina.task.snapfood.domain.trip.TripStatus;
import lombok.Data;

@Data
public class UpdateTripStatusRequestBody {
    private TripStatus status;
}
