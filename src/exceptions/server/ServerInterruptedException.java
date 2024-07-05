package exceptions.server;

/**
 * Exception thrown when a server thread is interrupted unexpectedly.
 * This exception indicates that an interruption occurred in a server thread unexpectedly.
 */
public class ServerInterruptedException extends RuntimeException {
    /**
     * Constructs a new ServerInterruptedException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public ServerInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }
}
