package gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Board;
import entities.CellColor;
import navigation.Page;
import utilities.BufferedHelper;
import utilities.OutOfBoundsException;
import utilities.Vector2;

public class BlokusBoard implements DrawableInterface
{
	/**
	 * Constante de l'offset de l'abscisse
	 */
	public static final int OFFSET_X = 15;
	
	/**
	 * Constante de l'offset de l'ordonnée
	 */
	public static final int OFFSET_Y = 15;
	
	/**
	 * Plateau de jeu
	 */
	private BufferedImage boardImage;
	
	/**
	 * L'objet correspondant aux données à représenter
	 */
	private Board board;
	
	/**
	 * La position de l'objet sur la zone de dessin parente
	 */
	private Vector2 position;
	
	/**
	 * La taille du plateau
	 */
	private Dimension size;

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
	
	/**
	 * Constructeur du BlokusBoard
	 * 
	 * @param board le plateau
	 */
	public BlokusBoard(Board board)
	{
		this.position = new Vector2();
		this.board = board;
		this.isValidMovesShown = true;
		this.cellColorForValidMoves = CellColor.BLUE;
		this.validsMoves = new ArrayList<Vector2>();
		this.cellMask = BufferedHelper.generateSampleMask(CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, 0.5f);
		this.size = new Dimension();
		try 
		{
			this.boardImage = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_IMAGES + "plateau.png"));
			this.size = new Dimension(this.boardImage.getWidth(), this.boardImage.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(float elapsedTime) {
		if(this.isValidMovesShown)
		{
			this.validsMoves = this.board.getFreePositions(this.cellColorForValidMoves);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D)g.create();
		
		g2d.drawImage(this.boardImage, this.position.getX(), this.position.getY(), null, null);
		
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
								OFFSET_X + this.position.getX() + x * CellColor.CELL_WIDTH, 
								OFFSET_Y + this.position.getY() + y * CellColor.CELL_HEIGHT,
								CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, null);
					}
					
					if(this.isValidMovesShown)
					{
						if(this.validsMoves != null && !this.validsMoves.contains(new Vector2(x, y)))
						{
							g2d.drawImage(this.cellMask, 
									OFFSET_X + this.position.getX() + x * CellColor.CELL_WIDTH, 
									OFFSET_Y + this.position.getY() + y * CellColor.CELL_HEIGHT,
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
	 * Determine l'objet de données de la représentation
	 * @param b Le board a représenter
	 */
	public Board setBoard(Board b) {
		return this.board = b;
	}
	
	/**
	 * Obtient la taille du plateau
	 * @return La taille du plateau
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * Determine si la position est située sur le tableau
	 * @param position La position à tester
	 * @return Vrai si la position donnée est disposée sur le tableau, Faux dans le cas contraire
	 */
	public boolean isInBounds(Vector2 p) {
		return p.getX() > (this.getPosition().getX() + OFFSET_X) &&
				p.getX() < (this.getPosition().getX() + (int)this.getSize().getWidth() - OFFSET_X) &&
				p.getY() > (this.getPosition().getY() + OFFSET_Y) &&
				p.getY() < (this.getPosition().getY() + (int)this.getSize().getHeight() - OFFSET_Y);
	}
	
	/**
	 * Determine l'état d'affichage des placements possibles sur la grille de jeu
	 * @param state L'état d'affichage (True pour afficher, False dans la cas contraire)
	 * @param color La couleur à tester pour les possibilités
	 */
	public void showValidMoves(boolean state, CellColor color)
	{
		this.isValidMovesShown = state;
		this.cellColorForValidMoves = color;
	}
	
}
