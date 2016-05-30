package entities;

import java.io.Serializable;

public enum CellType implements Serializable {
	
	BLANK(0),
	BODY(1),
	EXTREMITY(2);
	
	private int value;
	
	private CellType(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
