package exceptions.server;

public class ServerInterruptedException extends RuntimeException {
    public ServerInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }
}
