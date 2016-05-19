package entities;

import java.util.List;

import utilities.Move;

public class PlayerHuman extends Player {

	public PlayerHuman(String name, List<CellColor> colors) {
		super(name, colors);
	}

	@Override
	public void play(CellColor c) {
		this.chosenMove = null;
		this.playing = true;
	}
	
	public void setMove(Move m) {
		this.chosenMove = m;
		this.playing = false;
	}
}
