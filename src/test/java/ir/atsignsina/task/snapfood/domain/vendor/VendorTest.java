package ir.atsignsina.task.snapfood.domain.vendor;

import ir.atsignsina.task.snapfood.domain.order.Order;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorTest {
    @Test
    public void testVendorCreation() {
        UUID id = UUID.randomUUID();
        String name = "Snappfood Restaurant";

        Vendor vendor = new Vendor();
        vendor.setId(id);
        vendor.setName(name);
        vendor.setOrders(Set.of(new Order()));

        assertEquals(name, vendor.getName());
        assertEquals(id, vendor.getId());
        assertEquals(Set.of(new Order()), vendor.getOrders());
    }
}