package ir.atsignsina.task.snapfood.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class GenerateTripRequestBody {
    UUID orderId;
}
