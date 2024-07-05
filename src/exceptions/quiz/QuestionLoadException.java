package exceptions.quiz;

/**
 * Exception thrown when there is an error loading questions for a quiz.
 * This exception provides detailed information about the error encountered
 * during the question loading process, including a message and an underlying cause.
 */
public class QuestionLoadException extends Exception {
    /**
     * Constructs a new QuestionLoadException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public QuestionLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}