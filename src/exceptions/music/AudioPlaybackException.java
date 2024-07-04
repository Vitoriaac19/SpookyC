package exceptions.music;

/**
 * Exception thrown when there's an error during audio playback.
 */
public class AudioPlaybackException extends Exception {
    /**
     * Constructs a new AudioPlaybackException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage method).
     * @param cause   the cause (which is saved for later retrieval by the getCause method).
     */
    public AudioPlaybackException(String message, Throwable cause) {
        super(message, cause);
    }
}