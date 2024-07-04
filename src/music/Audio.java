package music;

import exceptions.music.AudioPlaybackException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Audio {
    public void playAudio() throws AudioPlaybackException {
        URL sound = Audio.class.getResource("creepy-sound.wav");
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(sound);
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            throw new AudioPlaybackException("Error playing audio", e);
        }
    }
}
