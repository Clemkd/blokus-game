package entities;
import java.util.ArrayList;
import java.util.List;

import utilities.Vector2;

public abstract class Player {
	/**
	 * Les deux couleurs du joueur
	 */
	protected Vector2<CellColor> colors;
	
	/**
	 * Les tuiles du joueur
	 */
	protected List<Tile> tiles;
	
	public Player(Vector2<CellColor> colors) {
		this.colors = colors;
		this.tiles = new ArrayList<Tile>();
	}
	
	/**
	 * Obtient la liste des tuiles du joueur
	 * @return La liste des tuiles
	 */
	public List<Tile> getTileInventory()
	{
		return this.tiles;
	}
	
	/**
	 * Obtient le couple de couleurs du joueur
	 * @return Le couple de couleurs
	 */
	public Vector2<CellColor> getColors() {
		return this.colors;
	}
	
	/**
	 * Le jeu du joueur
	 * @return La position voulu
	 */
	public abstract Vector2<Integer> play();
}
