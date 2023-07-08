package ir.atsignsina.task.snapfood.app.thirdpart.eta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "etaClient", url = "${snapfood.thirdparty.eta.url}")
public interface MockyETAClient {
    @GetMapping("122c2796-5df4-461c-ab75-87c1192b17f7")
    MockyETAResponse get();
}
