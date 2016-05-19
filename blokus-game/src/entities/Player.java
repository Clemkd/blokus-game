package entities;

import java.util.ArrayList;
import java.util.List;

import utilities.Move;

public abstract class Player {
	/**
	 * Les couleurs du joueur
	 */
	protected List<CellColor>	colors;

	/**
	 * Les tuiles du joueur
	 */
	protected List<Tile>		tiles;

	public Player(List<CellColor> colors) {
		this.colors = colors;
		this.tiles = new ArrayList<Tile>();
		for (CellColor c : colors)
			this.tiles.addAll(Tile.getListOfNeutralTile(c));
	}

	/**
	 * Obtient la liste des tuiles du joueur
	 * 
	 * @return La liste des tuiles
	 */
	public List<Tile> getTileInventory() {
		return this.tiles;
	}

	/**
	 * Obtient la liste de couleurs du joueur
	 * 
	 * @return La liste de couleurs
	 */
	public List<CellColor> getColors() {
		return this.colors;
	}

	/**
	 * Indique au joueur que c'est à son tour de jouer
	 * 
	 * @param c
	 *            Couleur à jouer
	 */
	public abstract void play(CellColor c);

	/**
	 * Permet de vérifier si un joueur est en train de jouer.
	 * 
	 * @return Etat
	 */
	public abstract boolean isPlaying();

	/**
	 * Récupère le coup choisi par le joueur
	 * 
	 * @return Coup choisi
	 */
	public abstract Move getMove();
}
