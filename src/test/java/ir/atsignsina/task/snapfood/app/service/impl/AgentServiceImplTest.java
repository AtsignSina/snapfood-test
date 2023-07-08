package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.app.service.impl.AgentServiceImpl;
import ir.atsignsina.task.snapfood.domain.agent.Agent;
import ir.atsignsina.task.snapfood.domain.agent.AgentRepository;
import ir.atsignsina.task.snapfood.domain.agent.error.AgentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AgentServiceImplTest {
    @Mock
    private AgentRepository agentRepository;
    @InjectMocks
    private AgentServiceImpl agentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGet() {
        UUID id = UUID.randomUUID();
        Agent agent = new Agent();
        agent.setId(id);

        when(agentRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(agent));

        Agent result = agentService.get(id);

        assertEquals(agent, result);
    }

    @Test
    void testGet_whenAgentNotFoundException() {
        UUID id = UUID.randomUUID();
        when(agentRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(AgentNotFoundException.class, () -> {
            agentService.get(id);
        });
    }
}