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
	
	public PlayerMCIA(String name, List<CellColor> colors)
	{
		super(name, colors);
		this.rand = new Random();
		this.timer = null;
		this.game = null;
	}

	@Override
	public void play(Game game, CellColor c)
	{
		this.playing = true;
		this.game = game;
		this.timer = new Timer(5000 + rand.nextInt(400), this);
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
	private Move monteCarlo(Game game, int sampleSize)
	{
		Move bestMove = Move.EMPTY;
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
