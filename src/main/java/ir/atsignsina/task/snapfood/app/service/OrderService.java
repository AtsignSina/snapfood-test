package ir.atsignsina.task.snapfood.app.service;

import ir.atsignsina.task.snapfood.domain.order.Order;

/**
 * The OrderService interface provides methods for managing orders.
 */
public interface OrderService extends DomainService<Order> {
    /**
     * Renews the estimate for the specified order.
     *
     * @param order The order to renew the estimate for.
     * @return The updated order.
     */
    Order renewEstimate(Order order);

    /**
     * Checks if the delivery time for the specified order has expired.
     *
     * @param order The order to check.
     * @return True if the delivery time has expired, false otherwise.
     */
    boolean isDeliveryTimeExpired(Order order);
}