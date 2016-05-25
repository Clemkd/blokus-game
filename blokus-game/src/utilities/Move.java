package utilities;

import entities.Tile;

public class Move {
	private final Vector2 position;
	private final Tile tile;
	private int value;
	private Vector2 tileOrigin;
	
	/**
	 * Crée un coup nul avec la valeur indiquée
	 * @param value Valeur du coup
	 */
	public Move(int value) {
		this.value = value;
		this.tile = null;
		this.position = null;
		this.tileOrigin = null;
	}
	
	/**
	 * Crée un coup avec les valeurs indiquées
	 * @param position Future position de la cellule d'origine du Tile sur le plateau
	 * @param tile Tile utilisé
	 * @param tileOrigin Position de la cellule utilisée dans le Tile
	 * @param value	Valeur du coup
	 */
	public Move(Vector2 position, Tile tile, Vector2 tileOrigin, int value) {
		this.value = value;
		this.tile = tile;
		this.position = position;
		this.tileOrigin = tileOrigin;
	}
	
	/**
	 * Crée un coup avec les valeurs indiquées
	 * @param position Future position de la cellule d'origine du Tile sur le plateau
	 * @param tile Tile utilisé
	 * @param tileOrigin Position de la cellule utilisée dans le Tile
	 */
	public Move(Vector2 position, Tile tile, Vector2 tileOrigin) {
		this.value = 0;
		this.tile = tile;
		this.position = position;
		this.tileOrigin = tileOrigin;
	}
	
	/**
	 * Transmet la future position de la cellule d'origine du Tile sur le plateau
	 * @return Position
	 */
	public Vector2 getPosition() {
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
	 * @param value Valeur du coup
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

	public Vector2 getTileOrigin() {
		return this.tileOrigin;
	}
}
