package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class OptionConfiguration implements Serializable {

	private static final long	serialVersionUID	= -1912346656700146846L;

	/**
	 * Flag de l'état de la musique de fond
	 */
	private boolean				playMusic;

	/**
	 * Flag de l'activation de l'aide
	 */
	private boolean				help;

	/**
	 * Volume de la musique de fond
	 */
	private float				volumeMusic;

	/**
	 * Flag de la sauvegarde automatique
	 */
	private boolean				autoSave;

	/**
	 * keyCode des touches utilisées par le jeu
	 */
	private int					keyRotateClockwise;
	private int					keyRotateCounterClockwise;
	private int					keySymetryClockwise;
	private int					keySymetryCounterClockwise;
	private int					keyReturn;

	private float volumeSFX;

	private boolean playSFX;

	private boolean firstLaunch;

	/**
	 * Constructeur
	 */
	public OptionConfiguration() {
		this.playMusic = true;
		this.playSFX = true;
		this.autoSave = true;
		this.help = false;
		this.volumeMusic = 0.45f;
		this.volumeSFX = 0.65f;
		this.firstLaunch = true;

		this.keyRotateClockwise = 39;
		this.keyRotateCounterClockwise = 37;
		this.keySymetryClockwise = 38;
		this.keySymetryCounterClockwise = 40;
		this.keyReturn = 27;
	}

	public boolean isFirstLaunch() {
		return this.firstLaunch;
	}
	
	public void setFirstLaunch(boolean b) {
		this.firstLaunch = b;
	}
	
	/**
	 * Getter du flag son
	 * 
	 * @return le flag du son
	 */
	public boolean isPlayMusic() {
		return this.playMusic;
	}

	/**
	 * Getter du flag de l'aide
	 * 
	 * @return le flag de l'aide
	 */
	public boolean isHelp() {
		return this.help;
	}

	/**
	 * Getter du volume
	 * 
	 * @return le volume
	 */
	public float getVolumeMusic() {
		return this.volumeMusic;
	}

	/**
	 * Getter du flag de la sauvegarde automatique
	 * 
	 * @return le flag de la sauvegarde automatique
	 */
	public boolean isAutoSave() {
		return this.autoSave;
	}

	/**
	 * Setter du flag de l'aide
	 * 
	 * @param help
	 */
	public void setHelp(boolean help) {
		this.help = help;
	}

	/**
	 * Setter du volume
	 * 
	 * @param volume
	 *            le volume
	 */
	public void setVolumeMusic(float volume) {
		this.volumeMusic = volume;
	}

	/**
	 * Setter du flag de la sauvegarde automatique
	 * 
	 * @param autoSave
	 */
	public void setAutoSave(boolean autoSave) {
		this.autoSave = autoSave;
	}

	/**
	 * Setter du flag de l'état de la musique de fond
	 * 
	 * @param playSong
	 */
	public void setPlayMusic(boolean playSong) {
		this.playMusic = playSong;
	}
	
	public int getKeyRotateClockwise() {
		return this.keyRotateClockwise;
	}

	public void setKeyRotateClockwise(int keyRotateClockwise) {
		this.keyRotateClockwise = keyRotateClockwise;
	}

	public int getKeyRotateCounterClockwise() {
		return this.keyRotateCounterClockwise;
	}

	public void setKeyRotateCounterClockwise(int keyRotateCounterClockwise) {
		this.keyRotateCounterClockwise = keyRotateCounterClockwise;
	}

	public int getKeySymetryClockwise() {
		return this.keySymetryClockwise;
	}

	public void setKeySymetryClockwise(int keySymetryClockwise) {
		this.keySymetryClockwise = keySymetryClockwise;
	}

	public int getKeySymetryCounterClockwise() {
		return this.keySymetryCounterClockwise;
	}

	public void setKeySymetryCounterClockwise(int keySymetryCounterClockwise) {
		this.keySymetryCounterClockwise = keySymetryCounterClockwise;
	}

	public int getKeyReturn() {
		return this.keyReturn;
	}

	public void setKeyReturn(int keyReturn) {
		this.keyReturn = keyReturn;
	}

	/**
	 * Méthode statique qui permet de charger les configurations
	 * 
	 * @return les options
	 */
	public static OptionConfiguration loadOption() {
		OptionConfiguration option = null;
		File fileConfig = new File("blokus.config");
		if (fileConfig.exists()) {
			try {
				FileInputStream config = new FileInputStream("blokus.config");
				ObjectInputStream in = new ObjectInputStream(config);
				option = (OptionConfiguration) in.readObject();
				in.close();
				config.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (ClassNotFoundException c) {
				c.printStackTrace();
			}
		}
		else {
			option = new OptionConfiguration();
			option.saveConfiguration();
		}
		return option;
	}

	/**
	 * Méthode permettant de sauvegarder les options de configuration
	 */
	public void saveConfiguration() {
		try {
			FileOutputStream fileOption = new FileOutputStream("blokus.config");
			ObjectOutputStream out = new ObjectOutputStream(fileOption);
			out.writeObject(this);
			out.close();
			fileOption.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isPlaySFX() {
		return this.playSFX;
	}
	
	public void setPlaySFX(boolean b) {
		this.playSFX = b;
	}
	
	public float getVolumeSFX() {
		return this.volumeSFX;
	}
	
	public void setVolumeSFX(float v) {
		this.volumeSFX = v;
	}
}
