package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;import navigation.Page;
import program.Program;
import utilities.Vector2;

public class BlokusNumericUpDown implements DrawableInterface, ActionListener{
	
	private String text;
	
	private BlokusButton up;
	
	private BlokusButton down;
	
	private Vector2 position;
	
	private Vector2 positionText;
	
	private Dimension size;
	
	private ArrayList<ActionListener> listeners;
	
	private Font font;
	
	private Rectangle2D textSize;
	
	private boolean isEnabled;
	
	public BlokusNumericUpDown(String text, Font font) {
		this.text = text;
		this.font = font;
		this.position = new Vector2();
		this.positionText = new Vector2();
		this.size = new Dimension();
		this.up = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"boxvolumeplus.png"));
		this.down = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"boxvolumemoins.png"));
		
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		this.textSize = this.font.getStringBounds(this.text, frc);
	}
	
	
	@Override
	public void update(float elapsedTime) {
		this.up.update(elapsedTime);
		this.down.update(elapsedTime);
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setFont(this.font);
		g2d.setColor(Color.WHITE);
		g2d.drawString(this.text, this.positionText.getX(), this.positionText.getY()+17);
		System.out.println("position" + this.position);
		this.up.draw(g2d);
		this.down.draw(g2d);
		System.out.println("position texte" + this.positionText);
		g2d.dispose();
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 position) {
		this.down.setPosition(position);
		this.positionText.setX((int) (this.down.getSize().getWidth()+this.down.getPosition().getX()));
		this.positionText.setY(position.getY());
		this.up.setPosition(new Vector2((int) (this.positionText.getX()+this.textSize.getWidth()), position.getY()));
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}


	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public boolean isInBoundsUp(Vector2 p)
	{
		return 	p.getX() >= this.position.getX()+this.down.getSize().getWidth()+this.textSize.getWidth() &&
				p.getX() < this.up.getSize().getWidth() + this.position.getX() + this.down.getSize().getWidth()+this.textSize.getWidth() &&
				p.getY() >= this.position.getY() &&
				p.getY() < this.getSize().getHeight() + this.position.getY();
	}
	
	public boolean isInBoundsDown(Vector2 p)
	{
		return 	p.getX() >= this.position.getX() &&
				p.getX() < this.getSize().getWidth() + this.position.getX() &&
				p.getY() >= this.position.getY() &&
				p.getY() < this.getSize().getHeight() + this.position.getY();
	}
	
	public void addListener(ActionListener listener){
		this.listeners.add(listener);
	}
	
	public void raiseClickEvent(ActionEvent e)
	{
		for(ActionListener listener : this.listeners)
		{
			listener.actionPerformed(e);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof BlokusButton){
			if(e.getSource().equals(this.down)){
				Float volume = Program.optionConfiguration.getVolume();
				
			}else if(e.getSource().equals(this.up)){
				
			}
		}
	}
}
