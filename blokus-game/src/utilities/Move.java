package utilities;

import entities.Tile;

public class Move {
	private final Vector2<Integer> position;
	private final Tile tile;
	private int value;
	
	/**
	 * Crée un coup nul avec la valeur indiquée
	 * @param value Valeur du coup
	 */
	public Move(int value) {
		this.value = value;
		this.tile = null;
		this.position = null;
	}
	
	/**
	 * Crée un coup avec les valeurs indiquées
	 * @param position Position de la première cellule du tile
	 * @param tile Tile utilisé
	 * @param value	Valeur du coup
	 */
	public Move(Vector2<Integer> position, Tile tile, int value) {
		this.value = value;
		this.tile = tile;
		this.position = position;
	}
	
	/**
	 * Crée un coup avec les valeurs indiquées
	 * @param position Position de la première cellule du tile
	 * @param tile Tile utilisé
	 */
	public Move(Vector2<Integer> position, Tile tile) {
		this.value = 0;
		this.tile = tile;
		this.position = position;
	}
	
	/**
	 * Transmet la position de la première cellule du tile
	 * @return Position
	 */
	public Vector2<Integer> getPosition() {
		return this.position;
	}
	
	/**
	 * Transmet le Tile joué
	 * @return Tile
	 */
	public Tile getTile() {
		return this.tile;
	}
	
	/**
	 * Change la valeur du coup
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Transmet la valeur du coup
	 * @return Valeur du coup
	 */
	public int getValue() {
		return this.value;
	}
}
