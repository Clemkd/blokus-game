package utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import entities.Game;
import entities.Player;

public class MCNode {
	private final static float UCTK = 1.44f;
	
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

	public boolean isLeaf() {
		return this.childs.size() == 0;
	}

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

	public MCNode addChild(MCNode nodeL) {
		this.childs.add(nodeL);
		return nodeL;
	}

	public void update(boolean gameResult) {
		this.visitsCount++;
		if(gameResult)
		{
			this.winsCount++;
		}
	}

	public Move getMostVisitedMove() 
	{
		this.getChilds().sort(new Comparator<MCNode>() {
			@Override
			public int compare(MCNode node1, MCNode node2) {
				return Integer.compare(node1.getVisitsCount(), node2.getVisitsCount());
			}
		});
		
		MCNode firstMostVisited = this.getChilds().get(0);
		
		if(firstMostVisited.getVisitsCount() > (this.getVisitsCount() / this.getChilds().size()) + 1)
		{
			return firstMostVisited.getMove();
		}
		else
		{
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
