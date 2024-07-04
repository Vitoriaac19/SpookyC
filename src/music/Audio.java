package music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * The Audio class is responsible for playing a sound file.
 * It uses Java Sound API to load and play the audio file specified by the URL.
 */
public class Audio {

    /**
     * Plays the audio file specified as "creepy-sound.wav".
     * This method loads the audio file from the resources, initializes the Clip,
     * opens the audio input stream, and starts playing the audio.
     * If any error occurs during the process, the stack trace is printed.
     */
    public void playAudio() {
        // URL of the sound file
        URL sound = Audio.class.getResource("creepy-sound.wav");
        try {
            // Obtain a Clip instance
            Clip clip = AudioSystem.getClip();
            // Get the audio input stream from the URL
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(sound);
            // Open the clip and start playing the audio
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            // Print the stack trace if any exception occurs
            e.printStackTrace();
        }
    }
}
