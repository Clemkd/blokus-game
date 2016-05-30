package utilities;

import java.io.Serializable;

public class OptionConfiguration implements Serializable {
	
	private boolean playSong;
	
	public boolean isPlaySong() {
		return playSong;
	}

	public boolean isHelp() {
		return help;
	}

	public float getVolume() {
		return volume;
	}

	public boolean isAutoSave() {
		return autoSave;
	}

	public boolean isDaltonienMode() {
		return daltonienMode;
	}

	private boolean help;
	
	private float volume;
	
	private boolean autoSave;
	
	private boolean daltonienMode;
	
	public OptionConfiguration() {
		
	}
}
