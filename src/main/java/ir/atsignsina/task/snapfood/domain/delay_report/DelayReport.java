package ir.atsignsina.task.snapfood.domain.delay_report;

import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import ir.atsignsina.task.snapfood.domain.order.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "delay_reports")
@Getter
@Setter
public class DelayReport extends AbstractEntity {
    @ManyToOne
    private Order order;
}
