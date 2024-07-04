package exceptions.server;

public class CustomServerStartupException extends Exception {

    public CustomServerStartupException(String message) {
        super(message);
    }

    public CustomServerStartupException(String message, Throwable cause) {
        super(message, cause);
    }
}
