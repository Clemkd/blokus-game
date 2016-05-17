package gui;

import java.awt.Color;
import java.awt.Graphics2D;

import entities.Tile;
import utilities.Vector2;

/**
 * @author Groupe 1
 *
 */
public class TilePanel implements DrawableInterface{
	private boolean state;
	private Color tileColor;
	
	public TilePanel(Color color) {
		this.state = false;
		this.tileColor = color;
	}

	/**
	 * Fonction qui ajout une pièce dans le panel
	 * @param t la pièce concernée
	 */
	public void addTile(Tile t)
	{
		afaire;
	}
	
	/**
	 * Fonction qui retire une pièce du panel
	 * @param t la pièce concernée
	 */
	public void removeTile(Tile t)
	{
		afaire;
	}
	
	/**
	 * Fonction qui renvoie la pièce sélectionnée lors du clic grâce à la position v
	 * @param v la position du clic
	 * @return la pièce cliquée
	 */
	public Tile getTile(Vector2<Integer> v)
	{
		afaire;
		return null;
	}
	
	/**
	 * Fonction qui rend la main au panel joueur
	 */
	public void enable()
	{
		this.state = true;
	}
	
	/**
	 * Fonction qui désactive le panel joueur
	 */
	public void disable()
	{
		this.state = false;
	}
	
	public boolean getState()
	{
		return this.state;
	}

	@Override
	public void update(float elaspedTime) {
		afaire;
	}

	@Override
	public void draw(Graphics2D g) {
		afaire;
	}
}
