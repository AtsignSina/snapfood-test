package ir.atsignsina.task.snapfood.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AbstractEntityTest {
    @Test
    public void testAbstractEntityCreation() {
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        AbstractEntity entity = new AbstractEntity();
        entity.setId(id);
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);

        assertEquals(id, entity.getId());
        assertEquals(createdAt, entity.getCreatedAt());
        assertEquals(updatedAt, entity.getUpdatedAt());
    }

    @Test
    public void testAbstractEntityEquality() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        AbstractEntity entity1 = new AbstractEntity();
        entity1.setId(id1);

        AbstractEntity entity2 = new AbstractEntity();
        entity2.setId(id1);

        AbstractEntity entity3 = new AbstractEntity();
        entity3.setId(id2);

        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}