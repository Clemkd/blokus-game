package entities;

import java.util.ArrayList;

import utilities.CellPositions;
import utilities.InvalidMoveException;
import utilities.OutOfBoundsException;
import utilities.Vector2;

public class Board {
	/**
	 * Le nombre de positions relatives (soit coins, soit adjacentes) d'une position
	 */
	protected final static int RELATIVES_POSITION_COUNT = 4;
	/**
	 * La largeur du plateau de jeu en nombre de cellules
	 */
	protected final static int WIDTH = 20;
	/**
	 * La hauteur du plateau de jeu en nombre de cellules
	 */
	protected final static int HEIGHT = 20;
	
	/**
	 * Couleur de départ pour l'angle haut gauche
	 */
	public final static CellColor UP_LEFT_COLOR = CellColor.RED;
	
	/**
	 * Couleur de départ pour l'angle haut droit
	 */
	public final static CellColor UP_RIGHT_COLOR = CellColor.GREEN;
	
	/**
	 * Couleur de départ pour l'angle bas gauche
	 */
	public final static CellColor DOWN_LEFT_COLOR = CellColor.BLUE;
	
	/**
	 * Couleur de départ pour l'angle bas droit
	 */
	public final static CellColor DOWN_RIGHT_COLOR = CellColor.YELLOW;
	
	/**
	 * Le tableau de cellules du plateau de jeu
	 */
	protected CellColor[][] cells;
	
	public Board()
	{
		this.cells = new CellColor[WIDTH][HEIGHT];
	}
	
	/**
	 * Modifie la cellule de la grille du plateau à la position spécifiée
	 * @param position La position de la cellule à modifier
	 * @param cell La cellule à insérer 
	 */
	private void setCell(Vector2 position, CellColor cell)
	{
		this.cells[position.getX()][position.getY()] = cell;
	}
	
	/**
	 * Obtient la cellule dans la grille du plateau à la position spécifiée
	 * @param position La position de la cellule à obtenir
	 * @return La cellule
	 */
	public CellColor getCell(Vector2 position) throws OutOfBoundsException
	{
		if(!this.isInBounds(position))
			throw new OutOfBoundsException("Tentative d'accès à une cellule en dehors du plateau");
		return this.cells[position.getX()][position.getY()];
	}
	
	/**
	 * Ajoute la tuile à la grille du tableau de jeu
	 * @param tile La tuile à ajouter
	 * @param position La position sur la grille
	 * @throws InvalidMoveException Exception levée si l'ajout n'est pas possible
	 */
	public void addTile(Tile tile, Vector2 position) throws InvalidMoveException
	{
		if(!isValidMove(tile, position))
		{
			throw new InvalidMoveException("Tentative d'ajout d'une tuile sur une zone interdite");
		}
		
		Vector2 fc = tile.getFirstCase();

		for(int offsetX = fc.getX(); offsetX < Tile.WIDTH; offsetX++)
		{
			for(int offsetY = fc.getY(); offsetY < Tile.HEIGHT; offsetY++)
			{	
				if(tile.getCellType(offsetX, offsetY) == CellType.PIECE)
				{
					Vector2 currentGridPosition = new Vector2(
							position.getX() + (fc.getX() - offsetX),
							position.getY() + (fc.getY() - offsetY));
					
					this.setCell(currentGridPosition, tile.getCouleur());
				}
			}
		}
	}
	
