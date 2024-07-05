package exceptions.server;

/**
 * Exception thrown when there is an error while handling a client in the server.
 * This exception indicates issues encountered during client handling operations.
 */
public class ClientHandlingException extends RuntimeException {
    /**
     * Constructs a new ClientHandlingException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public ClientHandlingException(String message, Throwable cause) {
        super(message, cause);
    }
}
