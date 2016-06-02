package utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

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
		if(this.getChilds().size() == 0)
		{
			System.err.println("NO CHILD [MCNode]");
			return this;
		}
		this.getChilds().sort(new Comparator<MCNode>() {

			@Override
			public int compare(MCNode node1, MCNode node2) {
				return Double.compare(node1.reckonUCT(), node2.reckonUCT());
			}
		});
		
		ArrayList<MCNode> childNodes = new ArrayList<MCNode>();
		MCNode node = this.getChilds().get(this.getChilds().size() - 1);
		
		int i = 0;
		while(i < this.getChilds().size() && this.getChilds().get(i).reckonUCT() == node.reckonUCT())
		{
			childNodes.add(this.getChilds().get(i));
			i++;
		}
		System.err.println("NOMBRE EQUIVALENTS : " + i);
		Random rand = new Random();
		if(childNodes.size() < 2)
			return node;

		return childNodes.get(rand.nextInt(childNodes.size() - 1));
	}

	public double reckonValue()
	{
		return this.getVisitsCount() / this.getVisitsCount();
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
		if(this.getChilds().size() == 0)
		{
			System.err.println("NO CHILD [MCNode]");
			return this.getMove();
		}
		// Tri sur le nombre de visites du noeud
		this.getChilds().sort(new Comparator<MCNode>() {
			@Override
			public int compare(MCNode node1, MCNode node2) {
				return Integer.compare(node1.getVisitsCount(), node2.getVisitsCount());
			}
		});
		
		// Le noeud le plus visité
		MCNode firstMostVisited = this.getChilds().get(this.getChilds().size() - 1);
		
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
			
			return this.getChilds().get(this.getChilds().size() - 1).getMove();
		}
	}
	
	
	// ************************************************************************************************
	
	public String toString()
	{
		return "["+this.winsCount+"/"+this.visitsCount+"] " + this.player.getName();
	}
	
    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + this.toString());
        for (int i = 0; i < this.childs.size() - 1; i++) {
            this.getChilds().get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (this.getChilds().size() > 0) {
            this.getChilds().get(this.getChilds().size() - 1).print(prefix + (isTail ?"    " : "│   "), true);
        }
    }
}
