package gui;

import java.awt.Graphics2D;

import entities.Tile;
import utilities.Vector2;

/**
 * @author Groupe1
 *
 */
public class PlayerPanel implements DrawableInterface{
	private boolean state;
	private TilePanel tp1;
	private TilePanel tp2;
	
	/**
	 * Constructeur de PlayerPanel
	 * @param t1 le premier panel du joueur
	 * @param t2 le deuxième panel du joueur
	 */
	public PlayerPanel(TilePanel t1, TilePanel t2) {
		this.state = false;
		this.tp1 = t1;
		this.tp2 = t2;
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
	
	/**
	 * Fonction qui retourne le statut du panel du joueur
	 * @return le statut du panel joueur
	 */
	public boolean getState()
	{
		return this.state;
	}
	
	/**
	 * Fonction qui renvoie la pièce sélectionnée lors du clic grâce à la position v
	 * @param v la position du clic
	 * @return la pièce cliquée
	 */
	public Tile getTile(Vector2<Integer> v)
	{
		return null;
		afaire;
	}

	@Override
	public void update(float elapsedTime) {
		afaire;
	}

	@Override
	public void draw(Graphics2D g) {
		afaire;
	}
}
