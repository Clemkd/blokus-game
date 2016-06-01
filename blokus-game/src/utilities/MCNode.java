package utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import entities.Game;
import entities.Player;

public class MCNode {
	private final static float UCTK = 0.7f;
	
	private Move move;
	private MCNode parent;
	private int winsCount;
	private int visitsCount;
	private Player player;
	private Game game;
	private List<MCNode> childs;

	public MCNode(MCNode parent, Move move, Game game) {
		this.parent = parent;
		this.move = move;
		this.game = game;
		this.player = game.getCurrentPlayer();
		this.childs = new ArrayList<MCNode>();
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public MCNode getParent() {
		return parent;
	}

	public void setParent(MCNode parent) {
		this.parent = parent;
	}

	public int getWinsCount() {
		return winsCount;
	}

	public void setWinsCount(int winsCount) {
		this.winsCount = winsCount;
	}

	public int getVisitsCount() {
		return visitsCount;
	}

	public void setVisitsCount(int visitsCount) {
		this.visitsCount = visitsCount;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<MCNode> getChilds() {
		return childs;
	}

	public void setChilds(List<MCNode> childs) {
		this.childs = childs;
	}

	/**
	 * Determine si le noeud est une feuille
	 * @return True si le noeud est une feuille, False dans le cas contraire
	 */
	public boolean isLeaf() {
		return this.childs.size() == 0;
	}

	/**
	 * Selection et retourne le meilleur noeud fils selon l'heuristiue d'exploration (UCT avec UCB1)
	 * @return Le meilleur sous-noeud
	 */
	public MCNode selectChild() {
		this.getChilds().sort(new Comparator<MCNode>() {

			@Override
			public int compare(MCNode node1, MCNode node2) {
				return Double.compare(node1.reckonUCT(), node2.reckonUCT());
			}
		});
		return this.getChilds().get(0);
	}

	/**
	 * Calcul avec la formule UCB1 permettant la variation du nombre d'exploration des sous-noeuds
	 * @return Le resultat UCB1 
	 */
	public double reckonUCT()
	{
		return (this.getWinsCount() / this.getVisitsCount()) +
				(MCNode.UCTK * Math.sqrt(2 * Math.log(this.getVisitsCount()) / this.getVisitsCount()));
	}

	/**
	 * Ajoute un nouveau noeud fils
	 * @param node Le noeud à ajouter 
	 * @return Le noeud ajouté
	 */
	public MCNode addChild(MCNode node) {
		this.childs.add(node);
		return node;
	}

	/**
	 * Met à jour le node avec le résultat de partie spécifié
	 * @param gameResult Le résultat de la partie référente au node
	 */
	public void update(boolean gameResult) {
		this.visitsCount++;
		if(gameResult)
		{
			this.winsCount++;
		}
	}

	/**
	 * Retroune le placement le plus visité et possédant le plus de parties gagnées (notion de visite par MCNode)
	 * @return Le meilleur placement selon l'UCT
	 */
	public Move getMostVisitedMove() 
	{
		// Tri sur le nombre de visites du noeud
		this.getChilds().sort(new Comparator<MCNode>() {
			@Override
			public int compare(MCNode node1, MCNode node2) {
				return Integer.compare(node1.getVisitsCount(), node2.getVisitsCount());
			}
		});
		
		// Le noeud le plus visité
		MCNode firstMostVisited = this.getChilds().get(0);
		
		// Afin d'éviter de prendre le premier noeud le plus visité,
		// si il existe un noeud possédant un grand nombre de parties gagnées et un grand nombre de visite
		if(firstMostVisited.getVisitsCount() > (this.getVisitsCount() / this.getChilds().size()) + 1)
		{
			return firstMostVisited.getMove();
		}
		else
		{
			// Tri sur le nombre de parties gagnées
			this.getChilds().sort(new Comparator<MCNode>() {
				@Override
				public int compare(MCNode node1, MCNode node2) {
					return Integer.compare(node1.getWinsCount(), node2.getWinsCount());
				}
			});
			
			return this.getChilds().get(0).getMove();
		}
	}
}
