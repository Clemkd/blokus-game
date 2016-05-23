package gui;

import java.awt.Graphics2D;

import utilities.Vector2;

public interface DrawableInterface {
	
	/**
	 * Fonction qui met à jour toute l'interface selon un taux value
	 * @param elapsedTime
	 */
	public void update(float elapsedTime);
	
	/**
	 * Fonction qui dessine 
	 * @param g un objet de type graphics
	 */
	public void draw(Graphics2D g);
}
