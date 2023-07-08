package ir.atsignsina.task.snapfood.domain.queue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.UUID;

public interface DelayQueueRepository extends JpaRepository<DelayQueue, UUID>, PagingAndSortingRepository<DelayQueue, UUID> {

    Optional<DelayQueue> findFirstNotAssignedByAgentIsNull();

    Optional<DelayQueue> findFirstByAgent_IdAndStatusNotIn(UUID agentId, List<DelayQueueStatus> status);

    Optional<DelayQueue> findFirstByOrder_IdAndStatusNotIn(UUID orderId, List<DelayQueueStatus> status);

    Optional<DelayQueue> findFirstByOrder_Id(UUID orderId);

    @Query("select new ir.atsignsina.task.snapfood.domain.queue.VendorDelay(v.order.vendor.id, sum(v.updatedAt-v.createdAt))" +
            "from DelayQueue v " +
            "where " +
            "v.status=ir.atsignsina.task.snapfood.domain.queue.DelayQueueStatus.CHECKED " +
            "and " +
            "(v.createdAt>=?1 or v.updatedAt>=?1)" +
            "group by v.order.vendor")
    TreeSet<VendorDelay> findVendorDelaySum(LocalDateTime lastWeekStart);
}
