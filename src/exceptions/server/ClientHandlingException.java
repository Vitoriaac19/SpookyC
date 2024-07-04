package exceptions.server;

public class ClientHandlingException extends RuntimeException {
    public ClientHandlingException(String message, Throwable cause) {
        super(message, cause);
    }
}
