package exceptions.music;

public class AudioPlaybackException extends Exception {
    public AudioPlaybackException(String message, Throwable cause) {
        super(message, cause);
    }
}