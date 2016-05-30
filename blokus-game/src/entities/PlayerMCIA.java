package entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.Timer;

import utilities.Move;

public class PlayerMCIA extends Player implements ActionListener
{
	private static final long serialVersionUID = -8387538910197440018L;
	
	private static final int DEFAULT_SAMPLE_SIZE = 5;
	private Random rand;
	private Timer timer;
	private Game game;
	
	public PlayerMCIA(String name, List<CellColor> colors)
	{
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
	public void play(Game g, CellColor c)
	{
		this.playing = true;
		this.game = g;
		this.timer = new Timer(800 + rand.nextInt(400), this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.timer.stop();
		
		// TODO : Usage de la copie de this.game
		this.chosenMove = this.monteCarlo(this.game, DEFAULT_SAMPLE_SIZE);
		
		this.game = null;
		this.playing = false;
	}
	
	// TODO : Reste à fix
	private Move monteCarlo(Game g, int sampleSize)
	{
		Move bestMove = Move.EMPTY;
		Stack<Move> moves = new Stack<Move>();
		Player firstPlayer = g.getCurrentPlayer();
		
		for(int i = 0; i < sampleSize; i++)
		{
			while(!g.isTerminated())
			{
				Move move = Move.generateRandomValidMove(g);
				g.doMove(move);
				moves.push(move);
			}
			
			int value = g.getScore(firstPlayer);
			Move m = null;
			while(!moves.isEmpty())
			{
				m = moves.pop();
				g.undoMove();
			}
			
			if(m == null)
			{
				throw new InternalError("Premier placement inexistant : Problème algorithme Monte Carlo");
			}
			
			m.setValue(value);
			
			if(bestMove == null || bestMove.getValue() < m.getValue())
			{
				bestMove = m;
			}
		}
		
		return bestMove;
	}

	@Override
	public Player copy() {
		return new PlayerMCIA(this);
	}
}
