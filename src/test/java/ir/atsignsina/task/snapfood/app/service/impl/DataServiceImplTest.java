package ir.atsignsina.task.snapfood.app.service.impl;

import ir.atsignsina.task.snapfood.domain.AbstractEntity;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DataServiceImplTest {

    @Mock
    private Session session;

    @InjectMocks
    private DataServiceImpl dataService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(session.getDelegate()).thenReturn(session);
    }

    @Test
    public void testDelete() {
        Class<? extends AbstractEntity> clazz = AbstractEntity.class;
        UUID id = UUID.randomUUID();
        org.hibernate.query.Query query = mock(org.hibernate.query.Query.class);

        when(session.createQuery(anyString(), eq(Void.class))).thenReturn(query);
        dataService.delete(clazz, id);

        verify(query, times(1)).setParameter(1, id);
        verify(query, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteAll() {
        Class<? extends AbstractEntity> clazz = AbstractEntity.class;
        org.hibernate.query.Query query = mock(org.hibernate.query.Query.class);

        when(session.createQuery(anyString(), eq(Void.class))).thenReturn(query);

        dataService.deleteAll(clazz);

        verify(query, times(1)).executeUpdate();
    }

    @Test
    public void testGetAll() {
        Class<? extends AbstractEntity> clazz = AbstractEntity.class;
        Pageable pageable = mock(Pageable.class);
        org.hibernate.query.Query countQuery = mock(org.hibernate.query.Query.class);
        org.hibernate.query.Query resultQuery = mock(org.hibernate.query.Query.class);
        List<AbstractEntity> resultList = List.of(new AbstractEntity());

        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(countQuery);
        when(session.createQuery(anyString(), eq(clazz))).thenReturn(resultQuery);
        when(countQuery.getSingleResult()).thenReturn(1L);
        when(resultQuery.getResultList()).thenReturn(resultList);

        Page<AbstractEntity> result = dataService.getAll(clazz, pageable);

        assertNotNull(result);
        assertEquals(resultList, result.getContent());
        assertEquals(1L, result.getTotalElements());

        verify(resultQuery, times(1)).setFirstResult((int) pageable.getOffset());
        verify(resultQuery, times(1)).setMaxResults((int) pageable.getOffset() + pageable.getPageSize());
    }

    @Test
    public void testCreate() {
        AbstractEntity entity = new AbstractEntity();

        AbstractEntity result = dataService.create(entity);

        assertNotNull(result);
        assertEquals(entity, result);

        verify(session, times(1)).persist(entity);
    }

    @Test
    public void testGet() {
        Class<? extends AbstractEntity> clazz = AbstractEntity.class;
        UUID id = UUID.randomUUID();
        AbstractEntity entity = new AbstractEntity();
        Answer<? extends AbstractEntity> answer = (Answer<AbstractEntity>) invocation -> entity;
        when(session.get(clazz, id)).thenAnswer(answer);

        AbstractEntity result = dataService.get(clazz, id);

        assertNotNull(result);
        assertEquals(entity, result);

        verify(session, times(1)).get(clazz, id);
    }
}