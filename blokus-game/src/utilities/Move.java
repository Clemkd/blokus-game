package utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import entities.Board;
import entities.Game;
import entities.Tile;

public class Move implements Serializable, Comparable<Move> {

	private static final long	serialVersionUID	= 711684268057009670L;

	public static final Move	EMPTY				= new Move(0);

	private final Vector2		position;
	private final Tile			tile;
	private int					value;
	private Vector2				tileOrigin;

	/**
	 * Crée un coup nul avec la valeur indiquée
	 * 
	 * @param value
	 *            Valeur du coup
	 */
	public Move(int value) {
		this.value = value;
		this.tile = null;
		this.position = null;
		this.tileOrigin = null;
	}

	/**
	 * Crée un coup avec les valeurs indiquées
	 * 
	 * @param position
	 *            Future position de la cellule d'origine du Tile sur le plateau
	 * @param tile
	 *            Tile utilisé
	 * @param tileOrigin
	 *            Position de la cellule utilisée dans le Tile
	 * @param value
	 *            Valeur du coup
	 */
	public Move(Vector2 position, Tile tile, Vector2 tileOrigin, int value) {
		this.value = value;
		this.tile = tile;
		this.position = position;
		this.tileOrigin = tileOrigin;
	}

	/**
	 * Crée un coup avec les valeurs indiquées
	 * 
	 * @param position
	 *            Future position de la cellule d'origine du Tile sur le plateau
	 * @param tile
	 *            Tile utilisé
	 * @param tileOrigin
	 *            Position de la cellule utilisée dans le Tile
	 */
	public Move(Vector2 position, Tile tile, Vector2 tileOrigin) {
		this.value = 0;
		this.tile = tile;
		this.position = position;
		this.tileOrigin = tileOrigin;
	}

	/**
	 * Transmet la future position de la cellule d'origine du Tile sur le plateau
	 * 
	 * @return Position
	 */
	public Vector2 getPosition() {
		return this.position;
	}

	/**
	 * Transmet le Tile joué
	 * 
	 * @return Tile
	 */
	public Tile getTile() {
		return this.tile;
	}

	/**
	 * Change la valeur du coup
	 * 
	 * @param value
	 *            Valeur du coup
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Transmet la valeur du coup
	 * 
	 * @return Valeur du coup
	 */
	public int getValue() {
		return this.value;
	}

	public Vector2 getTileOrigin() {
		return this.tileOrigin;
	}

	public static Move generateRandomValidMove(Game game) {
		return generateRandomValidMove(game, new Random());
	}

	public static double euclideanDistance(Vector2 v1, Vector2 v2) {
		return Math.sqrt(Math.pow(v2.getX() - v1.getX(), 2) + Math.pow(v2.getY() - v1.getY(), 2));
	}

	public static Move selectRandomlyPossibleMoveWithHeuristic(MCNode node, Game game, Random rand, int max) {
		ArrayList<Move> moves = Move.possibleMovesWithHeurisitic(game, max);

		Move move = Move.EMPTY;
		if (moves.size() > 1)
			move = moves.get(rand.nextInt(moves.size() - 1));
		else if (!moves.isEmpty()) {
			move = moves.get(0);
		}

		return move;
	}

	public static ArrayList<Move> possibleMovesWithHeurisitic(Game game, int max) {
		final Vector2 center = new Vector2(Board.WIDTH / 2, Board.HEIGHT / 2);
		ArrayList<Move> listMove = new ArrayList<Move>();

		// Les placements actuels possibles
		ArrayList<Vector2> validMoves = game.getBoard().getFreePositions(game.getCurrentColor());

		List<Tile> listTile = game.getCurrentPlayer().getTileInventory();
		listTile.sort(new Comparator<Tile>() {

			@Override
			public int compare(Tile tile1, Tile tile2) {
				return Integer.compare(tile2.getCellCount(), tile1.getCellCount());
			}
		});

		//Pour toutes les tuiles de l'inventaire
		int i = 0;
		while (i < listTile.size() && listMove.size() < max) {
			if (listTile.get(i).getColor() == game.getCurrentColor()) {
				//pour toutes les rotations et tous les flips possibles
				for (Tile t : listTile.get(i).getTilesListOfRotationsAndFlips()) {
					//Si la tile est possible à placer
					for (Vector2 tileOrigin : t.getExtremities()) {
						for (Vector2 position : validMoves) {
							if (game.getBoard().isValidMove(t, tileOrigin, position)) {
								listMove.add(new Move(position, t, tileOrigin));
							}
						}
					}
				}
			}
			i++;
		}

		ArrayList<Move> movesResult = new ArrayList<Move>();
		double minEuclideanDistance = Double.POSITIVE_INFINITY;
		for (Move m : listMove) {
			for (Vector2 v : m.getTile().getExtremities()) {
				Vector2 position = new Vector2();
				position.setX(m.getPosition().getX() + (v.getY() - m.getTileOrigin().getY()));
				position.setY(m.getPosition().getY() + (v.getX() - m.getTileOrigin().getX()));

				double res = euclideanDistance(position, center);
				if (res == minEuclideanDistance) {
					movesResult.add(m);
					minEuclideanDistance = res;
				}
				else if (res < minEuclideanDistance) {
					movesResult.clear();
					movesResult.add(m);
					minEuclideanDistance = res;
				}
			}
		}

		return movesResult;
	}

	public static List<Move> possibleMoves(Game game) {
		// La liste des placements possibles avec les tiles correspondants
		List<Move> validMovesWithTiles = new ArrayList<Move>();

		// La liste des tiles représentant les rotations et flips de tile
		ArrayList<Tile> tileVariations = new ArrayList<Tile>();

		// Les placements actuels possibles
		ArrayList<Vector2> validMoves = game.getBoard().getFreePositions(game.getCurrentColor());

		// La liste des pièces possibles avec leur position possible
		for (Vector2 position : validMoves) {
			// Pour chaque pièce du joueur
			for (Tile tile : game.getCurrentPlayer().getTileInventory()) {
				if (tile.getColor() == game.getCurrentColor()) {
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
		Collections.sort(validMovesWithTiles);
		return validMovesWithTiles;
	}

	public static Move generateRandomValidMove(Game game, Random rand) {
		// La liste des placements possibles avec les tiles correspondants
		List<Move> validMovesWithTiles = possibleMoves(game);

		if (validMovesWithTiles.size() > 0) {
			int index = rand.nextInt(validMovesWithTiles.size());
			return validMovesWithTiles.get(index);
		}

		return Move.EMPTY;
	}

	@Override
	public int compareTo(Move o) {
		if (this.getTile().getCellCount() < o.getTile().getCellCount()) {
			return 1;
		}
		else if (this.getTile().getCellCount() > o.getTile().getCellCount()){
			return -1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.position == null) ? 0 : this.position.hashCode());
		result = prime * result + ((this.tile == null) ? 0 : this.tile.hashCode());
		result = prime * result + ((this.tileOrigin == null) ? 0 : this.tileOrigin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (this.position == null) {
			if (other.position != null)
				return false;
		}
		else if (!this.position.equals(other.position))
			return false;
		if (this.tile == null) {
			if (other.tile != null)
				return false;
		}
		else if (!this.tile.equals(other.tile))
			return false;
		if (this.tileOrigin == null) {
			if (other.tileOrigin != null)
				return false;
		}
		else if (!this.tileOrigin.equals(other.tileOrigin))
			return false;
		return true;
	}
}
