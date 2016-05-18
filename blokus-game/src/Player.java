import entities.CellColor;
import utilities.Vector2;

public abstract class Player {
	protected Vector2<CellColor> colors;
	
	public Player(Vector2<CellColor> colors) {
		this.colors = colors;
	}
	
	public Vector2<CellColor> getColors() {
		return this.colors;
	}
	
	public abstract Vector2<Integer> play();
	
	public abstract boolean updateBoard();
}
