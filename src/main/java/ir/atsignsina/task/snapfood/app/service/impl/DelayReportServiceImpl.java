package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.api.dto.ReportDelayRequestBody;
import ir.atsignsina.task.snapfood.app.service.DelayQueueService;
import ir.atsignsina.task.snapfood.app.service.DelayReportService;
import ir.atsignsina.task.snapfood.app.service.OrderService;
import ir.atsignsina.task.snapfood.domain.delay_report.DelayReport;
import ir.atsignsina.task.snapfood.domain.delay_report.DelayReportRepository;
import ir.atsignsina.task.snapfood.domain.delay_report.error.DelayReportNotFoundException;
import ir.atsignsina.task.snapfood.domain.order.Order;
import ir.atsignsina.task.snapfood.domain.order.error.OrderEstimatedDeliveryTimeIsNotPassedYetException;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueue;
import ir.atsignsina.task.snapfood.domain.queue.error.DelayQueueUncheckedAlreadyExistsException;
import ir.atsignsina.task.snapfood.domain.trip.Trip;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ir.atsignsina.task.snapfood.domain.trip.TripStatus.*;

@AllArgsConstructor
@Component
public class DelayReportServiceImpl implements DelayReportService {
    private final DelayReportRepository delayReportRepository;
    private final OrderService orderService;
    private final DelayQueueService delayQueueService;

    @Override
    public DelayReport reportDelay(ReportDelayRequestBody body) {
        Order order = orderService.get(body.getOrderId());
        checkOrderExpiration(order);
        checkCurrentlyRegisteredDelayQueue(order);
        Trip trip = order.getTrip();
        if (trip == null || !List.of(AT_VENDOR, ASSIGNED, PICKED).contains(trip.getStatus())) {
            delayQueueService.add(order);
            return saveDelay(order);
        } else {
            order = orderService.renewEstimate(order);
            return saveDelay(order);
        }
    }

    private void checkOrderExpiration(Order order) {
        if (!orderService.isDeliveryTimeExpired(order)) {
            throw new OrderEstimatedDeliveryTimeIsNotPassedYetException()
                    .orderId(order.getId());
        }
    }

    private DelayReport saveDelay(Order order) {
        DelayReport dr = new DelayReport();
        dr.setOrder(order);
        return delayReportRepository.save(dr);
    }

    private void checkCurrentlyRegisteredDelayQueue(Order order) {
        Optional<DelayQueue> delayQueue = delayQueueService.findCurrentDelayQueue(order);
        if (delayQueue.isPresent()) {
            throw new DelayQueueUncheckedAlreadyExistsException().delayQueueId(delayQueue.get().getId());
        }
    }

    @Override
    public DelayReport get(UUID id) {
        return delayReportRepository.findById(id)
                .orElseThrow(() ->
                        new DelayReportNotFoundException()
                                .id(id)
                );
    }
}
