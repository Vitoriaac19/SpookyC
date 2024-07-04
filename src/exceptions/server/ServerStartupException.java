package exceptions.server;

public class ServerStartupException extends RuntimeException {
    public ServerStartupException(String message, Throwable cause) {
        super(message, cause);
    }
}
