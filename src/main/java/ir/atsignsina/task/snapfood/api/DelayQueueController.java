package ir.atsignsina.task.snapfood.api;

import ir.atsignsina.task.snapfood.api.dto.DelayQueueAgentRequestBody;
import ir.atsignsina.task.snapfood.api.dto.ReassignDelayQueueRequestBody;
import ir.atsignsina.task.snapfood.app.service.DelayQueueService;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueue;
import ir.atsignsina.task.snapfood.domain.queue.VendorDelay;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("delay-queue")
@AllArgsConstructor
public class DelayQueueController {
    private final DelayQueueService delayQueueService;

    @PostMapping("ready")
    public DelayQueue firstQueued(@RequestBody DelayQueueAgentRequestBody body) {
        return delayQueueService.firstQueued(body.getAgentId());
    }

    @PostMapping("checked")
    public DelayQueue check(@RequestBody DelayQueueAgentRequestBody body) {
        return delayQueueService.check(body.getAgentId());
    }

    @GetMapping("current")
    public DelayQueue currentDelayQueue(@RequestBody DelayQueueAgentRequestBody body) {
        return delayQueueService.findCurrentDelayQueue(body.getAgentId());
    }

    @PutMapping("reassigned")
    public DelayQueue reassign(@RequestBody ReassignDelayQueueRequestBody body) {
        return delayQueueService.reassign(body);
    }

    @GetMapping("report")
    public Collection<VendorDelay> report() {
        return delayQueueService.vendorDelays();
    }
}
