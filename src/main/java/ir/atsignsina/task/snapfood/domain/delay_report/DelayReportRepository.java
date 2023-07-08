package ir.atsignsina.task.snapfood.domain.delay_report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface DelayReportRepository extends JpaRepository<DelayReport, UUID>,
        PagingAndSortingRepository<DelayReport, UUID> {
}
