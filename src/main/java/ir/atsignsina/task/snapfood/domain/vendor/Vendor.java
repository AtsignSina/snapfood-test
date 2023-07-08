package ir.atsignsina.task.snapfood.domain.vendor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vastik.spring.data.faker.annotation.FakeFaker;
import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import ir.atsignsina.task.snapfood.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "vendors")
@Getter
@Setter
public class Vendor extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @FakeFaker("gameOfThrones.city")
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
    private Set<Order> orders;
}
