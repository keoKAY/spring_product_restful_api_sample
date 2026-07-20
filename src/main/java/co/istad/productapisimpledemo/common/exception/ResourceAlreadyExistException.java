package co.istad.productapisimpledemo.common.exception;

// this , this()
// super, super()..

// exception used when resource is already exists
public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
