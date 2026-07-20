package co.istad.productapisimpledemo.common;
import lombok.Builder;
import java.time.LocalDateTime;
@Builder
public record ErrorResponse<T>(
        LocalDateTime timestamp ,
        String message,
        T errors,
        Integer status
) {
}


