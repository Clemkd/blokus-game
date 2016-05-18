package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import utilities.Vector2;

public class GraphicsPanel extends JComponent implements MouseMotionListener, MouseListener, MouseWheelListener, DrawableInterface {
	private static final long	serialVersionUID	= 1L;
	private Image background;


	
	public GraphicsPanel() {
		super();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D batch = (Graphics2D) g;
		this.clear(batch);
		try {
			this.background = ImageIO.read(new File("./resources/images/background.png"));
		} catch (IOException e) {
			System.out.println("erreur chargement fond");
			e.printStackTrace();
		}
		
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		Navigation.getPage().draw(batch);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	protected void clear(Graphics2D batch)
	{
		batch.setPaint(Color.WHITE);
		batch.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float elapsedTime) {
		Navigation.getPage().update(elapsedTime);
		this.repaint();
	}

	@Override
	public void draw(Graphics2D g) {
		this.repaint();
		
	}

}
