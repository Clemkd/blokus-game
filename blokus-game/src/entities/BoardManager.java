package entities;

import java.util.ArrayList;

import utilities.CellPositions;
import utilities.InvalidMoveException;
import utilities.Vector2;

public abstract class BoardManager {
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
	 * Le tableau de cellules du plateau de jeu
	 */
	protected CellColor[][] cells;
	
	/**
	 * Ajoute la tuile à la grille du tableau de jeu
	 * @param tile La tuile à ajouter
	 * @param position La position sur la grille
	 * @throws InvalidMoveException Exception levée si l'ajout n'est pas possible
	 */
	public void addTile(Tile tile, Vector2<Integer> position) throws InvalidMoveException
	{
		if(!isValidMove(tile, position))
		{
			throw new InvalidMoveException("Tentative d'ajout d'une tuile sur une zone interdite");
		}
		
		Vector2<Integer> fc = tile.getFirstCase();

		for(int offsetX = fc.getX(); offsetX < Tile.WIDTH; offsetX++)
		{
			for(int offsetY = fc.getY(); offsetY < Tile.HEIGHT; offsetY++)
			{	
				if(tile.getCellType(offsetX, offsetY) == CellType.PIECE)
				{
					Vector2<Integer> currentGridPosition = new Vector2<Integer>(
							position.getX() + (fc.getX() - offsetX),
							position.getY() + (fc.getY() - offsetY));
					
					this.cells[currentGridPosition.getX()][currentGridPosition.getY()] = tile.getCouleur();
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
	public boolean isValidMove(Tile tile, Vector2<Integer> position)
	{
		Vector2<Integer> fc = tile.getFirstCase();

		for(int offsetX = fc.getX(); offsetX < Tile.WIDTH; offsetX++)
		{
			for(int offsetY = fc.getY(); offsetY < Tile.HEIGHT; offsetY++)
			{	
				if(tile.getCellType(offsetX, offsetY) == CellType.PIECE)
				{
					Vector2<Integer> v = new Vector2<Integer>(
							position.getX() + (fc.getX() - offsetX),
							position.getY() + (fc.getY() - offsetY));
					
					if(!this.isInBounds(v) || this.cells[v.getX()][v.getY()] != null)
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
	public ArrayList<Vector2<Integer>> getValidMoves(CellColor color)
	{
		ArrayList<Vector2<Integer>> validMoves = new ArrayList<Vector2<Integer>>();
		
		for(int x = 0; x < BoardManager.WIDTH; x++)
		{
			for(int y = 0; y < BoardManager.HEIGHT; y++)
			{
				Vector2<Integer> currentPosition = new Vector2<Integer>(x, y);
				if(this.cells[x][y] == null &&
						this.hasSameColorWithACornerCell(color, currentPosition) &&
						!this.hasSameColorWithAnAdjacentCell(color, currentPosition))
				{
					validMoves.add(currentPosition);
				}
			}
		}
		
		return validMoves;
	}
	
	/**
	 * Détermine s'il existe une cellule adjacente à la position donnée, possèdant la couleur spécifiée
	 * @param color La couleur à tester
	 * @param position La position de la cellule à tester
	 * @return Vrai s'il existe une case adjacente de même couleur, Faux dans le cas contraire
	 */
	protected boolean hasSameColorWithAnAdjacentCell(CellColor color, Vector2<Integer> position)
	{
		for(int i = 0; i < RELATIVES_POSITION_COUNT; i++)
		{
			Vector2<Integer> currentAdjacentPosition = CellPositions.values()[i].getPosition();
			Vector2<Integer> currentPosition = new Vector2<Integer>(
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
	protected boolean hasSameColorWithACornerCell(CellColor color, Vector2<Integer> position)
	{
		for(int i = 0; i < RELATIVES_POSITION_COUNT; i++)
		{
			Vector2<Integer> currentCornerPosition = CellPositions.values()[i + 4].getPosition();
			Vector2<Integer> currentPosition = new Vector2<Integer>(
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
	public boolean isInBounds(Vector2<Integer> position)
	{
		return position.getX() >= 0 && position.getX() < WIDTH && 
				position.getY() >= 0 && position.getY() < HEIGHT;
	}
	
	/**
	 * Réalise et retourne une copie de l'instance de l'objet
	 * @return La copie de l'instance d'objet
	 */
	public abstract BoardManager copy();
}
