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
	private static final int DEFAULT_SAMPLE_SIZE = 5;
	private Random rand;
	private Timer timer;
	private Game game;
	private CellColor color;
	
	public PlayerMCIA(String name, List<CellColor> colors)
	{
		super(name, colors);
		this.rand = new Random();
		this.timer = null;
		this.game = null;
		this.color = null;
	}

	@Override
	public void play(Game game, CellColor c)
	{
		this.playing = true;
		this.game = game;
		this.color = c;
		this.timer = new Timer(800 + rand.nextInt(400), this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.timer.stop();
		
		Move move = this.monteCarlo(this.game, DEFAULT_SAMPLE_SIZE);
		if(move != null)
			this.chosenMove = move;
		else
			this.chosenMove = new Move(0);
		
		this.game = null;
		this.color = null;
		this.playing = false;
	}
	
	private Move monteCarlo(Game game, int sampleSize)
	{
		Move bestMove = null;
		Stack<Move> moves = new Stack<Move>();
		Player firstPlayer = game.getCurrentPlayer();
		
		for(int i = 0; i < sampleSize; i++)
		{
			while(!game.isTerminated())
			{
				Move move = Move.generateRandomValidMove(game);
				game.doMove(move);
				moves.push(move);
			}
			
			int value = game.getScore(firstPlayer);
			Move m = null;
			while(!moves.isEmpty())
			{
				m = moves.pop();
				game.revertMove(m);
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
}
