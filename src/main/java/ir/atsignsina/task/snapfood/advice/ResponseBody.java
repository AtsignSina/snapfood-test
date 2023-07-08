package ir.atsignsina.task.snapfood.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Builder
public class ResponseBody {
    private final String error;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, Object> parameters;
}