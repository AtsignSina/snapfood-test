package ir.atsignsina.task.snapfood.api;

import ir.atsignsina.task.snapfood.app.service.DataService;
import ir.atsignsina.task.snapfood.domain.agent.Agent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("agents")
public class AgentController extends DomainController<Agent> {
    public AgentController(DataService dataService) {
        super(dataService);
    }

    @Override
    Class<Agent> getClazz() {
        return Agent.class;
    }
}
