package gui;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl;

public class MusicPlayer {
	private String			soundPath;
	private Clip			clip;
	private FloatControl	gainControl;

	/**
	 * Initialise la gestion de la musique avec le fichier fourni
	 * @param path Chemin du fichier son(format wav exigé)
	 */
	public MusicPlayer(String path) {
		this.soundPath = path;
		this.clip = null;
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(soundPath));
			this.clip = AudioSystem.getClip();
			this.clip.open(audioIn);
			this.gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
			this.gainControl.setValue(-10.0f);
		}
		catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Demarre la musique
	 */
	public void playSound() {
		if (this.clip != null) {
			this.clip.loop(Clip.LOOP_CONTINUOUSLY);
			this.clip.start();
		}
	}

	/**
	 * Stoppe la musique
	 */
	public void stopSound() {
		if (this.clip != null) {
			this.clip.stop();
		}
	}

	/**
	 * Change le volume de sortie de la musique
	 * @param modifier Nouveau modificateur de volume, une valeur négative réduira ce dernier d'autant de décibels.
	 */
	public void setVolume(float modifier) {
		this.gainControl.setValue(modifier);
	}
}
