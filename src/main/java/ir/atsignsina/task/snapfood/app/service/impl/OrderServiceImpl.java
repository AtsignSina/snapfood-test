package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.app.service.ETAService;
import ir.atsignsina.task.snapfood.app.service.OrderService;
import ir.atsignsina.task.snapfood.domain.order.Order;
import ir.atsignsina.task.snapfood.domain.order.OrderRepository;
import ir.atsignsina.task.snapfood.domain.order.error.OrderEstimatedDeliveryTimeIsNotPassedYetException;
import ir.atsignsina.task.snapfood.domain.order.error.OrderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Implementation of the OrderService interface.
 */
@Component
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ETAService etaService;

    @Override
    public Order get(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException().orderId(id));
    }

    @Override
    public Order renewEstimate(Order order) {
        if (isDeliveryTimeExpired(order)) {
            int eta = etaService.eta();
            order.setDeliveryTime(LocalDateTime.now().plus(eta, ChronoUnit.MINUTES));
            return orderRepository.save(order);
        } else {
            throw new OrderEstimatedDeliveryTimeIsNotPassedYetException().orderId(order.getId());
        }
    }
    @Override
    public boolean isDeliveryTimeExpired(Order order) {
        return order.getDeliveryTime().isBefore(LocalDateTime.now());
    }
}