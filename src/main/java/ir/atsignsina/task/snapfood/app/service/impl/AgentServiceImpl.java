package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.app.service.AgentService;
import ir.atsignsina.task.snapfood.domain.agent.Agent;
import ir.atsignsina.task.snapfood.domain.agent.AgentRepository;
import ir.atsignsina.task.snapfood.domain.agent.error.AgentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;

    @Override
    public Agent get(UUID id) {
        return agentRepository.findById(id)
                .orElseThrow(() -> new AgentNotFoundException().requestedId(id));
    }
}
