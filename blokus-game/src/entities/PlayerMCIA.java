package entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.Timer;

import utilities.Move;

public class PlayerMCIA extends Player implements ActionListener {
	private static final long serialVersionUID = -8387538910197440018L;

	private static final int DEFAULT_WIDTH_SIZE = 3;
	private static final int DEFAULT_SAMPLE_SIZE = 2;
	private Random rand;
	private Timer timer;
	private Game game;

	public PlayerMCIA(String name, List<CellColor> colors) {
		super(name, colors);
		this.rand = new Random();
		this.timer = null;
		this.game = null;
	}

	public PlayerMCIA(PlayerMCIA p) {
		super((Player) p);
		this.rand = new Random();
		this.timer = null;
		this.game = null;
	}

	@Override
	public void play(Game g, CellColor c) {
		this.playing = true;
		this.game = g;
		this.timer = new Timer(800 + rand.nextInt(400), this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.timer.stop();

		// TODO : Usage de la copie de this.game
		this.chosenMove = this.monteCarlo(this.game.copy(), DEFAULT_SAMPLE_SIZE);

		this.game = null;
		this.playing = false;
	}

	private Move monteCarlo(Game game, int sampleSize) 
	{
		MCResult res = expansion(game, Move.EMPTY, sampleSize);
		return res.getMove();
	}
	
	private MCResult expansion(Game game, Move precMove, int j) {
		MCResult res = new MCResult(precMove);
		
		if (game.isTerminated()) {
			if(game.getWinner().equals(this))
				res.setValue(1);
			else
				res.setValue(0);
			return res;
		}
		if (j == 0) {
			boolean b = simulateGameRandomly(game);
			res.setValue(b ? 1 : 0);
			return res;
		}
		
		for (int i = 0; i < DEFAULT_WIDTH_SIZE; i++) {
			Move mv = Move.generateRandomValidMove(game);
			res.setMove(mv);
			game.doMove(mv);
			
			MCResult mc = expansion(game, precMove, j - 1);
			
			double r = mc.getValue();
			res.setValue(r + res.getValue());
			game.undoSingleMove();
		}

		return res;
	}

	
	/**
	 * Simule un jeu depuis une configuration donnée
	 * 
	 * @param game
	 *            La configuration
	 * @param refPlayer
	 *            Le joueur de référence
	 * @return True si le joueur de référence à gagné le jeu, False dans le cas
	 *         contraire
	 */
	private boolean simulateGameRandomly(Game game) {
		int count = 0;
		while (!game.isTerminated()) {
			count++;
			Move mv = Move.generateRandomValidMove(game);
			game.doMove(mv);
		}
		
		boolean resultGame = game.getWinner().equals(this);
		
		// Revert de la partie
		while (count > 0) {
			count--;
			game.undoSingleMove();
		}

		System.out.println(resultGame);
		return resultGame;
	}

	@Override
	public Player copy() {
		return new PlayerMCIA(this);
	}
}
