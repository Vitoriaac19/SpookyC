package exceptions.quiz;

public class InvalidAnswerException extends Exception {
    public InvalidAnswerException(String message) {
        super(message);
    }
}