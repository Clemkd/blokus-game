package utilities;

import java.util.ArrayList;
import java.util.Random;

import entities.Game;
import entities.Tile;

public class Move {
	public static final Move EMPTY = new Move(0);
	
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
	
	public static Move generateRandomValidMove(Game game)
	{
		Random rand = new Random();
		
		// La liste des placements possibles avec les tiles correspondants
		ArrayList<Move> validMovesWithTiles = new ArrayList<Move>();

		// La liste des tiles représentant les rotations et flips de tile
		ArrayList<Tile> tileVariations = new ArrayList<Tile>();

		// Les placements actuels possibles
		ArrayList<Vector2> validMoves = game.getBoard().getFreePositions(game.getCurrentColor());

		// La liste des pièces possibles avec leur position possible
		for (Vector2 position : validMoves) {
			// Pour chaque pièce du joueur
			for (Tile tile : game.getCurrentPlayer().getTileInventory()) {
				if (tile.getCouleur() == game.getCurrentColor()) {
					// La liste des rotations et flips de la pièce
					tileVariations = tile.getTilesListOfRotationsAndFlips();

					// Pour chaque rotation et flip possible de la pièce
					for (Tile t : tileVariations) {
						for (Vector2 tileOrigin : t.getExtremities()) {
							if (game.getBoard().isValidMove(t, tileOrigin, position)) {
								validMovesWithTiles.add(new Move(position, t, tileOrigin));
							}
						}
					}
				}
			}
		}

		if (validMovesWithTiles.size() > 0) {
			int index = rand.nextInt(validMovesWithTiles.size());
			return validMovesWithTiles.get(index);
		}
		
		return Move.EMPTY;
	}
}
