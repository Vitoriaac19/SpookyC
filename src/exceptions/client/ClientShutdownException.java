package exceptions.client;

/**
 * Exception thrown when there's an error during client shutdown.
 * This exception provides detailed information about the shutdown error,
 * including a message and an underlying cause.
 */
public class ClientShutdownException extends Exception {
    /**
     * Constructs a new ClientShutdownException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public ClientShutdownException(String message, Throwable cause) {
        super(message, cause);
    }
}
