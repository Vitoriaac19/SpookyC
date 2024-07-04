package exceptions.quiz;

/**
 * Exception thrown when there is an error during quiz processing.
 */
public class QuizProcessingException extends Exception {
    /**
     * Constructs a new QuizProcessingException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method).
     * @param cause   the cause (which is saved for later retrieval by the getCause method).
     */
    public QuizProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
