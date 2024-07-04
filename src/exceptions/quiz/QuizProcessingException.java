package exceptions.quiz;

public class QuizProcessingException extends Exception {
    public QuizProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
