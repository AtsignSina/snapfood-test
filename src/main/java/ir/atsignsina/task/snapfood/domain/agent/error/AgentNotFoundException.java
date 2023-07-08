package ir.atsignsina.task.snapfood.domain.agent.error;

import ir.atsignsina.task.snapfood.domain.SnappfoodException;

import java.util.UUID;

public class AgentNotFoundException extends SnappfoodException {
    private static final String MESSAGE = "agent.notFound";

    public AgentNotFoundException() {
        super(MESSAGE);

    }

    public AgentNotFoundException requestedId(UUID agentId) {
        this.param("requestedAgentId", agentId);
        return this;
    }
}