package gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Board;
import entities.CellColor;
import entities.Tile;
import utilities.BufferedImageHelper;
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
	
	/**
	 * Determine si l'affichage du plateau doit prendre en compte l'affichage des placements possibles
	 */
	private boolean isValidMovesShown;
	
	/**
	 * Le mask de cellule
	 */
	private BufferedImage cellMask;
	
	/**
	 * La couleur actuelle pour la recherche des placements possibles
	 */
	private CellColor cellColorForValidMoves;
	
	/**
	 * La liste des placements possibles
	 */
	private ArrayList<Vector2> validsMoves; 
	
	public BlokusBoard(Board board)
	{
		this.position = new Vector2();
		this.board = board;
		this.isValidMovesShown = false;
		this.cellColorForValidMoves = CellColor.BLUE;
		this.validsMoves = new ArrayList<Vector2>();
		this.cellMask = BufferedImageHelper.generateSampleMask(CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, 0.5f);
	}
	
	@Override
	public void update(float elapsedTime) {
		if(this.isValidMovesShown)
		{
			this.validsMoves = this.board.getValidMoves(this.cellColorForValidMoves);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D)g.create();
		
		
		for(int x = 0; x < Board.WIDTH; x++)
		{
			for(int y = 0; y < Board.WIDTH; y++)
			{
				try 
				{
					
					CellColor c = this.board.getCell(new Vector2(x, y));
					if(c != null)
					{
						g2d.drawImage(c.getImage(),
								this.position.getX() + x * CellColor.CELL_WIDTH, 
								this.position.getY() + y * CellColor.CELL_HEIGHT,
								CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, null);
					}
					
					if(this.isValidMovesShown)
					{
						if(this.validsMoves != null && !this.validsMoves.contains(new Vector2(x, y)))
						{
							g2d.drawImage(this.cellMask, 
									this.position.getX() + x * CellColor.CELL_WIDTH, 
									this.position.getY() + y * CellColor.CELL_HEIGHT,
									CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, null);
						}
					}
				} 
				catch (OutOfBoundsException e) 
				{
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
	
	/**
	 * Obtient l'objet de données de la représentation
	 * @return L'objet de données
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Determine si la position est située sur le tableau
	 * @param position La position à tester
	 * @return Vrai si la position donnée est disposée sur le tableau, Faux dans le cas contraire
	 */
	public boolean isInBounds(Vector2 position) {
		return position.getX() > this.getPosition().getX() && position.getX() < this.getPosition().getX() + Board.WIDTH * CellColor.CELL_WIDTH &&
				position.getY() > this.getPosition().getY() && position.getY() < this.getPosition().getY() + Board.HEIGHT * CellColor.CELL_HEIGHT;
	}
	
	/**
	 * Determine l'�tat d'affichage des placements possibles sur la grille de jeu
	 * @param state L'�tat d'affichage (True pour afficher, False dans la cas contraire)
	 * @param color La couleur � tester pour les possibilit�s
	 */
	public void showValidMoves(boolean state, CellColor color)
	{
		this.isValidMovesShown = state;
		this.cellColorForValidMoves = color;
	}
	
}
