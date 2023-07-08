package ir.atsignsina.task.snapfood.domain.queue.error;

import ir.atsignsina.task.snapfood.domain.SnappfoodException;

import java.util.UUID;

public class DelayQueueIsNotFoundException extends SnappfoodException {
    private static final String MESSAGE = "delayQueue.NotFound";

    public DelayQueueIsNotFoundException() {
        super(MESSAGE);

    }

    public DelayQueueIsNotFoundException agentId(UUID agentId) {
        this.param("agentId", agentId);
        return this;
    }
}
