package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedString;

import utilities.Vector2;

public class BlokusText implements DrawableInterface {
	
	private String text;
	private Vector2 position;
	private Dimension size;
	private Font font;
	private int width;
	
	public BlokusText(String text, Font font, int width) {
		this.width = width;
		this.setText(text);
		this.position = new Vector2();
		this.font = font;
		this.setSize(new Dimension(10, 100));
	
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		LineBreakMeasurer linebreaker = new LineBreakMeasurer(new AttributedString(text).getIterator(), g.getFontRenderContext());
		 float y = 0.0f;
		 while (linebreaker.getPosition() < text.length()) {
		    TextLayout tl = linebreaker.nextLayout(width);

		    y += tl.getAscent();
		    tl.draw(g, 0, y);
		    y += tl.getDescent() + tl.getLeading();
		 }
		 
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
	

	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}
}
