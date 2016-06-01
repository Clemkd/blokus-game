package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import navigation.Navigation;

public class Window implements Runnable , ActionListener 
{
	private static final int UPDATE_DELAY = 15;
	
	/**
	 * La fenêtre
	 */
	private JFrame frame;
	
	/**
	 * Le dessin
	 */
	private GraphicsPanel gameGraphics;

	/**
	 * Le timer
	 */
	private Timer timer;

	/**
	 * 
	 */
	private long startTime;

	/**
	 * Le musicPlayer
	 */
	private static MusicPlayer musicPlayer = new MusicPlayer();
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 778;

	/**
	 * Constructeur d'une Window
	 * 
	 * @param name le nom de la fenêtre
	 */
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
		this.frame.setVisible(true);
		this.frame.setResizable(false);
		this.frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {
				Keyboard.setLastKeyTyped(e.getKeyCode());
			}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
		this.startTime = System.nanoTime();
		this.timer.start();
	}
	
	/**
	 * Fonction static qui permet de recuperer le MusicPlayer
	 * 
	 * @return le MusicPlayer
	 */
	public static MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}
}
