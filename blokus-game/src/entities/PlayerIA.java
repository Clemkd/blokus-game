package entities;

import java.util.List;

import utilities.Move;
import utilities.OutOfBoundsException;
import utilities.Vector2;

/*
 * 0- Toujours utiliser les pièces les plus grosses quand c'est possible 1- Aller vers le centre 2- Cut les coups
 * orientés vers ma zone 3- Aller vers zones voisines 4- ?
 * 
 * Monte Carlo pour determiner si plateau interessant Plutot que d'explo tout les plateaux on en genere quelques un au
 * pif et on en prend un selon pondération par score estimé
 * 
 * Estimer complexité alphabeta/montecarlo
 */

public class PlayerIA extends Player {

	private static final long	serialVersionUID	= -4411683191893021855L;

	private static final int	MAX_DEPTH			= 1;
	private int					k;

	public PlayerIA(String name, List<CellColor> colors) {
		super(name, colors);
		k = 0;
	}

	public PlayerIA(PlayerIA p) {
		super(p);
		k = 0;
	}

	@Override
	public void play(Game game, CellColor c) {
		this.playing = true;
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				k = 0;
				chosenMove = alphaBeta(game.copy(), MAX_DEPTH, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
				System.out.println("Calls: " + k);
				System.out.println("CHOSEN MOVE: " + chosenMove.getValue());

				playing = false;
			}
		});
		t.start();
	}

	private Move alphaBeta(Game node, int depth, boolean maximizingPlayer, int alpha, int beta) {
		k++;
		if (k % 500 == 0)
			System.out.println("Calls: " + k);
		if (depth == 0 || node.isTerminated()) {
			return new Move(this.evaluate(node));
		}
		int l = 0;

		if (maximizingPlayer) {
			Move bestMove = new Move(Integer.MIN_VALUE);
			for (Move m : possibleMovesRestricted(node, 5)) {
				l++;
				node.doMove(m);
				m.setValue(alphaBeta(node, depth - 1, false, alpha, beta).getValue());
				if (m.getValue() > bestMove.getValue()) {
					bestMove = m;
				}
				node.undoSingleMove();

				alpha = Math.max(alpha, m.getValue());
				if (beta <= alpha)
					break;
			}
			return bestMove;
		}
		else {
			Move bestMove = new Move(Integer.MAX_VALUE);
			for (Move m : possibleMovesRestricted(node, 5)) {
				l++;
				node.doMove(m);
				m.setValue(alphaBeta(node, depth - 1, true, alpha, beta).getValue());
				if (m.getValue() < bestMove.getValue()) {
					bestMove = m;
				}
				node.undoSingleMove();

				beta = Math.min(beta, m.getValue());
				if (beta <= alpha)
					break;
			}
			return bestMove;
		}
	}

	private List<Move> possibleMovesRestricted(Game node, int minTileSize) {
		List<Move> fullList = Move.possibleMoves(node);

		int endIndex = fullList.size();
		for (int i = 0; i < fullList.size(); i++) {
			if (fullList.get(i).getTile().getCellCount() < minTileSize) {
				endIndex = i;
				break;
			}
		}

		return fullList.subList(0, endIndex);
	}

	private int evaluate(Game node) {
		int res = 2 * node.getScore(node.getCurrentPlayer());

//		if (node.getTurn() > 24)
//			res -= node.getCurrentPlayer().getTileInventory().size() * 10;
//		else {
			double d = distanceToCenter(node);
			res -= 1000 * Math.round(d);
//		}

		/*for (Tile t : node.getCurrentPlayer().getTileInventory()) {
			if (t.getColor() == node.getCurrentColor())
				res -= t.getCellCount() * t.getCellCount();
		}*/

		/*
		 * TODO Critères - Passer à travers - Bloquer adversaire - Surface disponible - Mieux = traverser ou bloquer ?
		 * => Paramètres IA, pondération - Aller vers le centre - Fonction d'évaluation de minimax à améliorer - Phases
		 * de jeu: début, milieu, fin - Ameliorer fonction coups possibles, moins de mises à jour, sauvegarde de ce qui
		 * existe Compter blocs restants à l'ennemi ?
		 */

		return res;
	}

	private double distanceToCenter(Game node) {
		final Vector2 center = new Vector2(Board.WIDTH / 2, Board.HEIGHT / 2);
		double d = Double.MAX_VALUE;
		double res;
		Vector2 temp;

		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				temp = new Vector2(i,j);
				try {
					if (node.getBoard().getCell(temp) != null
							&& node.getBoard().getCell(temp).equals(node.getCurrentColor())) {
						res = Move.euclideanDistance(temp, center);
						if (res < d) {
							d = res;
						}
					}
				}
				catch (OutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		}
		return d;
	}

	@Override
	public Player copy() {
		return new PlayerIA(this);
	}
}
