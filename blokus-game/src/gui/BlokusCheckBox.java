package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import navigation.Page;
import utilities.BufferedImageHelper;
import utilities.Vector2;

public class BlokusCheckBox implements DrawableInterface {
	
	private boolean isEnabled;
	
	private boolean isChecked;
	
	private BufferedImage checked;
	
	private BufferedImage noChecked;
	
	private BufferedImage checkedHover;
	
	private BufferedImage noCheckedHover;
	
	private Vector2 position;
	
	private boolean mouseHover;
	
	private Dimension size;
	
	private ArrayList<ActionListener> listeners;
	
	public BlokusCheckBox(boolean enabled, boolean checked) {
		this.mouseHover = false;
		this.isChecked = checked;
		this.isEnabled = enabled;
		this.position = new Vector2();
		this.size = new Dimension(20, 20);
		this.listeners = new ArrayList<ActionListener>();
		try {
			this.checked = ImageIO.read(new File(Page.PATH_RESOURCES_BOUTONS+"box.png"));
			this.noChecked = ImageIO.read(new File(Page.PATH_RESOURCES_BOUTONS+"boxchecked.png"));
			this.checkedHover = BufferedImageHelper.generateMask(this.checked, Color.BLACK, 0.5f);
			this.noCheckedHover = BufferedImageHelper.generateMask(this.noChecked, Color.BLACK, 0.5f);
			this.checkedDisable = BufferedImageHelper.generateMask(this.checked, Color.BLACK, 0.5f);
			this.noCheckedDisable = BufferedImageHelper.generateMask(this.noChecked, Color.BLACK, 0.5f);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public boolean isInBounds(Vector2 p)
	{
		return p.getX() >= this.position.getX() &&
				p.getX() < this.getSize().getWidth() + this.position.getX() &&
				p.getY() >= this.position.getY() &&
				p.getY() < this.getSize().getHeight() + this.position.getY();
	}
	
	@Override
	public void update(float elapsedTime) {
		
		if(this.isInBounds(Mouse.getPosition()) && this.isEnabled)
		{
			this.mouseHover = true;
			if(Mouse.getLastMouseButton() == Mouse.LEFT)
			{
				if(this.isChecked) {
					if(Mouse.isReleased()) {
						this.raiseClickEvent(new ActionEvent(this, 0, null));
						this.isChecked = false;
					}
				}
				else {
					this.isChecked = true;
				}
			}
		}
		else
		{
			this.mouseHover = false;
			this.isChecked = false;
		}
		
		if(this.isChecked && Mouse.isReleased())
		{
			this.isChecked = false;
		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		/*
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawImage(this.isChecked ?this.checked: this.noChecked, this.position.getX(), this.position.getY(), (int) this.size.getWidth(), (int) this.size.getHeight(), null);
		g2d.dispose();
		*/
		
		if(this.backgroundImage != null)
		{
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawImage(this.checked, 
					this.position.getX(), 
					this.position.getY(), 
					(int) this.size.getWidth(), 
					(int) this.size.getHeight(), 
					null);
			
			if(this.mouseHover)
			{
				g2d.drawImage(this.checkHover,
						this.position.getX(),
						this.position.getY(), 
						(int)this.size.getWidth(),
						(int)this.size.getHeight(),
						null);
			}
			else if(!this.isEnabled)
			{
				g2d.drawImage(this.backgroundImageDisable,
						this.position.getX(),
						this.position.getY(), 
						(int)this.size.getWidth(),
						(int)this.size.getHeight(),
						null);
			}
			
			g2d.dispose();
		}
	}
	
	public void setChecked(){
		this.isChecked = !isChecked;
	}
	
	public void addListener(ActionListener listener){
		this.listeners.add(listener);
	}
	
	
	/**
	 * Lance l'évènement de clique bouton auprès de tous ses listeners
	 * @param e Les information sur l'évènement
	 */
	public void raiseClickEvent(ActionEvent e)
	{
		for(ActionListener listener : this.listeners)
		{
			listener.actionPerformed(e);
		}
	}
}
