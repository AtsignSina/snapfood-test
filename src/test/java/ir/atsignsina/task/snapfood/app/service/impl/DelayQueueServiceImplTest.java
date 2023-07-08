package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.api.dto.ReassignDelayQueueRequestBody;
import ir.atsignsina.task.snapfood.app.service.AgentService;
import ir.atsignsina.task.snapfood.app.service.impl.DelayQueueServiceImpl;
import ir.atsignsina.task.snapfood.domain.agent.Agent;
import ir.atsignsina.task.snapfood.domain.order.Order;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueue;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueueRepository;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueueStatus;
import ir.atsignsina.task.snapfood.domain.queue.VendorDelay;
import ir.atsignsina.task.snapfood.domain.queue.error.DelayQueueEmptyException;
import ir.atsignsina.task.snapfood.domain.queue.error.DelayQueueUncheckedAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DelayQueueServiceImplTest {
    @Mock
    private DelayQueueRepository delayQueueRepository;

    @Mock
    private AgentService agentService;

    @InjectMocks
    private DelayQueueServiceImpl delayQueueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdd() {
        Order order = new Order();
        DelayQueue delayQueue = new DelayQueue();
        delayQueue.setOrder(order);
        delayQueue.setStatus(DelayQueueStatus.REPORTED);

        when(delayQueueRepository.save(any(DelayQueue.class))).thenReturn(delayQueue);

        DelayQueue result = delayQueueService.add(order);

        assertNotNull(result);
        assertEquals(order, result.getOrder());
        assertEquals(DelayQueueStatus.REPORTED, result.getStatus());

        verify(delayQueueRepository, times(1)).save(any(DelayQueue.class));
    }

    @Test
    public void testFirstQueued() {
        // Arrange
        UUID agentId = UUID.randomUUID();
        Agent agent = new Agent();
        agent.setId(agentId);

        DelayQueue delayQueue = new DelayQueue();
        delayQueue.setAgent(null);
        delayQueue.setStatus(DelayQueueStatus.REPORTED);

        when(agentService.get(agentId)).thenReturn(agent);
        when(delayQueueRepository.findFirstByAgent_IdAndStatusNotIn(agent.getId(), List.of(DelayQueueStatus.CHECKED)))
                .thenReturn(Optional.empty());
        when(delayQueueRepository.findFirstNotAssignedByAgentIsNull()).thenReturn(Optional.of(delayQueue));
        when(delayQueueRepository.save(any(DelayQueue.class))).thenReturn(delayQueue);

        DelayQueue result = delayQueueService.firstQueued(agentId);

        assertNotNull(result);
        assertEquals(agent, result.getAgent());
        assertEquals(DelayQueueStatus.ASSIGNED, result.getStatus());

        verify(agentService, times(1)).get(agentId);
        verify(delayQueueRepository, times(1)).findFirstByAgent_IdAndStatusNotIn(agent.getId(), List.of(DelayQueueStatus.CHECKED));
        verify(delayQueueRepository, times(1)).findFirstNotAssignedByAgentIsNull();
        verify(delayQueueRepository, times(1)).save(any(DelayQueue.class));
    }

    @Test
    public void testFirstQueuedWithExistingUncheckedDelayQueue() {
        UUID agentId = UUID.randomUUID();
        Agent agent = new Agent();
        agent.setId(agentId);

        DelayQueue existingDelayQueue = new DelayQueue();
        existingDelayQueue.setId(UUID.randomUUID());
        existingDelayQueue.setAgent(agent);
        existingDelayQueue.setStatus(DelayQueueStatus.REPORTED);

        when(agentService.get(agentId)).thenReturn(agent);
        when(delayQueueRepository.findFirstByAgent_IdAndStatusNotIn(agent.getId(), List.of(DelayQueueStatus.CHECKED)))
                .thenReturn(Optional.of(existingDelayQueue));

        assertThrows(DelayQueueUncheckedAlreadyExistsException.class, () -> delayQueueService.firstQueued(agentId));

        verify(agentService, times(1)).get(agentId);
        verify(delayQueueRepository, times(1)).findFirstByAgent_IdAndStatusNotIn(agent.getId(), List.of(DelayQueueStatus.CHECKED));
        verify(delayQueueRepository, never()).findFirstNotAssignedByAgentIsNull();
        verify(delayQueueRepository, never()).save(any(DelayQueue.class));
    }

    @Test
    public void testFirstQueuedWithNoUnassignedDelayQueue() {
        UUID agentId = UUID.randomUUID();
        Agent agent = new Agent();
        agent.setId(agentId);

        when(agentService.get(agentId)).thenReturn(agent);
        when(delayQueueRepository.findFirstByAgent_IdAndStatusNotIn(agent.getId(), List.of(DelayQueueStatus.CHECKED)))
                .thenReturn(Optional.empty());
        when(delayQueueRepository.findFirstNotAssignedByAgentIsNull()).thenReturn(Optional.empty());

        assertThrows(DelayQueueEmptyException.class, () -> delayQueueService.firstQueued(agentId));

        verify(agentService, times(1)).get(agentId);
        verify(delayQueueRepository, times(1)).findFirstByAgent_IdAndStatusNotIn(agent.getId(), List.of(DelayQueueStatus.CHECKED));
        verify(delayQueueRepository, times(1)).findFirstNotAssignedByAgentIsNull();
        verify(delayQueueRepository, never()).save(any(DelayQueue.class));
    }

    @Test
    public void testVendorDelays() {
        LocalDateTime lastWeekStart = LocalDateTime.now();
        lastWeekStart = lastWeekStart.minus(lastWeekStart.getDayOfWeek().ordinal(), ChronoUnit.DAYS);
        TreeSet<VendorDelay> expectedDelays = new TreeSet<>();

        when(delayQueueRepository.findVendorDelaySum(lastWeekStart)).thenReturn(expectedDelays);

        Collection<VendorDelay> result = delayQueueService.vendorDelays();

        assertNotNull(result);
        assertEquals(expectedDelays, result);

        verify(delayQueueRepository, times(1)).findVendorDelaySum(any(LocalDateTime.class));
    }

    @Test
    public void testFindCurrentDelayQueue() {
        UUID agentId = UUID.randomUUID();
        Agent agent = new Agent();
        agent.setId(agentId);

        UUID delayQueueId = UUID.randomUUID();
        DelayQueue delayQueue = new DelayQueue();
        delayQueue.setId(delayQueueId);
        delayQueue.setAgent(agent);
        delayQueue.setStatus(DelayQueueStatus.REPORTED);

        when(agentService.get(agentId)).thenReturn(agent);
        when(delayQueueRepository.findFirstByAgent_IdAndStatusNotIn(agentId, List.of(DelayQueueStatus.CHECKED)))
                .thenReturn(Optional.of(delayQueue));

        DelayQueue result = delayQueueService.findCurrentDelayQueue(agentId);

        assertNotNull(result);
        assertEquals(delayQueue, result);

        verify(delayQueueRepository, times(1)).findFirstByAgent_IdAndStatusNotIn(agentId, List.of(DelayQueueStatus.CHECKED));
    }

    @Test
    public void testReassign() {
        // Arrange
        UUID agentId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Agent agent = new Agent();
        agent.setId(agentId);

        ReassignDelayQueueRequestBody requestBody = new ReassignDelayQueueRequestBody();
        requestBody.setAgentId(agentId);
        requestBody.setOrderId(orderId);

        DelayQueue existingDelayQueue = new DelayQueue();
        existingDelayQueue.setId(UUID.randomUUID());
        existingDelayQueue.setAgent(agent);
        existingDelayQueue.setStatus(DelayQueueStatus.REPORTED);

        DelayQueue updatedDelayQueue = new DelayQueue();
        updatedDelayQueue.setId(existingDelayQueue.getId());
        updatedDelayQueue.setAgent(agent);
        updatedDelayQueue.setStatus(DelayQueueStatus.ASSIGNED);

        when(agentService.get(agentId)).thenReturn(agent);
        when(delayQueueRepository.findFirstByOrder_Id(orderId)).thenReturn(Optional.of(existingDelayQueue));
        when(delayQueueRepository.save(existingDelayQueue)).thenReturn(updatedDelayQueue);

        DelayQueue result = delayQueueService.reassign(requestBody);

        assertNotNull(result);
        assertEquals(updatedDelayQueue, result);

        verify(agentService, times(1)).get(agentId);
        verify(delayQueueRepository, times(1)).findFirstByOrder_Id(orderId);
        verify(delayQueueRepository, times(1)).save(existingDelayQueue);
    }
}