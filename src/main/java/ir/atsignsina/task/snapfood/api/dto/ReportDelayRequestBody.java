package ir.atsignsina.task.snapfood.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReportDelayRequestBody {
    private UUID orderId;
}
