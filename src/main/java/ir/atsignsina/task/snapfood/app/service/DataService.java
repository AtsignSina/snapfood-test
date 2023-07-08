package ir.atsignsina.task.snapfood.app.service;

import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DataService {

    @Transactional
    void delete(Class<? extends AbstractEntity> clazz, UUID id);

    @Transactional
    void deleteAll(Class<? extends AbstractEntity> clazz);

    @Transactional
    Page<AbstractEntity> getAll(Class<? extends AbstractEntity> clazz, Pageable pageable);

    AbstractEntity create(AbstractEntity t);

    @Transactional
    AbstractEntity get(Class<? extends AbstractEntity> clazz, UUID id);
}
