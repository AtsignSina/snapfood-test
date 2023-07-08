package ir.atsignsina.task.snapfood.domain.order;

import ir.atsignsina.task.snapfood.domain.delay_report.DelayReport;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueue;
import ir.atsignsina.task.snapfood.domain.trip.Trip;
import ir.atsignsina.task.snapfood.domain.vendor.Vendor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    @Test
    public void testOrderCreation() {
        Order order = new Order();
        LocalDateTime orderTime = LocalDateTime.now();
        LocalDateTime deliveryTime = orderTime.plusMinutes(30);
        Vendor vendor = new Vendor();
        Trip trip = new Trip();
        DelayReport delayReport = new DelayReport();
        DelayQueue delayQueue = new DelayQueue();

        order.setOrderTime(orderTime);
        order.setDeliveryTime(deliveryTime);
        order.setVendor(vendor);
        order.setTrip(trip);
        order.setDelayReport(Set.of(delayReport));
        order.setDelayQueues(Set.of(delayQueue));

        assertEquals(orderTime, order.getOrderTime());
        assertEquals(deliveryTime, order.getDeliveryTime());
        assertEquals(vendor, order.getVendor());
        assertEquals(trip, order.getTrip());
        assertEquals(Set.of(delayReport), order.getDelayReport());
        assertEquals(Set.of(delayQueue), order.getDelayQueues());
    }
}