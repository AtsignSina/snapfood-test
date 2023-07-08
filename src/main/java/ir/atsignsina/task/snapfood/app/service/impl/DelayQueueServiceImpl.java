package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.api.dto.ReassignDelayQueueRequestBody;
import ir.atsignsina.task.snapfood.app.service.AgentService;
import ir.atsignsina.task.snapfood.app.service.DelayQueueService;
import ir.atsignsina.task.snapfood.domain.agent.Agent;
import ir.atsignsina.task.snapfood.domain.order.Order;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueue;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueueRepository;
import ir.atsignsina.task.snapfood.domain.queue.DelayQueueStatus;
import ir.atsignsina.task.snapfood.domain.queue.VendorDelay;
import ir.atsignsina.task.snapfood.domain.queue.error.DelayQueueEmptyException;
import ir.atsignsina.task.snapfood.domain.queue.error.DelayQueueIsNotFoundException;
import ir.atsignsina.task.snapfood.domain.queue.error.DelayQueueUncheckedAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
@AllArgsConstructor
public class DelayQueueServiceImpl implements DelayQueueService {
    private final DelayQueueRepository delayQueueRepository;
    private final AgentService agentService;

    @Override
    @Transactional
    public DelayQueue add(Order order) {
        DelayQueue delayQueue = new DelayQueue();
        delayQueue.setOrder(order);
        delayQueue.setStatus(DelayQueueStatus.REPORTED);
        return delayQueueRepository.save(delayQueue);
    }

    @Override
    @Transactional
    public DelayQueue firstQueued(UUID agentId) {
        Agent agent = agentService.get(agentId);
        delayQueueRepository.findFirstByAgent_IdAndStatusNotIn(agent.getId(), List.of(DelayQueueStatus.CHECKED)).ifPresent(delayQueue -> {
            throw new DelayQueueUncheckedAlreadyExistsException().delayQueueId(delayQueue.getId());
        });
        DelayQueue queue = delayQueueRepository.findFirstNotAssignedByAgentIsNull()
                .orElseThrow(DelayQueueEmptyException::new);
        queue.setAgent(agent);
        queue.setStatus(DelayQueueStatus.ASSIGNED);
        return delayQueueRepository.save(queue);
    }

    @Override
    @Transactional
    public DelayQueue check(UUID agentId) {
        Agent agent = agentService.get(agentId);
        DelayQueue delayQueue = delayQueueRepository.findFirstByAgent_IdAndStatusNotIn(agentId, List.of(DelayQueueStatus.CHECKED))
                .orElseThrow(() -> new DelayQueueIsNotFoundException().agentId(agent.getId()));
        delayQueue.setStatus(DelayQueueStatus.CHECKED);
        delayQueueRepository.save(delayQueue);
        return delayQueue;
    }

    @Override
    public Optional<DelayQueue> findCurrentDelayQueue(Order order) {
        return delayQueueRepository.findFirstByOrder_IdAndStatusNotIn(order.getId(), List.of(DelayQueueStatus.CHECKED));
    }

    @Override
    public DelayQueue reassign(ReassignDelayQueueRequestBody body) {
        Agent agent = agentService.get(body.getAgentId());
        DelayQueue dq = delayQueueRepository.findFirstByOrder_Id(body.getOrderId())
                .orElseThrow(DelayQueueIsNotFoundException::new);
        checkNotAssignedToAnotherAgent(agent, dq);
        checkThereIsNoActiveDelayQueueToAgent(agent);
        dq.setAgent(agent);
        dq.setStatus(DelayQueueStatus.ASSIGNED);
        return delayQueueRepository.save(dq);
    }

    private void checkNotAssignedToAnotherAgent(Agent agent, DelayQueue dq) {
        if (dq.getStatus() == DelayQueueStatus.ASSIGNED && dq.getAgent() != null && !dq.getAgent().equals(agent)) {
            throw new DelayQueueUncheckedAlreadyExistsException()
                    .delayQueueId(dq.getId())
                    .agentId(dq.getAgent().getId());
        }
    }

    @Override
    public DelayQueue findCurrentDelayQueue(UUID agentId) {
        return delayQueueRepository.findFirstByAgent_IdAndStatusNotIn(agentId, List.of(DelayQueueStatus.CHECKED))
                .orElseThrow(() -> new DelayQueueIsNotFoundException().agentId(agentId));
    }

    @Override
    public Collection<VendorDelay> vendorDelays() {
        LocalDateTime lastWeekStart = LocalDateTime.now();
        lastWeekStart = lastWeekStart.minus(lastWeekStart.getDayOfWeek().ordinal(), ChronoUnit.DAYS);
        return delayQueueRepository.findVendorDelaySum(lastWeekStart);
    }

    private void checkThereIsNoActiveDelayQueueToAgent(Agent agent) {
        // EXPLAIN: CONDITION 3
        delayQueueRepository.findFirstByAgent_IdAndStatusNotIn(agent.getId(), List.of(DelayQueueStatus.CHECKED))
                .ifPresent(delayQueue -> {
                    throw new DelayQueueUncheckedAlreadyExistsException()
                            .delayQueueId(delayQueue.getId())
                            .agentId(agent.getId());
                });
    }


}