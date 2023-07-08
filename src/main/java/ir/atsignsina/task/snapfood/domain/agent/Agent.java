package ir.atsignsina.task.snapfood.domain.agent;

import com.vastik.spring.data.faker.annotation.FakeFaker;
import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an agent in the system.
 */
@Entity
@Table(name = "agents")
@Getter
@Setter
public class Agent extends AbstractEntity {
    /**
     * The name of the agent.
     */
    @FakeFaker("gameOfThrones.character")
    private String name;
}
