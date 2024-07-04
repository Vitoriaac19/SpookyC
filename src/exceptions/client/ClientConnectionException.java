package exceptions.client;

public class ClientConnectionException extends Exception {

    public ClientConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
