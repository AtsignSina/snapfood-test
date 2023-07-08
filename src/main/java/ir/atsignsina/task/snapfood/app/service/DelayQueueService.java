package ir.atsignsina.task.snapfood.app.service;

import ir.atsignsina.task.snapfood.api.dto.ReassignDelayQueueRequestBody;
import ir.atsignsina.task.snapfood.domain.order.Order;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueue;
import ir.atsignsina.task.snapfood.domain.queue.VendorDelay;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The DelayQueueService interface provides methods for managing delay queues.
 */
public interface DelayQueueService {
    /**
     * Adds an order to the delay queue.
     *
     * @param order The order to add to the delay queue.
     * @return The created delay queue.
     */
    DelayQueue add(Order order);

    /**
     * Retrieves the first queued delay queue for the specified agent.
     *
     * @param agentId The ID of the agent.
     * @return The first queued delay queue for the agent.
     */
    DelayQueue firstQueued(UUID agentId);

    /**
     * Checks the specified delay queue.
     *
     * @param queueId The ID of the delay queue to check.
     * @return The checked delay queue.
     */
    DelayQueue check(UUID queueId);

    /**
     * Finds the current delay queue for the specified order.
     *
     * @param order The order to find the current delay queue for.
     * @return The current delay queue for the order, if found.
     */
    Optional<DelayQueue> findCurrentDelayQueue(Order order);

    DelayQueue reassign(ReassignDelayQueueRequestBody agentId);

    DelayQueue findCurrentDelayQueue(UUID agentId);

    Collection<VendorDelay> vendorDelays();
}