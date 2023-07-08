package ir.atsignsina.task.snapfood.api;

import ir.atsignsina.task.snapfood.api.dto.GenerateTripRequestBody;
import ir.atsignsina.task.snapfood.api.dto.UpdateTripStatusRequestBody;
import ir.atsignsina.task.snapfood.app.service.TripService;
import ir.atsignsina.task.snapfood.domain.trip.Trip;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("trip")
@AllArgsConstructor
public class TripController {
    private final TripService tripService;

    @PostMapping()
    public Trip generateTrip(@RequestBody GenerateTripRequestBody body) {
        return tripService.generateTrip(body);
    }

    @PutMapping("{id}")
    public Trip updateTripStatus(@PathVariable("id") UUID id, @RequestBody UpdateTripStatusRequestBody body) {
        return tripService.updateTripStatus(id,body);
    }
}
