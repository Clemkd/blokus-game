package entities;

import java.util.List;
import java.util.Random;

import utilities.Move;

/*
 * 0- Toujours utiliser les pièces les plus grosses quand c'est possible
 * 1- Aller vers le centre
 * 2- Cut les coups orientés vers ma zone
 * 3- Aller vers zones voisines
 * 4- ?
 * 
 * Monte Carlo pour determiner si plateau interessant
 * Plutot que d'explo tout les plateaux on en genere quelques un au pif et on en prend un selon pondération par score estimé
 * 
 * Estimer complexité alphabeta/montecarlo
 */

public class PlayerIA extends Player {

	private static final long serialVersionUID = -4411683191893021855L;
	
	private static final int MAX_DEPTH = 4;
	protected Random	rand;

	public PlayerIA(String name, List<CellColor> colors) {
		super(name, colors);
		this.rand = new Random();
	}

	public PlayerIA(PlayerIA p) {
		super(p);
		this.rand = new Random();
	}

	@Override
	public void play(Game game, CellColor c) {
		this.playing = true;
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				chosenMove = alphaBeta(game, MAX_DEPTH, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
				playing = false;
			}
		});
		t.start();
	}
	
	private Move alphaBeta(Game node, int depth, boolean maximizingPlayer, int alpha, int beta) {
	    if (depth == 0 || node.isTerminated())
	        return new Move(this.evaluate(node));

		if (maximizingPlayer) {
			Move bestMove = new Move(Integer.MIN_VALUE);
			for (Move m : Move.possibleMoves(node)) {
				node.doMove(m);
				m.setValue(alphaBeta(node, depth-1, false, alpha, beta).getValue());
				if(m.getValue()>bestMove.getValue()) {
					bestMove = m;
				}
				alpha = Math.max(alpha, m.getValue());
				if (beta <= alpha)
					break;
				node.undoMove();
			}
			return bestMove;
		}
		else {
			Move bestMove = new Move(Integer.MAX_VALUE);
			for (Move m : Move.possibleMoves(node)) {
				node.doMove(m);
				m.setValue(alphaBeta(node, depth-1, true, alpha, beta).getValue());
				if(m.getValue()<bestMove.getValue()) {
					bestMove = m;
				}
				beta = Math.min(beta, m.getValue());
				if (beta <= alpha)
					break;
				node.undoMove();
			}
			return bestMove;
		}
	}

	private int evaluate(Game node) {
		int res = node.getScore(this); // Score qu'on peut esperer obtenir si la partie se terminait maintenant
		/* TODO
		 * Critères suppélmentaires 
		 * - Passer à travers 
		 * - Bloquer adversaire 
		 * - Surface disponible 
		 * - Mieux = traverser ou bloquer ? => Paramètres IA, pondération
		 * - Aller vers le centre
		 * - Fonction d'évaluation de minimax à améliorer
		 * - Phases de jeu: début, milieu, fin
		 * - Ameliorer fonction coups possibles, moins de mises à jour, sauvegarde de ce qui existe
		 * Compter blocs restants à l'ennemi ?
		 */
		res -= this.distanceToCenter(node);
		
		return res;
	}

	private int distanceToCenter(Game node) {
		//TODOOOO, TODO, TODO, TODOOOOO, TODOOO, TODOOOO...
		return 0;
	}

	@Override
	public Player copy() {
		return new PlayerIA(this);
	}
}
