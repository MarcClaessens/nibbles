package marcclaessens.nibbles;

import marcclaessens.soundutil.ResourceSound;
import marcclaessens.soundutil.Sound;

public enum GameSounds {
	WIN("/sounds/floop2_x.mp3"),
	LOSE("/sounds/hit_with_frying_pan_y.mp3"),
	LEVEL_UP0("/sounds/completely_different1_x.mp3"),
	LEVEL_UP1("/sounds/completely_different2.mp3");
	
	private Sound sound;
	
	GameSounds(String path) {
		sound = new ResourceSound(path);
	}
	
	public Sound getSound() {
		return sound;
	}

}
