package utilities;

import java.io.Serializable;

public enum CellPositions implements Serializable{
	UP			(new Vector2(0, -1)),
	DOWN		(new Vector2(0, 1)),
	RIGHT		(new Vector2(1, 0)),
	LEFT		(new Vector2(-1, 0)),
	UP_LEFT		(new Vector2(-1, -1)),
	UP_RIGHT	(new Vector2(1, -1)),
	DOWN_LEFT	(new Vector2(-1, 1)),
	DOWN_RIGHT	(new Vector2(1, 1));

	/**
	 * La position de la case
	 */
	private Vector2 position;
	
	/**
	 * Constructeur de CellPosition
	 * 
	 * @param position la position
	 */
	CellPositions(Vector2 position)
	{
		this.position = position;
	}
	
	/**
	 * Getter de la position
	 * 
	 * @return la position
	 */
	public Vector2 getPosition()
	{
		return this.position;
	}
}
