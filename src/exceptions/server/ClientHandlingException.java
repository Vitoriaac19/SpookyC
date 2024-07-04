package exceptions.server;

/**
 * Exception thrown when there is an error while handling a client in the server.
 */
public class ClientHandlingException extends RuntimeException {
    /**
     * Constructs a new ClientHandlingException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method).
     * @param cause   the cause (which is saved for later retrieval by the getCause method).
     */
    public ClientHandlingException(String message, Throwable cause) {
        super(message, cause);
    }
}
