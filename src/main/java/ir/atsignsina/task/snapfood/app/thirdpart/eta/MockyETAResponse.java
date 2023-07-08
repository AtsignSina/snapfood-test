package ir.atsignsina.task.snapfood.app.thirdpart.eta;

import lombok.Data;

@Data
public class MockyETAResponse {
    private MockyETAResponseBody data;

    @Data
    public static class MockyETAResponseBody {
        int eta;
    }
}
