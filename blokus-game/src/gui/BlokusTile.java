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
	
	public BlokusTile(Tile tile, Vector2 position)
	{
		this.position = position;
		this.tile = tile;
	}
	
	public BlokusTile(Tile tile)
	{
		this(tile, new Vector2());
	}
	
	@Override
	public void update(float elapsedTime) {
		if(!Mouse.isReleased())
		{
			if(this.isInBounds(Mouse.getPosition()))
			{
				System.out.println(this.getTile().toString());
			}
		}
		
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
	
	/**
	 * Méthode qui dit si le clic est sur la pièce
	 * @param v la position du clic
	 * @param positionTile 
	 * @return vrai si la position est dans la pièce, faux sinon
	 */
	public boolean isInBounds(Vector2 v)
	{
		Vector2 fc = this.tile.getFirstCase();
		
		for(int i=0; i<Tile.WIDTH; i++){
			for(int j=0; j<Tile.HEIGHT; j++){
				if(this.tile.getCellType(i, j) == CellType.PIECE)
				{
					Vector2 currentPosition = new Vector2(
						this.position.getX() + ( - fc.getX() + i) * CellColor.CELL_WIDTH,
						this.position.getY() + ( - fc.getY() + j) * CellColor.CELL_HEIGHT);
					
					System.out.println(currentPosition);
					System.exit(1);
					if(v.getX()>currentPosition.getX() && v.getY()>currentPosition.getY() && v.getX()<currentPosition.getX()+CellColor.CELL_WIDTH && v.getY()<currentPosition.getY()+CellColor.CELL_HEIGHT )
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
