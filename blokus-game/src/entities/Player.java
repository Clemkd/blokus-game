package entities;

import java.util.ArrayList;
import java.util.List;

import utilities.Move;

public abstract class Player {
	/**
	 * Les couleurs du joueur
	 */
	protected List<CellColor>	colors;
	protected String name;

	/**
	 * Les tuiles du joueur
	 */
	protected List<Tile>		tiles;
	protected boolean			playing;
	protected Move				chosenMove;

	public Player(String name, List<CellColor> colors) {
		this.playing = false;
		this.chosenMove = null;
		this.colors = colors;
		this.name = name;
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
	public abstract void play(Game game, CellColor c);

	/**
	 * Permet de vérifier si un joueur est en train de jouer.
	 * 
	 * @return Etat
	 */
	public boolean isPlaying() {
		return this.playing;
	}

	/**
	 * Récupère le coup choisi par le joueur
	 * 
	 * @return Coup choisi
	 */
	public Move getMove() {
		Move m = this.chosenMove;
		this.chosenMove = null;

		return m;
	}
	
	/**
	 * Transmet le nom du joueur
	 * @return
	 */
	public String getName() {
		return this.name;
	}
}
