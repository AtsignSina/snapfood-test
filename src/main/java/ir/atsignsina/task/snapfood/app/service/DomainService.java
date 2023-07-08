package ir.atsignsina.task.snapfood.app.service;

import java.util.UUID;

/**
 * The DomainService interface provides methods for managing domain entities.
 *
 * @param <T> The type of the domain entity.
 */
public interface DomainService<T> {
    /**
     * Retrieves the domain entity with the specified ID.
     *
     * @param id The ID of the domain entity to retrieve.
     * @return The domain entity with the specified ID.
     */
    T get(UUID id);
}