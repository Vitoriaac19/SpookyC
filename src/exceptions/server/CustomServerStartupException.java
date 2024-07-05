package exceptions.server;

/**
 * Exception thrown when there is a custom error during server startup.
 * This exception is used to indicate specific errors that can occur during server startup.
 */
public class CustomServerStartupException extends Exception {
    /**
     * Constructs a new CustomServerStartupException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public CustomServerStartupException(String message) {
        super(message);
    }

    /**
     * Constructs a new CustomServerStartupException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public CustomServerStartupException(String message, Throwable cause) {
        super(message, cause);
    }
}
