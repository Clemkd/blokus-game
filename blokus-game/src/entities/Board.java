package entities;

public class Board extends BoardManager {

	public Board()
	{
		super();
		System.out.println(this.getValidMoves(CellColor.BLUE));
	}
	
	@Override
	public BoardManager copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
