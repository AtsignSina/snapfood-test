package ir.atsignsina.task.snapfood.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReassignDelayQueueRequestBody {
    private UUID orderId;
    private UUID agentId;
}
