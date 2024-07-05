package music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * The Audio class is responsible for handling audio playback.
 * It supports playing audio in a loop and playing audio once.
 */
public class Audio {
    private Clip clip;

    /**
     * Plays an audio clip in a continuous loop.
     *
     * @param sound The URL of the audio clip to play.
     */
    public void keepAudioPlaying(URL sound) {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(sound);

            clip.open(inputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays an audio clip once.
     *
     * @param sound The URL of the audio clip to play.
     */
    public void playOnce(URL sound) {
        try {
            Clip clip2 = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(sound);
            clip2.open(inputStream);
            clip2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}