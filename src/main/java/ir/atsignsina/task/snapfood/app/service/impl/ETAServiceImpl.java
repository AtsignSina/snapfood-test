package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.app.service.ETAService;
import ir.atsignsina.task.snapfood.app.thirdpart.eta.MockyETAClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ETAServiceImpl implements ETAService {
    private final MockyETAClient etaClient;

    @Override
    public int eta() {
        return etaClient.get().getData().getEta();
    }
}
