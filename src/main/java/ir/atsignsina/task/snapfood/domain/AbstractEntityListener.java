package ir.atsignsina.task.snapfood.domain;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;


public class AbstractEntityListener {
    @PrePersist
    public void addTimesPrePersist(AbstractEntity e) {
        if (e.getCreatedAt() == null) {
            e.setCreatedAt(LocalDateTime.now());
        }
        if (e.getUpdatedAt() == null) {
            e.setUpdatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void addTimesPreUpdate(AbstractEntity e) {
        if (e.getUpdatedAt() == null) {
            e.setUpdatedAt(LocalDateTime.now());
        }
    }
}
