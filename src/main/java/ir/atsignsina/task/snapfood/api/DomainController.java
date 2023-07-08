package ir.atsignsina.task.snapfood.api;

import ir.atsignsina.task.snapfood.app.service.DataService;
import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
public abstract class DomainController<T extends AbstractEntity> {
    protected final DataService dataService;

    @PostMapping()
    public AbstractEntity create(@RequestBody T t) {
        return dataService.create(t);
    }

    @GetMapping("{id}")
    public AbstractEntity get(@PathVariable("id") UUID id) {
        return dataService.get(getClazz(), id);
    }

    @GetMapping()
    public Page<AbstractEntity> getAll(Pageable pageable) {
        return dataService.getAll(getClazz(), pageable);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") UUID id) {
        dataService.delete(getClazz(), id);
    }

    @DeleteMapping()
    public void deleteAll() {
        dataService.deleteAll(getClazz());
    }

    abstract Class<T> getClazz();
}
