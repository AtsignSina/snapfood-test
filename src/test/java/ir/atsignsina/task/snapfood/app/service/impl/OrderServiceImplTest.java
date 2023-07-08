package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.app.service.ETAService;
import ir.atsignsina.task.snapfood.app.service.impl.OrderServiceImpl;
import ir.atsignsina.task.snapfood.domain.order.Order;
import ir.atsignsina.task.snapfood.domain.order.OrderRepository;
import ir.atsignsina.task.snapfood.domain.order.error.OrderEstimatedDeliveryTimeIsNotPassedYetException;
import ir.atsignsina.task.snapfood.domain.order.error.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ETAService etaService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGet() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.get(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());

        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    public void testGetWithNonExistingOrder() {
        UUID orderId = UUID.randomUUID();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.get(orderId));

        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    public void testRenewEstimateWithExpiredDeliveryTime() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        order.setDeliveryTime(LocalDateTime.now().minusMinutes(30));

        int eta = 15;

        when(etaService.eta()).thenReturn(eta);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.renewEstimate(order);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertTrue(result.getDeliveryTime().isAfter(LocalDateTime.now()));
        assertEquals(LocalDateTime.now().plusMinutes(eta).truncatedTo(ChronoUnit.SECONDS), result.getDeliveryTime().truncatedTo(ChronoUnit.SECONDS));

        verify(etaService, times(1)).eta();
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testRenewEstimateWithNonExpiredDeliveryTime() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        order.setDeliveryTime(LocalDateTime.now().plusMinutes(30));

        assertThrows(OrderEstimatedDeliveryTimeIsNotPassedYetException.class, () -> orderService.renewEstimate(order));

        verify(etaService, never()).eta();
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void testIsDeliveryTimeExpiredWithExpiredDeliveryTime() {
        Order order = new Order();
        order.setDeliveryTime(LocalDateTime.now().minusMinutes(30));

        boolean result = orderService.isDeliveryTimeExpired(order);

        assertTrue(result);
    }

    @Test
    public void testIsDeliveryTimeExpiredWithNonExpiredDeliveryTime() {
        Order order = new Order();
        order.setDeliveryTime(LocalDateTime.now().plusMinutes(30));

        boolean result = orderService.isDeliveryTimeExpired(order);

        assertFalse(result);
    }
}