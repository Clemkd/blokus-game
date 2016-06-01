package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import gui.Window;

public class OptionConfiguration implements Serializable {
	
	private static final long serialVersionUID = -1912346656700146846L;

	/**
	 * Flag de l'état de la musique de fond
	 */
	private boolean playSong;
	
	/**
	 * Flag de l'activation de l'aide 
	 */
	private boolean help;
	
	/**
	 * Volume de la musique de fond
	 */
	private float volume;
	
	/**
	 * Flag de la sauvegarde automatique
	 */
	private boolean autoSave;
	
	/**
	 * Flag du mode daltonien
	 */
	private boolean daltonienMode;
	
	/**
	 * Constructeur
	 */
	public OptionConfiguration(){
		this.playSong = true;
		this.autoSave = true;
		this.daltonienMode = false;
		this.help = false;
		this.volume = 1.0f;
	}
	
	/**
	 * Getter du flag son
	 * 
	 * @return le flag du son
	 */
	public boolean isPlaySong() {
		return playSong;
	}

	/**
	 * Getter du flag de l'aide
	 * 
	 * @return le flag de l'aide
	 */
	public boolean isHelp() {
		return help;
	}

	/**
	 * Getter du volume
	 * 
	 * @return le volume
	 */
	public float getVolume() {
		return volume;
	}

	/**
	 * Getter du flag de la sauvegarde automatique
	 * 
	 * @return le flag de la sauvegarde automatique
	 */
	public boolean isAutoSave() {
		return autoSave;
	}

	/**
	 * Getter du flag du mode daltonien
	 * 
	 * @return le flag du mode daltonien
	 */
	public boolean isDaltonienMode() {
		return daltonienMode;
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
	 * @param volume le volume
	 */
	public void setVolume(float volume) {
		this.volume = volume;
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
	 * Setter du flag du mode daltonien
	 * 
	 * @param daltonienMode
	 */
	public void setDaltonienMode(boolean daltonienMode) {
		this.daltonienMode = daltonienMode;
	}
	
	/**
	 * Setter du flag de l'état de la musique de fond
	 * 
	 * @param playSong
	 */
	public void setPlaySong(boolean playSong) {
		this.playSong = playSong;
	}
	
	/**
	 * Méthode statique qui permet de charger les configurations
	 * 
	 * @return les options
	 */
	public static OptionConfiguration loadOption() {
		OptionConfiguration option = null;
		File fileConfig = new File("config.txt");
		if(fileConfig.exists()){
			try {
				FileInputStream config = new FileInputStream("config.txt");
				ObjectInputStream in = new ObjectInputStream(config);
				option = (OptionConfiguration) in.readObject();
				in.close();
				config.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(ClassNotFoundException c){
				c.printStackTrace();
			}
		}else{
			option = new OptionConfiguration();
		}
		return option;
	}
	
	/**
	 * Méthode permettant de sauvegarder les options de configuration
	 */
	public void saveConfiguration(){
		try {
			FileOutputStream fileOption = new FileOutputStream("config.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOption);
			out.writeObject(this);
			out.close();
			fileOption.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
