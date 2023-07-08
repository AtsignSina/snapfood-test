package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.app.service.impl.ETAServiceImpl;
import ir.atsignsina.task.snapfood.app.thirdpart.eta.MockyETAClient;
import ir.atsignsina.task.snapfood.app.thirdpart.eta.MockyETAResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ETAServiceImplTest {
    @Mock
    private MockyETAClient etaClient;
    @InjectMocks
    private ETAServiceImpl etaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEta() {
        MockyETAResponse.MockyETAResponseBody responseBody = new MockyETAResponse.MockyETAResponseBody();
        responseBody.setEta(30);

        MockyETAResponse response = new MockyETAResponse();
        response.setData(responseBody);

        when(etaClient.get()).thenReturn(response);

        int result = etaService.eta();

        assertEquals(30, result);

        verify(etaClient, times(1)).get();
    }
}