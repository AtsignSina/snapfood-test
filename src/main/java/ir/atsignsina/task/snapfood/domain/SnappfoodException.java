package ir.atsignsina.task.snapfood.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SnappfoodException extends RuntimeException {
    private final Map<String, Object> parameters = new LinkedHashMap<>();

    public SnappfoodException(String message) {
        super(message);
    }

    public Map<String, Object> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public SnappfoodException param(String key, Object value) {
        this.parameters.put(key, value);
        return this;
    }
}
