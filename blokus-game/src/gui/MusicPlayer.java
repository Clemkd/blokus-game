package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicPlayer {
	private String soundPath;
	private Player player;
	private Thread thread;

	public MusicPlayer(String path) {
		this.soundPath = path;
		this.thread = null;
	}
	
	public void playSound() {
		if(this.player == null) {
			try {
				InputStream is = getClass().getResourceAsStream(soundPath);
				player = new Player(is);
			}
			catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			this.thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						player.play();
						player = null;
					}
					catch (JavaLayerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			this.thread.start();
		}
	}

	public void stopSound() {
		if(this.player != null) {
			this.player.close();
			this.thread.stop();
		}
	}

	public boolean isTerminated() {
		return (this.player == null);
	}
}
