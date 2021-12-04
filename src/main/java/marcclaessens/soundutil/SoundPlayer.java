package marcclaessens.soundutil;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javazoom.jl.player.Player;


public class SoundPlayer {
	public SoundPlayer() {
	}


	private void playImpl(final Sound sound) {
		try (InputStream is = sound.getSoundStream()) {
			switch (sound.getType()) {
			case MP3 :
				Player player = new Player(is);
				player.play();
				break;
			case WAV :
			case AU: 
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(is);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play(final Sound sound) {
		if (sound != null ) {
			new Thread(() -> playImpl(sound)).start();
		} 
	}
}
