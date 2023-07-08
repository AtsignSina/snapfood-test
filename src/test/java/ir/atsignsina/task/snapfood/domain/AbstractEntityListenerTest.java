package ir.atsignsina.task.snapfood.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AbstractEntityListenerTest {
    private AbstractEntityListener listener;

    @BeforeEach
    public void setUp() {
        listener = new AbstractEntityListener();
    }

    @Test
    public void testAddTimesPrePersist() {
        AbstractEntity entity = new AbstractEntity();

        listener.addTimesPrePersist(entity);

        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUpdatedAt());
    }

    @Test
    public void testAddTimesPreUpdate() {
        AbstractEntity entity = new AbstractEntity();

        listener.addTimesPreUpdate(entity);

        assertNotNull(entity.getUpdatedAt());
    }
}