package co.istad.productapisimpledemo.advisor;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleMethodNotValidException(MethodArgumentNotValidException exception ){
        Map<String,String > errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(
                error ->
                        errors.put(error.getField(), error.getDefaultMessage())
        );
        // should use entity response for better message
        return errors;
    }
}
