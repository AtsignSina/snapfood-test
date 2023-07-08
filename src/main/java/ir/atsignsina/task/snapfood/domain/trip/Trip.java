package ir.atsignsina.task.snapfood.domain.trip;

import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import ir.atsignsina.task.snapfood.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "trips")
@Getter
@Setter
public class Trip extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;
}
