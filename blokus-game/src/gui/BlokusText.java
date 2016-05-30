package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import utilities.Vector2;

public class BlokusText implements DrawableInterface {
	
	private String text;
	private Vector2 position;
	private Dimension size;
	private Font font;
	
	public BlokusText(String text, Font font) {
		this.setText(text);
		this.position = new Vector2();
		this.font = font;
		this.setSize(new Dimension(10, 100));
	
	}

	
	private void drawString(Graphics2D g, String text, int x, int y) {
	      for (String line : text.split("\n"))
	          g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setFont(this.font);
		g2d.setColor(Color.WHITE);
	    drawString(g2d, this.text,  this.position.getX()+5, this.position.getY());
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
	

	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}
}
