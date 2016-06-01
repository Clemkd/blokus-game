package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utilities.MCNode;
import utilities.Move;

public class PlayerMedium extends Player{
	private static final long serialVersionUID = -8387538910197440018L;

	private Game game;
	private Random rand;

	public PlayerMedium(String name, List<CellColor> colors) 
	{
		super(name, colors);
		this.game = null;
		this.rand = new Random();
	}

	public PlayerMedium(PlayerMedium p) {
		super(p);
		this.game = null;
		this.rand = new Random();
	}

	@Override
	public void play(Game g, CellColor c) {
		this.playing = true;
		this.game = g.copy();

		new Thread(new Runnable()
		{
			@Override
			public void run() 
			{

				ArrayList<Move> moves = Move.possibleMovesWithHeurisitic(game, 5);
				chosenMove = moves.isEmpty() ? Move.EMPTY : moves.get(rand.nextInt(moves.size()));
				
				game = null;
				playing = false;
			}
	
		}).start();
		
	}
	
	@Override
	public Player copy() {
		return new PlayerMedium(this);
	}
}
