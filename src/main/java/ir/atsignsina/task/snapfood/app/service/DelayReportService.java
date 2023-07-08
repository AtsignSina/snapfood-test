package ir.atsignsina.task.snapfood.app.service;

import ir.atsignsina.task.snapfood.api.dto.ReportDelayRequestBody;
import ir.atsignsina.task.snapfood.domain.delay_report.DelayReport;

/**
 * The DelayReportService interface provides methods for managing delay reports.
 */
public interface DelayReportService extends DomainService<DelayReport> {
    /**
     * Reports a delay for the specified order.
     *
     * @param body The request body containing the order ID and delay details.
     * @return The created delay report.
     */
    DelayReport reportDelay(ReportDelayRequestBody body);
}