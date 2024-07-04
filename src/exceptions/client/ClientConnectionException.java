package exceptions.client;

/**
 * Exception thrown when there's an error during client connection.
 */
public class ClientConnectionException extends Exception {
    /**
     * Constructs a new ClientConnectionException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method).
     * @param cause   the cause (which is saved for later retrieval by the getCause method).
     */
    public ClientConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
