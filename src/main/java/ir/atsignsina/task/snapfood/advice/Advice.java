package ir.atsignsina.task.snapfood.advice;

import ir.atsignsina.task.snapfood.domain.SnappfoodException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

@ControllerAdvice
public class Advice {
    @Value("${spring.messages.basename}")
    private String baseName = "i18/messages";

    @ExceptionHandler(SnappfoodException.class)
    public ResponseEntity<ResponseBody> handleSnappfoodException(SnappfoodException exception) {
        return ResponseEntity.status(status(exception)).body(ResponseBody.builder().error(exception.getMessage()).message(message(exception.getMessage())).parameters(exception.getParameters()).build());
    }

    int status(SnappfoodException exception) {
        ResponseStatus rs = exception.getClass().getAnnotation(ResponseStatus.class);
        if (rs == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        } else {
            return rs.value().value();
        }
    }

    String message(String message) {
        String errorKey = String.format("error.%s.message", message);
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, LocaleContextHolder.getLocale());
            return resourceBundle.getString(errorKey).trim();
        } catch (MissingResourceException err) {
            return null;
        }
    }
}
