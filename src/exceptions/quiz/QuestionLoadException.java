package exceptions.quiz;

public class QuestionLoadException extends Exception {
    public QuestionLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}