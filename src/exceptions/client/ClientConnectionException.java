package exceptions.client;

/**
 * The {@code ClientConnectionException} is thrown to indicate an error that occurs during client connection.
 * This exception provides detailed information about the error, including a message and an underlying cause.
 */
public class ClientConnectionException extends Exception {
    /**
     * Constructs a new {@code ClientConnectionException} with the specified detail message and cause.
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     * @param cause   the cause of the exception, saved for later retrieval by the {@link #getCause()} method.
     *                A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public ClientConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
