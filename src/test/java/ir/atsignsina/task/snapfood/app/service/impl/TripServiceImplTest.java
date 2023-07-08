package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.api.dto.GenerateTripRequestBody;
import ir.atsignsina.task.snapfood.api.dto.UpdateTripStatusRequestBody;
import ir.atsignsina.task.snapfood.app.service.OrderService;
import ir.atsignsina.task.snapfood.app.service.impl.TripServiceImpl;
import ir.atsignsina.task.snapfood.domain.order.Order;
import ir.atsignsina.task.snapfood.domain.trip.Trip;
import ir.atsignsina.task.snapfood.domain.trip.TripRepository;
import ir.atsignsina.task.snapfood.domain.trip.TripStatus;
import ir.atsignsina.task.snapfood.domain.trip.error.TripAlreadyExistsException;
import ir.atsignsina.task.snapfood.domain.trip.error.TripNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TripServiceImplTest {
    @Mock
    private OrderService orderService;

    @Mock
    private TripRepository tripRepository;
    @InjectMocks
    private TripServiceImpl tripService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateTrip() {
        UUID orderId = UUID.randomUUID();
        GenerateTripRequestBody requestBody = new GenerateTripRequestBody();
        requestBody.setOrderId(orderId);

        Order order = new Order();
        order.setId(orderId);

        when(orderService.get(orderId)).thenReturn(order);
        when(tripRepository.findByOrder(order)).thenReturn(Optional.empty());
        when(tripRepository.save(any(Trip.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Trip result = tripService.generateTrip(requestBody);

        assertNotNull(result);
        assertEquals(order, result.getOrder());
        assertEquals(TripStatus.ASSIGNED, result.getStatus());

        verify(orderService, times(1)).get(orderId);
        verify(tripRepository, times(1)).findByOrder(order);
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    public void testGenerateTripWithExistingTrip() {
        UUID orderId = UUID.randomUUID();
        GenerateTripRequestBody requestBody = new GenerateTripRequestBody();
        requestBody.setOrderId(orderId);

        Order order = new Order();
        order.setId(orderId);

        Trip existingTrip = new Trip();
        existingTrip.setId(UUID.randomUUID());
        existingTrip.setOrder(order);

        when(orderService.get(orderId)).thenReturn(order);
        when(tripRepository.findByOrder(order)).thenReturn(Optional.of(existingTrip));

        assertThrows(TripAlreadyExistsException.class, () -> tripService.generateTrip(requestBody));

        verify(orderService, times(1)).get(orderId);
        verify(tripRepository, times(1)).findByOrder(order);
        verify(tripRepository, never()).save(any(Trip.class));
    }

    @Test
    public void testUpdateTripStatus() {
        UUID tripId = UUID.randomUUID();
        UpdateTripStatusRequestBody requestBody = new UpdateTripStatusRequestBody();
        requestBody.setStatus(TripStatus.DELIVERED);

        Trip trip = new Trip();
        trip.setId(tripId);
        trip.setStatus(TripStatus.ASSIGNED);

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(tripRepository.save(any(Trip.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Trip result = tripService.updateTripStatus(tripId, requestBody);

        assertNotNull(result);
        assertEquals(tripId, result.getId());
        assertEquals(TripStatus.DELIVERED, result.getStatus());

        verify(tripRepository, times(1)).findById(tripId);
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    public void testUpdateTripStatusWithNonExistingTrip() {
        UUID tripId = UUID.randomUUID();
        UpdateTripStatusRequestBody requestBody = new UpdateTripStatusRequestBody();
        requestBody.setStatus(TripStatus.DELIVERED);

        when(tripRepository.findById(tripId)).thenReturn(Optional.empty());

        assertThrows(TripNotFoundException.class, () -> tripService.updateTripStatus(tripId, requestBody));

        verify(tripRepository, times(1)).findById(tripId);
        verify(tripRepository, never()).save(any(Trip.class));
    }

    @Test
    public void testGet() {
        UUID tripId = UUID.randomUUID();
        Trip trip = new Trip();
        trip.setId(tripId);

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));

        Trip result = tripService.get(tripId);

        assertNotNull(result);
        assertEquals(tripId, result.getId());

        verify(tripRepository, times(1)).findById(tripId);
    }

    @Test
    public void testGetWithNonExistingTrip() {
        UUID tripId = UUID.randomUUID();

        when(tripRepository.findById(tripId)).thenReturn(Optional.empty());

        assertThrows(TripNotFoundException.class, () -> tripService.get(tripId));

        verify(tripRepository, times(1)).findById(tripId);
    }
}