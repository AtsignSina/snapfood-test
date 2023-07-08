package ir.atsignsina.task.snapfood.domain.queue;

import ir.atsignsina.task.snapfood.domain.agent.Agent;
import ir.atsignsina.task.snapfood.domain.order.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DelayQueueTest {
    @Test
    public void testDelayQueueCreation() {
        DelayQueue delayQueue = new DelayQueue();
        DelayQueueStatus status = DelayQueueStatus.REPORTED;
        Agent agent = new Agent();
        Order order = new Order();

        delayQueue.setStatus(status);
        delayQueue.setAgent(agent);
        delayQueue.setOrder(order);

        assertEquals(status, delayQueue.getStatus());
        assertEquals(agent, delayQueue.getAgent());
        assertEquals(order, delayQueue.getOrder());
    }
}