package entities;

import java.util.List;

import utilities.Move;

public class PlayerHuman extends Player {

	public PlayerHuman(String name, List<CellColor> colors) {
		super(name, colors);
	}
	
	public PlayerHuman(){
		super();
	}

	public PlayerHuman(PlayerHuman p) {
		super(p);
	}

	@Override
	public void play(Game game, CellColor c) {
		this.chosenMove = null;
		this.playing = true;
	}
	
	public void setChosenMove(Move m) {
		super.setChosenMove(m);
		this.playing = false;
	}

	@Override
	public Player copy() {
		return new PlayerHuman(this);
	}

	@Override
	public String toString() {
		return "PlayerHuman [colors=" + colors + ", name=" + name + ", tiles=" + tiles.toString() + "]";
	}
	
	
}
