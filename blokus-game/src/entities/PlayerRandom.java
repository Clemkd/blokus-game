package entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import utilities.Move;
import utilities.Vector2;

public class PlayerRandom extends Player implements ActionListener {

	private Random rand;
	private Timer timer;
	private Game game;

	public PlayerRandom(String name, List<CellColor> colors) {
		super(name, colors);
		this.rand = new Random();
		this.timer = null;
		this.game = null;
	}

	@Override
	public void play(Game g, CellColor c) {
		this.playing = true;
		this.game = g;
		this.timer = new Timer(800 + rand.nextInt(400), this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.timer.stop();
		
		this.chosenMove = Move.generateRandomValidMove(this.game);

		this.game = null;
		this.playing = false;
	}

}
