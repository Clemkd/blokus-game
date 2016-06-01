package entities;

import java.util.List;

import utilities.Move;

public class PlayerHuman extends Player {

	private static final long serialVersionUID = 1783049661851925535L;

	/**
	 * Constructeur d'un joueur humain
	 * 
	 * @param name le nom 
	 * @param colors les couleurs
	 */
	public PlayerHuman(String name, List<CellColor> colors) {
		super(name, colors);
	}
	
	/**
	 * Constructeur d'un joueur humain
	 */
	public PlayerHuman(){
		super();
	}

	/**
	 * Constructeur d'un joueur humain
	 * 
	 * @param p le joueur
	 */
	public PlayerHuman(PlayerHuman p) {
		super(p);
	}

	@Override
	public void play(Game game, CellColor c) {
		this.chosenMove = null;
		this.playing = true;
	}
	
	public void setChosenMove(Move m) {
		super.setChosenMove(m);
		this.playing = false;
	}

	@Override
	public Player copy() {
		return new PlayerHuman(this);
	}

	@Override
	public String toString() {
		return "PlayerHuman [colors=" + colors + ", name=" + name + ", tiles=" + tiles.toString() + "]";
	}
	
	
}
