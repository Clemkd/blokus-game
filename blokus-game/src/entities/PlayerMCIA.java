package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utilities.MCNode;
import utilities.Move;

public class PlayerMCIA extends Player{
	private static final long serialVersionUID = -8387538910197440018L;

	private static final int DEFAULT_SAMPLE_SIZE = 200;
	private Game game;
	private Random rand;
	private int piecePlace;

	public PlayerMCIA(String name, List<CellColor> colors) 
	{
		super(name, colors);
		this.game = null;
		this.rand = new Random();
		this.piecePlace = 0;
	}

	public PlayerMCIA(PlayerMCIA p) {
		super(p);
		this.game = null;
		this.rand = new Random();
		this.piecePlace = 0;
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
				if(piecePlace < 16)
				{
					ArrayList<Move> moves = Move.possibleMovesWithHeurisitic(game, 5);
					chosenMove = moves.isEmpty() ? Move.EMPTY : moves.get(rand.nextInt(moves.size()));
					piecePlace++;
				}
				else
				{
					System.err.println("MONTECARLO");
					chosenMove = monteCarlo(game, DEFAULT_SAMPLE_SIZE);
				}
				
				game = null;
				playing = false;
			}
	
		}).start();
		
	}

	/**
	 * Algorithme Monte Carlo (Arbre de recherche)
	 * @param game La configuration
	 * @param itermax Le nombre d'itérations
	 * @param uctk La valeur 
	 * @return
	 */
	private Move monteCarlo(Game game, int itermax) {
		MCNode rootNode = new MCNode(null, null, game);

		for (int i = 0; i < itermax; i++) {
			MCNode node = rootNode;
			Game gameCopy = game.copy();

			// SELECTION
			while (!node.isLeaf()) {
				node = node.selectChild();
				gameCopy.doMove(node.getMove());
			}

			// EXPAND
			Move move = Move.generateRandomValidMove(gameCopy);
			gameCopy.doMove(move);
			MCNode nodeL = new MCNode(node, move, gameCopy);
			node = node.addChild(nodeL);

			// SIMULATION
			boolean gameResult = this.simulateGameRandomlyWhithoutRevert(gameCopy);
			
			// BACKPROPAGATE
			while (node != null) {
				node.update(gameResult);
				node = node.getParent();
			}
		}

		return rootNode.getMostVisitedMove();
	}

	/**
	 * Simule un jeu depuis une configuration donnée et remet le plateau à la
	 * configuration donnée
	 * 
	 * @param game
	 *            La configuration
	 * @param refPlayer
	 *            Le joueur de référence
	 * @return True si le joueur de référence à gagné le jeu, False dans le cas
	 *         contraire
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private boolean simulateGameRandomlyAndRevert(Game game) {
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

		return resultGame;
	}

	/**
	 * Simule un jeu depuis une configuration donnée sans remise en état du
	 * plateau
	 * 
	 * @param game
	 *            La configuration
	 * @param refPlayer
	 *            Le joueur de référence
	 * @return True si le joueur de référence à gagné le jeu, False dans le cas
	 *         contraire
	 */
	private boolean simulateGameRandomlyWhithoutRevert(Game game) {
		while (!game.isTerminated()) {
			Move mv = Move.generateRandomValidMove(game);
			game.doMove(mv);
		}

		boolean resultGame = game.getWinner().equals(this);

		return resultGame;
	}

	@Override
	public Player copy() {
		return new PlayerMCIA(this);
	}
}
