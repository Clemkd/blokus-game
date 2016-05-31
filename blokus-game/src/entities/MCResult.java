package entities;

import utilities.Move;

public class MCResult {
	private Move move;
	private double value;

	public MCResult(Move mv)
	{
		this.move = mv;
		this.value = 0;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
