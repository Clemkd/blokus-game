package gui;

import java.awt.Graphics2D;

import entities.Board;
import entities.CellColor;
import utilities.OutOfBoundsException;
import utilities.Vector2;

public class BlokusBoard implements DrawableInterface
{
	/**
	 * L'objet correspondant aux données à représenter
	 */
	private Board board;
	
	/**
	 * La position de l'objet sur la zone de dessin parente
	 */
	private Vector2 position;
	
	public BlokusBoard(Board board)
	{
		this.position = new Vector2();
		this.board = board;
	}
	
	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D)g.create();
		
		for(int x = 0; x < Board.WIDTH; x++)
		{
			for(int y = 0; y < Board.WIDTH; y++)
			{
				try {
					g2d.drawImage(this.board.getCell(new Vector2(x, y)).getImage(),
							this.position.getX() + x * CellColor.CELL_WIDTH, 
							this.position.getY() + y * CellColor.CELL_HEIGHT,
							CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, null);
				} catch (OutOfBoundsException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		g2d.dispose();
	}

	/**
	 * Obtient la position actuelle de l'objet sur la zone de dessin parente
	 * @return La position actuelle de l'objet
	 */
	public Vector2 getPosition() {
		return this.position;
	}
	
	/**
	 * Modifie la position de l'objet sur la zone de dessin parente
	 * @param position La nouvelle position de l'objet
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
}
