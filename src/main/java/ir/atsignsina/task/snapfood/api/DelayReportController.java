package ir.atsignsina.task.snapfood.api;

import ir.atsignsina.task.snapfood.api.dto.ReportDelayRequestBody;
import ir.atsignsina.task.snapfood.app.service.DelayReportService;
import ir.atsignsina.task.snapfood.domain.delay_report.DelayReport;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delay-reports")
@AllArgsConstructor
public class DelayReportController {
    private final DelayReportService delayReportService;

    @PostMapping()
    public DelayReport reportDelay(@RequestBody ReportDelayRequestBody body) {
        return delayReportService.reportDelay(body);
    }
}
