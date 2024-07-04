package exceptions.quiz;

/**
 * Exception thrown when there is an error loading questions for a quiz.
 */
public class QuestionLoadException extends Exception {
    /**
     * Constructs a new QuestionLoadException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method).
     * @param cause   the cause (which is saved for later retrieval by the getCause method).
     */
    public QuestionLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}