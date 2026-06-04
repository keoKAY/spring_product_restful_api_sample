package co.istad.productapisimpledemo.advisor;

import co.istad.productapisimpledemo.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestControllerAdvisor {
    // handle not found issue


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<?>> handleMethodNotValidException(MethodArgumentNotValidException exception ){
        Map<String,String > errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(
                error ->
                        errors.put(error.getField(), error.getDefaultMessage())
        );
        // should use entity response for better message
        return new ResponseEntity<>(
                ErrorResponse.builder().
                        message("Provided data is invalid")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .errors(errors)
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
