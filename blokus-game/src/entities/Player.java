package entities;

import java.util.ArrayList;
import java.util.List;

import utilities.Move;

public abstract class Player {
	/**
	 * Les couleurs du joueur
	 */
	protected List<CellColor>	colors;
	protected String			name;

	/**
	 * Les tuiles du joueur
	 */
	protected List<Tile>		tiles;
	protected boolean			playing;
	protected Move				chosenMove;
	private boolean				singleCellLast;

	public Player(String name, List<CellColor> colors) {
		this.playing = false;
		this.chosenMove = null;
		this.colors = colors;
		this.name = name;
		this.singleCellLast = false;
		this.tiles = new ArrayList<Tile>();
		for (CellColor c : colors)
			this.tiles.addAll(Tile.getListOfNeutralTile(c));
	}

	public Player() {
		this.playing = false;
		this.chosenMove = null;
		this.colors = new ArrayList<CellColor>();
		this.name = "Erreur";
		this.singleCellLast = false;
		this.tiles = new ArrayList<Tile>();
	}

	public Player(Player p) {
		this.name = p.name;
		this.playing = p.isPlaying();
		this.chosenMove = p.getChosenMove();
		this.singleCellLast = p.lastTileWasSingleCell();
		this.colors = new ArrayList<CellColor>();
		this.tiles = new ArrayList<Tile>();
		
		for(CellColor c : p.getColors()) {
			this.colors.add(c);
		}
		
		for(Tile t : p.getTileInventory()) {
			this.tiles.add(t);
		}
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
		if(this.chosenMove!=null) {
			if(this.chosenMove.getTile()!=null && this.chosenMove.getTile().getId()==Tile.SINGLE_CELL_ID)
				this.singleCellLast = true;
			else
				this.singleCellLast = false;
			this.removeTileFromInventory(this.chosenMove.getTile());
		}
		Move m = this.chosenMove;

		this.chosenMove = null;

		return m;
	}

	/**
	 * Transmet le nom du joueur
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	private Move getChosenMove() {
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

	/**
	 * Enlève le tile transmis de l'inventaire, renvoi vrai si un tile a bien été supprimé
	 * 
	 * @param tile
	 *            Tile à retirer de l'inventaire
	 * @return True si le tile recherché était présent et a été retiré, false sinon
	 */
	public boolean removeTileFromInventory(Tile tile) {
		if (tile != null) {
			// Tile bin, lol 
			Tile bin = null;

			int id = tile.getId();
			CellColor color = tile.getColor();
			for (Tile t : this.tiles) {
				if (t.getId() == id && t.getColor() == color) {
					bin = t;
					break;
				}
			}

			if (bin != null) {
				this.tiles.remove(bin);
				return true;
			}
		}

		return false;
	}

	/**
	 * Rajoute le tile fourni à l'inventaire, renvoi vrai si un tile a bien été ajouté
	 * 
	 * @param tile
	 *            Tile à ajouter dans l'inventaire
	 * @return True si le tile fourni a bien été ajouté, false sinon
	 */
	public boolean addTileToInventory(Tile tile) {
		if (tile != null) {
			int id = tile.getId();
			CellColor color = tile.getColor();
			for (Tile t : Tile.getListOfNeutralTile(color)) {
				if (t.getId() == id) {
					this.tiles.add(t);
					return true;
				}
			}
		}

		return false;
	}

	public boolean lastTileWasSingleCell() {
		return this.singleCellLast;
	}
	
	public abstract Player copy();
}
