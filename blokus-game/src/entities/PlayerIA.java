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
	
	private static final int MAX_DEPTH = 5;
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
				chosenMove = alphaBeta(game.copy(), MAX_DEPTH, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
				System.out.println("CHOSEN MOVE: " + chosenMove.getValue());
				playing = false;
			}
		});
		t.start();
	}
	
	int k = 0;
	int l =0;
	private Move alphaBeta(Game node, int depth, boolean maximizingPlayer, int alpha, int beta) {
	    if (depth == 0 || node.isTerminated()) {
	        return new Move(this.evaluate(node));
	    }
	    
		if (maximizingPlayer) {
			Move bestMove = new Move(Integer.MIN_VALUE);
			for (Move m : Move.possibleMovesWithHeurisitic(node, 12)) {
				node.doMove(m);
				m.setValue(this.alphaBeta(node, depth-1, false, alpha, beta).getValue());
				if(m.getValue()>bestMove.getValue()) {
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
			for (Move m : Move.possibleMovesWithHeurisitic(node, 12)) {
				node.doMove(m);
				m.setValue(alphaBeta(node, depth-1, true, alpha, beta).getValue());
				if(m.getValue()<bestMove.getValue()) {
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

	private int evaluate(Game node) {
		int res = 2*node.getScore(node.getCurrentPlayer()); 
		
		if(node.getTurn()>20)
			res -= node.getCurrentPlayer().getTileInventory().size()*10;
		
		for(Tile t : node.getCurrentPlayer().getTileInventory()) {
			if(t.getColor()==node.getCurrentColor())
				res -= t.getCellCount()*t.getCellCount();
		}
		
		/* TODO
		 * Critères
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
		
		return res;
	}

	@Override
	public Player copy() {
		return new PlayerIA(this);
	}
}
