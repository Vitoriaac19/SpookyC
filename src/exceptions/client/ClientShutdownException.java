package exceptions.client;

public class ClientShutdownException extends Exception {
    public ClientShutdownException(String message, Throwable cause) {
        super(message, cause);
    }
}
