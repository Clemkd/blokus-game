package gui;

import java.awt.Graphics2D;

import entities.CellColor;
import entities.CellType;
import entities.Tile;
import utilities.Vector2;

public class BlokusTile implements DrawableInterface {
	/**
	 * L'objet correspondant aux données à représenter
	 */
	private Tile tile;
	
	/**
	 * La position de l'objet sur la zone de dessin parente
	 */
	private Vector2 position;
	
	public BlokusTile(Tile tile)
	{
		this.position = new Vector2();
		this.tile = tile;
	}
	
	
	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D)g.create();
		
		Vector2 fc = this.tile.getFirstCase();
		
		for(int offsetX = 0; offsetX < Tile.WIDTH; offsetX++)
		{
			for(int offsetY = 0; offsetY < Tile.HEIGHT; offsetY++)
			{	
				if(tile.getCellType(offsetY, offsetX) == CellType.PIECE)
				{
					Vector2 currentPosition = new Vector2(
							this.position.getX() + ( - fc.getX() + offsetX) * CellColor.CELL_WIDTH,
							this.position.getY() + ( - fc.getY() + offsetY) * CellColor.CELL_HEIGHT);
					
					g2d.drawImage(this.tile.getCouleur().getImage(), 
							currentPosition.getX(),
							currentPosition.getY(), 
							CellColor.CELL_WIDTH, 
							CellColor.CELL_HEIGHT, 
							null);
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

	/**
	 * Obtient l'objet de données de la représentation
	 * @return L'objet de données
	 */
	public Tile getTile() {
		return tile;
	}
}
