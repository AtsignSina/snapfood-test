package ir.atsignsina.task.snapfood.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vastik.spring.data.faker.annotation.FakeDateNow;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AbstractEntityListener.class)
public class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @FakeDateNow
    private LocalDateTime createdAt;
    @FakeDateNow
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false;
        }
        return Objects.equals(id, ((AbstractEntity) obj).id);
    }
}
