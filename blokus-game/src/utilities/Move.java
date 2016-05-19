package utilities;

import entities.Tile;

public class Move {
	private final Vector2<Integer> position;
	private final Tile tile;
	private int value;
	
	public Move(int value) {
		this.value = value;
		this.tile = null;
		this.position = null;
	}
	
	public Move(Vector2<Integer> position, Tile tile, int value) {
		this.value = value;
		this.tile = tile;
		this.position = position;
	}
	
	public Move(Vector2<Integer> position, Tile tile) {
		this.value = 0;
		this.tile = tile;
		this.position = position;
	}
	
	public Vector2<Integer> getPosition() {
		return this.position;
	}
	
	public Tile getTile() {
		return this.tile;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
