package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import utilities.Vector2;

public class BlokusWaitPress implements DrawableInterface {

	private boolean isEnabled;
	private String text;
	private Vector2 position;
	private Dimension size;
	private ArrayList<ActionListener> listeners;
	private Font font;
	private boolean waiting;
	private int keyCode;
	
	public BlokusWaitPress(boolean enabled, int defaultKeyCode, String text, Font font){
		this.isEnabled = enabled;
		this.position = new Vector2();
		this.size = new Dimension(32, 32);
		this.listeners = new ArrayList<ActionListener>();
		this.text = text;
		this.font = font;
		this.waiting = false;
		this.keyCode = defaultKeyCode;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
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
	
	public boolean getWaiting() {
		return this.waiting;
	}
	
	public int getKeyCode() {
		return this.keyCode;
	}
	
	public void setKeyCode(int k) {
		this.keyCode = k;
	}
	
	public boolean isInBounds(Vector2 p)
	{
		return 	p.getX() >= this.position.getX() &&
				p.getX() < this.getSize().getWidth() + this.position.getX() &&
				p.getY() >= this.position.getY() &&
				p.getY() < this.getSize().getHeight() + this.position.getY();
	}
	
	@Override
	public void update(float elapsedTime) {
		if(this.isInBounds(Mouse.getPosition()) && this.isEnabled){
			if(this.waiting==false) {
				if(Mouse.getLastMouseButton() == Mouse.LEFT && !Mouse.isReleased()) {
					this.waiting = true;
					Keyboard.consumeLastKeyTyped();
					Mouse.consumeLastMouseButton();
				}
			}
			else {
				if(Mouse.getLastMouseButton() == Mouse.RIGHT && !Mouse.isReleased()) {
					this.waiting = false;
					Mouse.consumeLastMouseButton();
				}
				if(Keyboard.getLastKeyTyped()!=Keyboard.NONE) {
					this.keyCode = Keyboard.getLastKeyTyped();
					this.waiting = false;
					Keyboard.consumeLastKeyTyped();
				}
			}
		}	
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.BLACK);
		g2d.drawRect(this.position.getX(), this.position.getY(), (int) this.size.getWidth(), (int) this.size.getHeight());
		g2d.setColor(Color.WHITE);
		g2d.setFont(this.font);
		g2d.drawString(this.text + " " + this.keyCode, this.position.getX(), this.position.getY());
		g2d.dispose();
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
