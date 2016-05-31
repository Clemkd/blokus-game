package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import navigation.Page;
import utilities.Vector2;

public class BlokusWaitPress implements DrawableInterface {

	private boolean isEnabled;
	private boolean isChecked;
	private BufferedImage butonFrame;
	private String text;
	private Vector2 position;
	private Dimension size;
	private ArrayList<ActionListener> listeners;
	private Font font;
	private Rectangle2D textSize;
	
	
	
	private BlokusWaitPress(boolean enabled, boolean checked, String text, Font font){
		this.isChecked = checked;
		this.isEnabled = enabled;
		this.text = "";
		this.font = null;
		this.position = new Vector2();
		this.size = new Dimension(20, 20);
		this.listeners = new ArrayList<ActionListener>();
		this.text = text;
		this.font = font;
		
		try {
			this.butonFrame = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"box.png"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		this.textSize = this.font.getStringBounds(this.text, frc);
	}
	
	public boolean isInBounds(Vector2 p)
	{
		return 	p.getX() >= this.position.getX() &&
				p.getX() < this.getSize().getWidth() + this.position.getX() + this.textSize.getWidth() &&
				p.getY() >= this.position.getY() &&
				p.getY() < this.getSize().getHeight() + this.position.getY();
	}
	
	
	@Override
	public void update(float elapsedTime) {
		if(this.isInBounds(Mouse.getPosition()) && this.isEnabled){
			if(Mouse.getLastMouseButton() == Mouse.LEFT && !Mouse.isReleased()){
				Mouse.consumeLastMouseButton();
			}
		}	
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawImage(butonFrame, this.position.getX(), this.position.getY(), (int) this.size.getWidth(), (int) this.size.getHeight(), null);
		g2d.setColor(Color.WHITE);
		g2d.setFont(this.font);
		g2d.drawString(this.text, this.position.getX(), this.position.getY());
		g2d.dispose();
		
	}
	
	public Dimension getSize() {
		return size;
	}
	
	

}
