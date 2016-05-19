package utilities;

public enum CellPositions {
	UP			(new Vector2<Integer>(1, 0)),
	DOWN		(new Vector2<Integer>(1, 2)),
	RIGHT		(new Vector2<Integer>(2, 1)),
	LEFT		(new Vector2<Integer>(0, 1)),
	UP_LEFT		(new Vector2<Integer>(0, 0)),
	UP_RIGHT	(new Vector2<Integer>(2, 0)),
	DOWN_LEFT	(new Vector2<Integer>(0, 2)),
	DOWN_RIGHT	(new Vector2<Integer>(2, 2));

	private Vector2<Integer> position;
	
	CellPositions(Vector2<Integer> position)
	{
		this.position = position;
	}
	
	public Vector2<Integer> getPosition()
	{
		return this.position;
	}
}
