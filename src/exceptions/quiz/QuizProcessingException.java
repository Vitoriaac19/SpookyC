package exceptions.quiz;

/**
 * Exception thrown when there is an error during quiz processing.
 * This exception provides detailed information about the quiz processing error,
 * including a message and an underlying cause.
 */
public class QuizProcessingException extends Exception {
    /**
     * Constructs a new QuizProcessingException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public QuizProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
