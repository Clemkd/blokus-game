package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window implements Runnable , ActionListener 
{
	private JFrame frame;
	private GraphicsPanel gameGraphics;

	public Window(String name, int width, int height) {
		this.frame = new JFrame();
		this.frame.setName(name);
		this.frame.setSize(width, height);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameGraphics = new GraphicsPanel();
		this.frame.add(gameGraphics);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	@Override
	public void run() {
		this.frame.setVisible(true);
		this.frame.setResizable(false);
	}

}
