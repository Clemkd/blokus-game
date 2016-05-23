package gui;

import java.awt.Graphics2D;

import entities.Tile;
import utilities.Vector2;

public class BlokusTile implements DrawableInterface {
	/**
	 * L'objet correspondant aux données à représenter
	 */
	private Tile tile;
	
	/**
	 * La position de l'objet sur la zone de dessin parente
	 */
	private Vector2 position;
	
	public BlokusTile(Tile tile)
	{
		this.position = new Vector2();
		this.tile = tile;
	}
	
	
	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Obtient la position actuelle de l'objet sur la zone de dessin parente
	 * @return La position actuelle de l'objet
	 */
	public Vector2 getPosition() {
		return this.position;
	}
	
	/**
	 * Modifie la position de l'objet sur la zone de dessin parente
	 * @param position La nouvelle position de l'objet
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * Obtient l'objet de données de la représentation
	 * @return L'objet de données
	 */
	public Tile getTile() {
		return tile;
	}
}
