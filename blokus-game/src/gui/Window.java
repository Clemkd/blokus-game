package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import navigation.Navigation;

public class Window implements Runnable , ActionListener 
{
	private static final int UPDATE_DELAY = 15;
	
	private JFrame frame;
	private GraphicsPanel gameGraphics;

	private Timer timer;

	private long startTime;

	private static MusicPlayer musicPlayer = new MusicPlayer("/test.wav");
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 800;

	public Window(String name) {
		this.frame = new JFrame();
		this.frame.setName(name);
		this.frame.setSize(WIDTH, HEIGHT);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameGraphics = new GraphicsPanel();
		this.frame.getContentPane().add(this.gameGraphics, BorderLayout.CENTER);
		
		Navigation.NavigateTo(Navigation.homePage);
		this.timer = new Timer(UPDATE_DELAY, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.timer) {
			float elapsedTime = (System.nanoTime() - this.startTime)/1000000f;
			this.startTime = System.nanoTime();
			
			this.gameGraphics.update(elapsedTime);
		}
	}

	@Override
	public void run() {
		musicPlayer.playSound();
		this.frame.setVisible(true);
		this.frame.setResizable(false);
		this.startTime = System.nanoTime();
		this.timer.start();
		
	}
	
	public static MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}
}
