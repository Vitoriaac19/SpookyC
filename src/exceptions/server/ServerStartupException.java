package exceptions.server;

/**
 * Exception thrown when there is an error during server startup.
 */
public class ServerStartupException extends RuntimeException {

    /**
     * Constructs a new ServerStartupException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method).
     * @param cause   the cause (which is saved for later retrieval by the getCause method).
     */
    public ServerStartupException(String message, Throwable cause) {
        super(message, cause);
    }
}
