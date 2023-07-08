package ir.atsignsina.task.snapfood.api;

import ir.atsignsina.task.snapfood.app.service.DataService;
import ir.atsignsina.task.snapfood.domain.order.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController extends DomainController<Order> {
    public OrderController(DataService dataService) {
        super(dataService);
    }

    @Override
    Class<Order> getClazz() {
        return Order.class;
    }
}
