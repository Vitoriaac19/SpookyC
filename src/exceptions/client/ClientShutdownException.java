package exceptions.client;

/**
 * Exception thrown when there's an error during client shutdown.
 */
public class ClientShutdownException extends Exception {
    /**
     * Constructs a new ClientShutdownException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method).
     * @param cause   the cause (which is saved for later retrieval by the getCause method).
     */
    public ClientShutdownException(String message, Throwable cause) {
        super(message, cause);
    }
}
