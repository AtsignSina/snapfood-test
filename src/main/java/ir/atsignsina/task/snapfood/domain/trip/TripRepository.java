package ir.atsignsina.task.snapfood.domain.trip;

import ir.atsignsina.task.snapfood.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface TripRepository
        extends JpaRepository<Trip, UUID>, PagingAndSortingRepository<Trip, UUID> {
    Optional<Trip> findByOrder(Order order);
}
