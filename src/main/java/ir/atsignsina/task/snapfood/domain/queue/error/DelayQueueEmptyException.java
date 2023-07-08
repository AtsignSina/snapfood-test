package ir.atsignsina.task.snapfood.domain.queue.error;

import ir.atsignsina.task.snapfood.domain.SnappfoodException;

public class DelayQueueEmptyException extends SnappfoodException {
    private static final String MESSAGE = "delayQueue.empty";

    public DelayQueueEmptyException() {
        super(MESSAGE);

    }
}
