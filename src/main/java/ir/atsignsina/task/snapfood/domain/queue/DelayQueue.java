package ir.atsignsina.task.snapfood.domain.queue;

import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import ir.atsignsina.task.snapfood.domain.agent.Agent;
import ir.atsignsina.task.snapfood.domain.order.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "delay_queue")
@Getter
@Setter
public class DelayQueue extends AbstractEntity {
    private DelayQueueStatus status;
    @ManyToOne
    private Agent agent;

    @ManyToOne
    private Order order;
}
