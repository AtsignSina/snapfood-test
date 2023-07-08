package ir.atsignsina.task.snapfood.domain.order;

import ir.atsignsina.task.snapfood.domain.agent.Agent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository
    extends JpaRepository<Order, UUID>, PagingAndSortingRepository<Order, UUID> {}
