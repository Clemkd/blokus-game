package entities;

import java.util.ArrayList;

import utilities.Vector2;

public abstract class BoardManager {
	/**
	 * Le nombre de positions relatives (soit coins, soit adjacentes) d'une position
	 */
	private final static int RELATIVES_POSITION_COUNT = 4;
	/**
	 * La largeur du plateau de jeu en nombre de cellules
	 */
	private final static int WIDTH = 20;
	/**
	 * La hauteur du plateau de jeu en nombre de cellules
	 */
	private final static int HEIGHT = 20;
	/**
	 * Le tableau de cellules du plateau de jeu
	 */
	private CellColor[][] cells;
	
	/**
	 * La liste des positions relatives adjacentes
	 */
	private static Vector2<Integer>[] adjacentPositions;
	
	/**
	 * La liste des positions relatives des coins
	 */
	private static Vector2<Integer>[] cornerPositions;
	
	/**
	 * Important : Initialise les dépendances
	 */
	public static void initialize()
	{
		adjacentPositions[0] = new Vector2<Integer>(1, 0);
		adjacentPositions[1] = new Vector2<Integer>(0, 1);
		adjacentPositions[2] = new Vector2<Integer>(2, 1);
		adjacentPositions[3] = new Vector2<Integer>(1, 2);
		
		cornerPositions[0] = new Vector2<Integer>(0, 0);
		cornerPositions[1] = new Vector2<Integer>(2, 0);
		cornerPositions[2] = new Vector2<Integer>(0, 2);
		cornerPositions[3] = new Vector2<Integer>(2, 2);
	}
	
	/**
	 * Determine si le placement de la piece donnée est valide sur le plateau de jeu à la position spécifiée
	 * @param tile La piece à placer
	 * @param position La position sur le plateau de jeu (case de la tuile la plus en haut à gauche)
	 * @return Vrai si le placement est possible, Faux dans le cas contraire
	 */
	public boolean isValidMove(Tile tile, Vector2<Integer> position)
	{
		
	}
	
	/**
	 * Retourne la liste des emplacements possibles pour la couleur spécifiée
	 * @param color La couleur à tester
	 * @return La liste des emplacements possibles pour la couleur spécifiée
	 */
	public ArrayList<Vector2<Integer>> getValidMoves(CellColor color)
	{
		
	}
	
	/**
	 * Détermine s'il existe une cellule adjacente à la position donnée, possèdant la couleur spécifiée
	 * @param color La couleur à tester
	 * @param position La position de la cellule à tester
	 * @return Vrai s'il existe une case adjacente de même couleur, Faux dans le cas contraire
	 */
	private boolean hasSameColorWithAnAdjacentCell(CellColor color, Vector2<Integer> position)
	{
		for(int i = 0; i < RELATIVES_POSITION_COUNT; i++)
		{
			Vector2<Integer> currentAdjacentPosition = adjacentPositions[i];
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
	private boolean hasSameColorWithACornerCell(CellColor color, Vector2<Integer> position)
	{
		for(int i = 0; i < RELATIVES_POSITION_COUNT; i++)
		{
			Vector2<Integer> currentCornerPosition = cornerPositions[i];
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
