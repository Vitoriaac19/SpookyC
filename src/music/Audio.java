package music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Audio {
    private Clip clip;

    public void keepAudioPlaying(URL sound) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(sound);

            clip.open(inputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    

    public void stopAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
