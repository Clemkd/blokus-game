package entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;
import utilities.MCNode;
import utilities.Move;

public class PlayerMCIA extends Player implements ActionListener {
	private static final long serialVersionUID = -8387538910197440018L;

	private static final int DEFAULT_SAMPLE_SIZE = 100;
	private Timer timer;
	private Game game;

	public PlayerMCIA(String name, List<CellColor> colors) {
		super(name, colors);
		this.timer = null;
		this.game = null;
	}

	public PlayerMCIA(PlayerMCIA p) {
		super((Player) p);
		this.timer = null;
		this.game = null;
	}

	@Override
	public void play(Game g, CellColor c) {
		this.playing = true;
		this.game = g;
		this.timer = new Timer(0, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.timer.stop();

		this.chosenMove = this.monteCarlo(game, DEFAULT_SAMPLE_SIZE);
		
		this.game = null;
		this.playing = false;
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
