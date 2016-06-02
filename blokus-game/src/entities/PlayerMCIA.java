package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utilities.MCNode;
import utilities.Move;

public class PlayerMCIA extends Player{
	private static final long serialVersionUID = -8387538910197440018L;

	private static final int DEFAULT_SAMPLE_SIZE = 2000;
	private Game game;
	private Random rand;
	private int piecesPlacees;

	public PlayerMCIA(String name, List<CellColor> colors) 
	{
		super(name, colors);
		this.game = null;
		this.rand = new Random();
		this.piecesPlacees = 0;
	}

	public PlayerMCIA(PlayerMCIA p) {
		super(p);
		this.game = null;
		this.rand = new Random();
		this.piecesPlacees = 0;
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
				/*if(piecesPlacees < 16)
				{
					ArrayList<Move> moves = Move.possibleMovesWithHeurisitic(game, 5);
					chosenMove = moves.isEmpty() ? Move.EMPTY : moves.get(rand.nextInt(moves.size()));
					piecesPlacees++;
				}
				else*/
				{
					System.err.println("------- MONTECARLO ---------\n" + game.getCurrentPlayer().getName());
					chosenMove = monteCarlo(game, DEFAULT_SAMPLE_SIZE + piecesPlacees * 20);
					System.out.println("PIECE PLACEES : " + ++piecesPlacees);
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
	private Move monteCarlo(Game game, long miliseconds) 
	{
		long ms = System.currentTimeMillis() + miliseconds;
		
		MCNode rootNode = new MCNode(null, null, game);

		while(ms > System.currentTimeMillis()) 
		{
			MCNode node = rootNode;
			Game state = node.getGame().copy();

			// STEP 1 : SELECTION
			MCNode selectedNode = this.selection(node, state);

			
			// STEP 2 : EXPAND
			/*MCNode expandedNode = */this.expand(selectedNode, state);

			rootNode.print();
			// STEP 3 : SIMULATION
			//boolean gameResult  = this.simulateGameRandomlyAndRevert(expandedNode.getGame());
			
			// STEP 4 : BACKPROPAGATE
			//this.backpropagate(expandedNode, gameResult);
		}
		
		return rootNode.getMostVisitedMove();
	}
	
	private MCNode selection(MCNode node, Game state)
	{
		while (!node.isLeaf()) {
			node = node.selectChild();
			//state.doMove(Move.selectRandomlyPossibleMoveWithHeuristic(node, state, rand, 12));
		}
		
		return node;
	}
	
	private /*MCNode*/void expand(MCNode node, Game game)
	{
		
		for(int i = 0; i < 3; i++){
			//**************************************************
			Move mv = Move.selectRandomlyPossibleMoveWithHeuristic(node, game, rand, 12);
			Game g = game.copy();
			g.doMove(mv);
			MCNode fNode = new MCNode(node, mv, g);
			
			node.addChild(fNode);
			
			// STEP 3 : SIMULATION
			boolean gameResult = this.simulateGameRandomlyAndRevert(fNode.getGame());
			
			// STEP 4 : BACKPROPAGATE
			this.backpropagate(fNode, gameResult);
			//**************************************************
		}
		// EXPAND
		/*Move move = Move.selectRandomlyPossibleMoveWithHeuristic(node, game, rand, 12);
		
		game.doMove(move);
		MCNode nodeL = new MCNode(node, move, game);
		node = node.addChild(nodeL);
		
		return node;*/
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
	private boolean simulateGameRandomlyAndRevert(Game game) {
		int count = 0;
		while (!game.isTerminated()) {
			count++;
			ArrayList<Move> moves = Move.possibleMovesWithHeurisitic(game, 12);
			Move move = Move.EMPTY;
			if(moves.size() > 1)
				move = moves.get(this.rand.nextInt(moves.size() - 1));
			else if(!moves.isEmpty())
				move = moves.get(0);
			game.doMove(move);
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
	
	// BACKPROPAGATE
	private void backpropagate(MCNode node, boolean gameResult)
	{
		while (node != null) {
			node.update(gameResult);
			node = node.getParent();
		}
	}

	@Override
	public Player copy() {
		return new PlayerMCIA(this);
	}
}