	/**
	 * Determine si le placement de la piece donnée est valide sur le plateau de jeu à la position spécifiée
	 * @param tile La piece à placer
	 * @param position La position sur le plateau de jeu (case de la tuile la plus en haut à gauche)
	 * @return Vrai si le placement est possible, Faux dans le cas contraire
	 */
	public boolean isValidMove(Tile tile, Vector2 position)
	{
		Vector2 fc = tile.getFirstCase();

		for(int offsetX = fc.getX(); offsetX < Tile.WIDTH; offsetX++)
		{
			for(int offsetY = fc.getY(); offsetY < Tile.HEIGHT; offsetY++)
			{	
				if(tile.getCellType(offsetX, offsetY) == CellType.PIECE)
				{
					Vector2 v = new Vector2(
							position.getX() + (fc.getX() - offsetX),
							position.getY() + (fc.getY() - offsetY));
					
					if(!this.isInBounds(v) || this.cells[v.getX()][v.getY()] != null || this.hasSameColorWithAnAdjacentCell(tile.getCouleur(), v))
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Retourne la liste des emplacements possibles pour la couleur spécifiée
	 * @param color La couleur à tester
	 * @return La liste des emplacements possibles pour la couleur spécifiée
	 */
	public ArrayList<Vector2> getValidMoves(CellColor color)
	{
		ArrayList<Vector2> validMoves = new ArrayList<Vector2>();
		
		try
		{
			for(int x = 0; x < Board.WIDTH; x++)
			{
				for(int y = 0; y < Board.HEIGHT; y++)
				{
					Vector2 currentPosition = new Vector2(x, y);
					if(this.getCell(currentPosition) == null && (this.hasSameColorWithACornerCell(color, currentPosition) && !this.hasSameColorWithAnAdjacentCell(color, currentPosition)))
					{
						validMoves.add(currentPosition);
					}
				}
			}
			
			Vector2 v;
			switch(color) {
				case BLUE:
					v = new Vector2(0,0);
					if(this.getCell(v)==null)
						validMoves.add(v);
					break;
				case YELLOW:
					v = new Vector2(WIDTH-1,0);
					if(this.getCell(v)==null)
						validMoves.add(v);
					break;
				case RED:
					v = new Vector2(WIDTH-1,HEIGHT-1);
					if(this.getCell(v)==null)
						validMoves.add(v);
					break;
				case GREEN:
					v = new Vector2(0,HEIGHT-1);
					if(this.getCell(v)==null)
						validMoves.add(v);
					break;
				default:
					v = new Vector2(0,0);
					if(this.getCell(v)==null)
						validMoves.add(v);
					break;
			}
			
		}
		catch(OutOfBoundsException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return validMoves;
	}
	
	/**
	 * Détermine s'il existe une cellule adjacente à la position donnée, possèdant la couleur spécifiée
	 * @param color La couleur à tester
	 * @param position La position de la cellule à tester
	 * @return Vrai s'il existe une case adjacente de même couleur, Faux dans le cas contraire
	 */
	protected boolean hasSameColorWithAnAdjacentCell(CellColor color, Vector2 position)
	{
		for(int i = 0; i < RELATIVES_POSITION_COUNT; i++)
		{
			Vector2 currentAdjacentPosition = CellPositions.values()[i].getPosition();
			Vector2 currentPosition = new Vector2(
					position.getX() + currentAdjacentPosition.getX(),
					position.getY() + currentAdjacentPosition.getY());
			
			if(this.isInBounds(currentPosition))
			{
				CellColor currentCell = this.cells[currentPosition.getX()][currentPosition.getY()];
				if(currentCell == color)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Détermine s'il existe une cellule en coin à la position donnée, possèdant la couleur spécifiée
	 * @param color La couleur à tester
	 * @param position La position de la cellule à tester
	 * @return Vrai s'il existe une case en coin de même couleur, Faux dans le cas contraire
	 */
	protected boolean hasSameColorWithACornerCell(CellColor color, Vector2 position)
	{
		for(int i = 0; i < RELATIVES_POSITION_COUNT; i++)
		{
			Vector2 currentCornerPosition = CellPositions.values()[i + 4].getPosition();
			Vector2 currentPosition = new Vector2(
					position.getX() + currentCornerPosition.getX(),
					position.getY() + currentCornerPosition.getY());
			
			if(this.isInBounds(currentPosition))
			{
				CellColor currentCell = this.cells[currentPosition.getX()][currentPosition.getY()];
				if(currentCell == color)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Determine si la position existe dans la grille
	 * @param position La position à tester
	 * @return Vrai si la position est dans la grille, Faux dans le cas contraire
	 */
	public boolean isInBounds(Vector2 position)
	{
		return position.getX() >= 0 && position.getX() < WIDTH && 
				position.getY() >= 0 && position.getY() < HEIGHT;
	}
	
	/**
	 * Réalise et retourne une copie de l'instance de l'objet
	 * @return La copie de l'instance d'objet
	 */
	public Board copy()
	{
		Board result = new Board();
		try {
		for(int x = 0; x < Board.WIDTH; x++)
		{
			for(int y = 0; y < Board.HEIGHT; y++)
			{
				Vector2 pos = new Vector2(x, y);
				result.setCell(pos, this.getCell(pos));
			}
		}
		} catch (OutOfBoundsException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
