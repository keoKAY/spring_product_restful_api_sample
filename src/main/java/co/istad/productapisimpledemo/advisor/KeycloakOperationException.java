package co.istad.productapisimpledemo.advisor;

import org.springframework.http.HttpStatus;

public class KeycloakOperationException extends RuntimeException {

    private final HttpStatus status;

    public KeycloakOperationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
