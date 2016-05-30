package entities;

import java.util.List;
import java.util.Random;

import utilities.Move;

public class PlayerRandom extends Player {

	private Random rand;
	private Game game;

	public PlayerRandom(String name, List<CellColor> colors) {
		super(name, colors);
		this.rand = new Random();
		this.game = null;
	}

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
