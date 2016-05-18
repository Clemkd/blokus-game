package entities;

public enum CellType {
	
	BLANK(0),
	PIECE(1),
	ADJACENT(2),
	CORNER(3);
	
	private int value;
	
	private CellType(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
