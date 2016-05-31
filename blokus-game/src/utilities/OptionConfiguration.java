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

	private boolean playSong;
	
	private boolean help;
	
	private float volume;
	
	private boolean autoSave;
	
	private boolean daltonienMode;
	
	public OptionConfiguration(){
		this.playSong = true;
		this.autoSave = true;
		this.daltonienMode = false;
		this.help = false;
		this.volume = 1.0f;
	}
	
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
	
	public void setHelp(boolean help) {
		this.help = help;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public void setAutoSave(boolean autoSave) {
		this.autoSave = autoSave;
	}

	public void setDaltonienMode(boolean daltonienMode) {
		this.daltonienMode = daltonienMode;
	}
	
	public void setPlaySong(boolean playSong) {
		this.playSong = playSong;
	}
	
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
