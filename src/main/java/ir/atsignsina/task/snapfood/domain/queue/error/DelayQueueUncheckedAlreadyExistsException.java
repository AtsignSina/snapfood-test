package ir.atsignsina.task.snapfood.domain.queue.error;

import ir.atsignsina.task.snapfood.domain.SnappfoodException;

import java.util.UUID;

public class DelayQueueUncheckedAlreadyExistsException extends SnappfoodException {
    private static final String MESSAGE = "delayQueue.uncheckedAlreadyExists";

    public DelayQueueUncheckedAlreadyExistsException() {
        super(MESSAGE);

    }

    public DelayQueueUncheckedAlreadyExistsException delayQueueId(UUID queueId) {
        this.param("delayQueueId", queueId);
        return this;
    }

    public DelayQueueUncheckedAlreadyExistsException agentId(UUID agentId) {
        this.param("agentId", agentId);
        return this;
    }
}
