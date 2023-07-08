package ir.atsignsina.task.snapfood.domain.queue;

import ir.atsignsina.task.snapfood.domain.vendor.Vendor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.UUID;

@Getter
@Setter
public class VendorDelay implements Comparable<VendorDelay> {
    private final UUID vendor;
    private final Duration duration;

    public VendorDelay(UUID vendor, Number sum) {
        this.vendor = vendor;
        this.duration = Duration.ofNanos(sum.longValue());
    }

    @Override
    public int compareTo(VendorDelay o) {
        return o.duration.compareTo(this.duration);
    }
}
