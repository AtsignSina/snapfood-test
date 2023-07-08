package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.api.dto.ReportDelayRequestBody;
import ir.atsignsina.task.snapfood.app.service.DelayQueueService;
import ir.atsignsina.task.snapfood.app.service.OrderService;
import ir.atsignsina.task.snapfood.app.service.impl.DelayReportServiceImpl;
import ir.atsignsina.task.snapfood.domain.delay_report.DelayReport;
import ir.atsignsina.task.snapfood.domain.delay_report.DelayReportRepository;
import ir.atsignsina.task.snapfood.domain.delay_report.error.DelayReportNotFoundException;
import ir.atsignsina.task.snapfood.domain.order.Order;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueue;
import ir.atsignsina.task.snapfood.domain.queue.error.DelayQueueUncheckedAlreadyExistsException;
import ir.atsignsina.task.snapfood.domain.trip.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static ir.atsignsina.task.snapfood.domain.trip.TripStatus.AT_VENDOR;
import static ir.atsignsina.task.snapfood.domain.trip.TripStatus.DELIVERED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DelayReportServiceImplTest {
    @Mock
    private DelayReportRepository delayReportRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private DelayQueueService delayQueueService;

    @InjectMocks
    private DelayReportServiceImpl delayReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReportDelayWithNoTrip() {
        UUID orderId = UUID.randomUUID();
        ReportDelayRequestBody requestBody = new ReportDelayRequestBody();
        requestBody.setOrderId(orderId);

        Order order = new Order();
        order.setId(orderId);

        when(orderService.get(orderId)).thenReturn(order);
        when(delayQueueService.findCurrentDelayQueue(order)).thenReturn(Optional.empty());
        when(delayReportRepository.save(any(DelayReport.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderService.isDeliveryTimeExpired(order)).thenReturn(true);

        DelayReport result = delayReportService.reportDelay(requestBody);

        assertNotNull(result);
        assertEquals(order, result.getOrder());

        verify(orderService, times(1)).get(orderId);
        verify(delayQueueService, times(1)).findCurrentDelayQueue(order);
        verify(delayReportRepository, times(1)).save(any(DelayReport.class));
    }

    @Test
    public void testReportDelayWithTripAndValidStatus() {
        UUID orderId = UUID.randomUUID();
        ReportDelayRequestBody requestBody = new ReportDelayRequestBody();
        requestBody.setOrderId(orderId);

        Order order = new Order();
        order.setId(orderId);

        Trip trip = new Trip();
        trip.setStatus(AT_VENDOR);
        order.setTrip(trip);

        when(orderService.get(orderId)).thenReturn(order);
        when(orderService.renewEstimate(order)).thenReturn(order);
        when(delayReportRepository.save(any(DelayReport.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderService.isDeliveryTimeExpired(order)).thenReturn(true);

        DelayReport result = delayReportService.reportDelay(requestBody);

        assertNotNull(result);
        assertEquals(order, result.getOrder());

        verify(orderService, times(1)).get(orderId);
        verify(orderService, times(1)).renewEstimate(order);
        verify(delayReportRepository, times(1)).save(any(DelayReport.class));
    }

    @Test
    public void testReportDelayWithTripAndInvalidStatus() {
        UUID orderId = UUID.randomUUID();
        ReportDelayRequestBody requestBody = new ReportDelayRequestBody();
        requestBody.setOrderId(orderId);

        Order order = new Order();
        order.setId(orderId);
        Trip trip = new Trip();
        trip.setStatus(DELIVERED);
        order.setTrip(trip);

        DelayQueue delayQueue = new DelayQueue();
        delayQueue.setId(UUID.randomUUID());

        when(orderService.get(orderId)).thenReturn(order);
        when(orderService.isDeliveryTimeExpired(order)).thenReturn(true);
        when(delayQueueService.findCurrentDelayQueue(order)).thenReturn(Optional.of(delayQueue));

        assertThrows(DelayQueueUncheckedAlreadyExistsException.class, () -> delayReportService.reportDelay(requestBody));

        verify(orderService, times(1)).get(orderId);
        verify(delayQueueService, times(1)).findCurrentDelayQueue(order);
        verify(delayReportRepository, never()).save(any(DelayReport.class));
    }

    @Test
    public void testGet() {
        UUID delayReportId = UUID.randomUUID();
        DelayReport delayReport = new DelayReport();
        delayReport.setId(delayReportId);

        when(delayReportRepository.findById(delayReportId)).thenReturn(Optional.of(delayReport));

        DelayReport result = delayReportService.get(delayReportId);

        assertNotNull(result);
        assertEquals(delayReportId, result.getId());

        verify(delayReportRepository, times(1)).findById(delayReportId);
    }

    @Test
    public void testGetWithNonExistingDelayReport() {
        UUID delayReportId = UUID.randomUUID();

        when(delayReportRepository.findById(delayReportId)).thenReturn(Optional.empty());

        assertThrows(DelayReportNotFoundException.class, () -> delayReportService.get(delayReportId));

        verify(delayReportRepository, times(1)).findById(delayReportId);
    }
}