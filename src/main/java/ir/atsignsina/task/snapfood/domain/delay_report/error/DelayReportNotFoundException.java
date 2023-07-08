package ir.atsignsina.task.snapfood.domain.delay_report.error;

import ir.atsignsina.task.snapfood.domain.SnappfoodException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DelayReportNotFoundException extends SnappfoodException {
    private static final String MESSAGE = "delayReport.NotFound";

    public DelayReportNotFoundException() {
        super(MESSAGE);

    }

    public DelayReportNotFoundException id(UUID id) {
        this.param("delayReportId", id);
        return this;
    }
}
