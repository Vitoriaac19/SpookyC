package exceptions.quiz;

/**
 * Exception thrown when the user provides an invalid answer during quiz processing.
 * This exception provides information about the specific error encountered
 * when an invalid answer is detected during the quiz.
 */
public class InvalidAnswerException extends Exception {
    /**
     * Constructs a new InvalidAnswerException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public InvalidAnswerException(String message) {
        super(message);
    }
}