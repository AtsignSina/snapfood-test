package ir.atsignsina.task.snapfood.domain.agent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgentTest {
    @Test
    public void testSetName() {
        Agent agent = new Agent();
        agent.setName("John Doe");
        assertEquals("John Doe", agent.getName());
    }
}