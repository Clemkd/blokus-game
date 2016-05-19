package utilities;

public enum CellPositions {
	UP			(new Vector2(1, 0)),
	DOWN		(new Vector2(1, 2)),
	RIGHT		(new Vector2(2, 1)),
	LEFT		(new Vector2(0, 1)),
	UP_LEFT		(new Vector2(0, 0)),
	UP_RIGHT	(new Vector2(2, 0)),
	DOWN_LEFT	(new Vector2(0, 2)),
	DOWN_RIGHT	(new Vector2(2, 2));

	private Vector2 position;
	
	CellPositions(Vector2 position)
	{
		this.position = position;
	}
	
	public Vector2 getPosition()
	{
		return this.position;
	}
}
