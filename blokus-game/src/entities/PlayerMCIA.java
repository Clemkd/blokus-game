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
	private static final int DEFAULT_SAMPLE_SIZE = 5;
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
		this.chosenMove = this.monteCarlo(this.game.copy(), this, DEFAULT_SAMPLE_SIZE);

		this.game = null;
		this.playing = false;
	}

	// TODO : Reste à fix
	/*
	 * private Move monteCarlo(Game g, int sampleSize) { Move bestMove =
	 * Move.EMPTY; Stack<Move> moves = new Stack<Move>(); Player firstPlayer =
	 * g.getCurrentPlayer();
	 * 
	 * for(int i = 0; i < sampleSize; i++) { while(!g.isTerminated()) { Move
	 * move = Move.generateRandomValidMove(g); g.doMove(move); moves.push(move);
	 * }
	 * 
	 * int value = g.getScore(firstPlayer); Move m = null;
	 * while(!moves.isEmpty()) { m = moves.pop(); g.undoMove(); }
	 * 
	 * if(m == null) { throw new InternalError(
	 * "Premier placement inexistant : Problème algorithme Monte Carlo"); }
	 * 
	 * m.setValue(value);
	 * 
	 * if(bestMove == null || bestMove.getValue() < m.getValue()) { bestMove =
	 * m; } }
	 * 
	 * return bestMove; }
	 */

	private Move monteCarlo(Game game, Player refPlayer, int sampleSize) 
	{
		MCResult res = expansion(game, Move.EMPTY, refPlayer, sampleSize);
		return res.getMove();
	}

	private MCResult expansion(Game game, Move precMove, Player refPlayer, int j) {
		MCResult res = new MCResult(precMove);
		
		if (game.isTerminated()) {
			System.out.println("IS TERMINATED");
			res.setValue((game.getWinner() == refPlayer) ? 1 : 0);
			return res;
		}
		if (j == 0) {
			boolean b = simulateGameRandomly(game, refPlayer);
			System.out.println("SIMULATE " + b);
			res.setValue(b ? 1 : 0);
			return res;
		}
		
		System.out.println("ETAGE : " + j + " ; VALUE : " + res.getValue());
		for (int i = 0; i < DEFAULT_WIDTH_SIZE; i++) {
			Move mv = Move.generateRandomValidMove(game);
			res.setMove(mv);
			game.doMove(mv);
			System.out.println("MOVE EFFECTUÉ");
			MCResult mc = expansion(game, mv, refPlayer, j - 1);
			System.out.println(mc);
			double r = mc.getValue();
			System.out.println(r);
			res.setValue(r + res.getValue());
			game.undoMove();
		}
		System.out.println("ETAGE : " + j + " ; VALUE : " + res.getValue());
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
	private boolean simulateGameRandomly(Game game, Player refPlayer) {
		int count = 0;
		while (!game.isTerminated()) {
			count++;
			Move mv = Move.generateRandomValidMove(game);
			if(mv.getTile() == null)
			{
				game.surrendCurrentColor();
			}
			else game.doMove(mv);
			System.out.println("GENERATE MOVE");
		}
		System.out.println("GAME FINISHED");
		// Revert de la partie
		while (count > 0) {
			count--;
			game.undoMove();
		}

		return game.getWinner() == refPlayer;
	}

	@Override
	public Player copy() {
		return new PlayerMCIA(this);
	}
}
