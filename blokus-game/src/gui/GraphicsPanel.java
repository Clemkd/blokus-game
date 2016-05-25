package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import navigation.Navigation;
import navigation.Page;
import utilities.Vector2;

public class GraphicsPanel extends JComponent implements MouseMotionListener, MouseListener, MouseWheelListener {
	private static final long	serialVersionUID	= 1L;
	private static final boolean DEBUG = false;
	
	private Image				background;
	private long				framesRendered;
	private double				timeElapsed;
	private int					approxFPS;

	public GraphicsPanel() {
		super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		try {
			this.background = ImageIO.read(getClass().getResource("/images/background.png"));
		}
		catch (IOException e) {
			System.out.println("erreur chargement fond");
			e.printStackTrace();
		}
		this.framesRendered = 0;
		this.timeElapsed = 0.0;
		this.approxFPS = 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		this.clear(batch);

		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		Navigation.getPage().draw(batch);
		
		this.framesRendered++;
		if(DEBUG) {
			batch.setColor(new Color(240,240,240,190));
			batch.fillRect(0, 0, 140, 65);
			batch.setColor(Color.BLACK);
			batch.drawString(String.valueOf(this.framesRendered), 5, 15);
			batch.drawString(String.valueOf(this.timeElapsed), 5, 30);
			batch.drawString(String.valueOf(this.framesRendered / this.timeElapsed), 5, 45);
			batch.drawString(String.valueOf(this.approxFPS), 5, 60);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Mouse.setLastMouseButton(e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Mouse.setLastMouseButton(e.getButton());
		Mouse.setMouseState(Mouse.MOUSE_PRESSED);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Mouse.setLastMouseButton(e.getButton());
		Mouse.setMouseState(Mouse.MOUSE_RELEASED);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Mouse.setMouseState(Mouse.MOUSE_PRESSED);
		Mouse.setMousePosition(new Vector2(e.getX(), e.getY()));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Mouse.setMousePosition(new Vector2(e.getX(), e.getY()));
	}

	protected void clear(Graphics2D batch) {
		batch.setPaint(Color.WHITE);
		batch.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Mouse.setLastScrollClicks(e.getWheelRotation());
	}

	public void update(float elapsedTime) {
		this.timeElapsed += elapsedTime / 1000f;
		this.approxFPS = Math.round(1000/elapsedTime);
		Navigation.getPage().update(elapsedTime);
		this.repaint();
	}
}
