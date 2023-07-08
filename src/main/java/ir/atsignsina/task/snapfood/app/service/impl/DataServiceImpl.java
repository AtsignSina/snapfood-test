package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.app.service.DataService;
import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DataServiceImpl implements DataService {
    @PersistenceContext
    EntityManager EM;

    @Override
    @Transactional
    public void delete(Class<? extends AbstractEntity> clazz, UUID id) {
        Query q = session().createQuery("DELETE from " + clazz.getName() + " v where v.id=?1", Void.class);
        q.setParameter(1, id);
        q.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAll(Class<? extends AbstractEntity> clazz) {
        session().createQuery("DELETE from " + clazz.getName(), Void.class).executeUpdate();
    }

    @Override
    @Transactional
    public Page<AbstractEntity> getAll(Class<? extends AbstractEntity> clazz, Pageable pageable) {
        long total = session()
                .createQuery("select count(v) from " + clazz.getName() + " v", Long.class)
                .getSingleResult();
        Query q = session()
                .createQuery("select v from " + clazz.getName() + " v", clazz);
        q.setFirstResult((int) pageable.getOffset());
        q.setMaxResults((int) pageable.getOffset() + pageable.getPageSize());
        List<AbstractEntity> list = (List<AbstractEntity>) q.getResultList();
        return new PageImpl<>(list, pageable, total);
    }

    @Override
    @Transactional
    public AbstractEntity create(AbstractEntity t) {
        session().persist(t);
        return t;
    }

    @Override
    @Transactional
    public AbstractEntity get(Class<? extends AbstractEntity> clazz, UUID id) {
        return session().get(clazz, id);
    }

    private Session session() {
        return (Session) EM.getDelegate();
    }
}
