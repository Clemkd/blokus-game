package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import utilities.Vector2;

public class BlokusLabel implements DrawableInterface {
	
	/**
	 * Le texte
	 */
	private String text;
	
	/**
	 * La position
	 */
	private Vector2 position;
	
	/**
	 * La taille 
	 */
	private Dimension size;
	
	/**
	 * La police de caractère
	 */
	private Font font;
	
	/**
	 * Constructeur d'un BlokusLabel
	 * 
	 * @param text le texte
	 * @param font le police de caractère
	 */
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

	/**
	 * Obtient la position du BlokusLabel
	 * 
	 * @return la position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Setter de la position du label
	 * 
	 * @param position la position
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
	 * Getter de la taille 
	 * 
	 * @return la taille
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * Setter de la taille
	 * 
	 * @param size la taille
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}
}
