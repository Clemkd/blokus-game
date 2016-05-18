package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Window implements Runnable , ActionListener 
{
	private static final int UPDATE_DELAY = 15;
	
	private JFrame frame;
	private GraphicsPanel gameGraphics;

	private Timer timer;

	private long startTime;

	public Window(String name, int width, int height) {
		this.frame = new JFrame();
		this.frame.setName(name);
		this.frame.setSize(width, height);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameGraphics = new GraphicsPanel();
		this.frame.getContentPane().add(this.gameGraphics, BorderLayout.CENTER);
		this.timer = new Timer(UPDATE_DELAY, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.timer) {
			float elapsedTime = (System.nanoTime() - this.startTime)/100000f;
			this.startTime = System.nanoTime();
			
			this.gameGraphics.update(elapsedTime);
		}
	}

	@Override
	public void run() {
		this.frame.setVisible(true);
		this.frame.setResizable(false);
		this.startTime = System.nanoTime();
		this.timer.start();
	}
}
