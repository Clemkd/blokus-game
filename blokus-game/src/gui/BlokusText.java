package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import utilities.Vector2;

public class BlokusText implements DrawableInterface {
	
	/**
	 * Le texte
	 */
	private String text;
	
	/**
	 * La positon du texte
	 */
	private Vector2 position;
	
	/**
	 * La taille du texte
	 */
	private Dimension size;
	
	/**
	 * La police de caractère du texte
	 */
	private Font font;
	
	/**
	 * Constructeur
	 * 
	 * @param text le texte
	 * @param font la police de caractère
	 */
	public BlokusText(String text, Font font) {
		this.setText(text);
		this.position = new Vector2();
		this.font = font;
		this.setSize(new Dimension(10, 100));
	
	}
	
	/**
	 * Gestion de l'affichage du texte
	 * 
	 * @param g le graphics2D
	 * @param text le texte
	 * @param x l'abcisse 
	 * @param y l'ordonnée
	 */
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

	/**
	 * Getter de la position du texte
	 * 
	 * @return la positon du texte
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Setter de la position du texte
	 * 
	 * @param position la postion du texte
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * Getter du texte 
	 * 
	 * @return le texte
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter du texte 
	 * 
	 * @param text le texte
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter de la taille du texte
	 * 
	 * @return la taille du texte
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * Setter de la taille du texte
	 * 
	 * @param size la taille du texte
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}

	@Override
	public void update(float elapsedTime) {
		
	}
}
