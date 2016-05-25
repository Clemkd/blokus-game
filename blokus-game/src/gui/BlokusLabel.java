package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import utilities.Vector2;

public class BlokusLabel implements DrawableInterface {
	
	private String text;
	private Vector2 position;
	private Dimension size;
	private Font font;
	
	public BlokusLabel(String text, Font font) {
		this.setText(text);
		this.position = new Vector2();
		this.font = font;
		this.setSize(new Dimension(10, 100));
	}

	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(new Color(0, 0, 0, 60));
		g2d.fillRect(this.position.getX(), this.position.getY(), 807, 35);
		g2d.setFont(this.font);
		g2d.setColor(Color.WHITE);
		g2d.drawString(this.text, this.position.getX()+5, this.position.getY()+25);
		g2d.dispose();
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
}
