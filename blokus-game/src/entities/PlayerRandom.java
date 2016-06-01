package entities;

import java.util.List;
import java.util.Random;

import utilities.Move;

public class PlayerRandom extends Player {

	private static final long serialVersionUID = -8981196791159515517L;
	
	/**
	 * Le random
	 */
	private Random rand;
	
	/**
	 * La partie
	 */
	private Game game;

	/**
	 * Constructeur d'un joueur random
	 * 
	 * @param name le nom
	 * @param colors les couleurs
	 */
	public PlayerRandom(String name, List<CellColor> colors) {
		super(name, colors);
		this.rand = new Random();
		this.game = null;
	}

	/**
	 * Constructeur d'un joueur random
	 * 
	 * @param p un joueur
	 */
	public PlayerRandom(PlayerRandom p) {
		super(p);
		this.rand = new Random();
		this.game = null;
	}

	@Override
	public void play(Game g, CellColor c) {
		this.playing = true;
		this.game = g;
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				chosenMove = Move.generateRandomValidMove(game, rand);

				game = null;
				playing = false;
			}
		});
		t.start();
	}

	@Override
	public Player copy() {
		return new PlayerRandom(this);
	}
}
