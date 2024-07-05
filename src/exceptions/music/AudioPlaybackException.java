package exceptions.music;

/**
 * Exception thrown when there's an error during audio playback.
 * This exception provides detailed information about the playback error,
 * including a message and an underlying cause.
 */
public class AudioPlaybackException extends Exception {
    /**
     * Constructs a new AudioPlaybackException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.
     */
    public AudioPlaybackException(String message, Throwable cause) {
        super(message, cause);
    }
}