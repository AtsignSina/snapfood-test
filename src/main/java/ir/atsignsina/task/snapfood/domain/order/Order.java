package ir.atsignsina.task.snapfood.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vastik.spring.data.faker.annotation.FakeDateFuture;
import com.vastik.spring.data.faker.annotation.FakeDateNow;
import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import ir.atsignsina.task.snapfood.domain.delay_report.DelayReport;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueue;
import ir.atsignsina.task.snapfood.domain.trip.Trip;
import ir.atsignsina.task.snapfood.domain.vendor.Vendor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends AbstractEntity {
    @FakeDateNow
    private LocalDateTime orderTime;
    @FakeDateFuture(value = 60, unit = TimeUnit.MINUTES)
    private LocalDateTime deliveryTime;

    @ManyToOne
    private Vendor vendor;

    @JsonIgnore
    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Trip trip;

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DelayReport> delayReport;

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DelayQueue> delayQueues;
}
