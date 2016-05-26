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

	public Player(){
		
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
		
		if(m != null && m.getTile()!=null)
		{
			// Suppression du tile joué de l'inventaire
			Tile tile = null;
			
			int id = m.getTile().getId();
			for(Tile t : this.tiles)
			{
				if(t.getId() == id && t.getCouleur() == m.getTile().getCouleur())
				{
					tile = t;
					break;
				}
			}
			this.tiles.remove(tile);
		}
		
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
	
	public void setName(String name){
		this.name = name;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	public Move getChosenMove() {
		return chosenMove;
	}

	public void setChosenMove(Move chosenMove) {
		this.chosenMove = chosenMove;
	}

	public void setColors(List<CellColor> colors) {
		this.colors = colors;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	
	
}
