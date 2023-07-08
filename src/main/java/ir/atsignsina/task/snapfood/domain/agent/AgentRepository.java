package ir.atsignsina.task.snapfood.domain.agent;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgentRepository
    extends JpaRepository<Agent, UUID>, PagingAndSortingRepository<Agent, UUID> {}
