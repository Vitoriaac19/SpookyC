package exceptions.quiz;

/**
 * Exception thrown when the user provides an invalid answer during quiz processing.
 */
public class InvalidAnswerException extends Exception {
    /**
     * Constructs a new InvalidAnswerException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method).
     */
    public InvalidAnswerException(String message) {
        super(message);
    }
}