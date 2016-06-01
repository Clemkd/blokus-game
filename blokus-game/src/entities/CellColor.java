package entities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import utilities.Vector2;

public enum CellColor implements Serializable {
	
	/**
	 * Cellule de couleur bleu avec le chemin d'accès vers l'image correspondante
	 */
	BLUE("/cells/bluecell.png",new Vector2(0,0)),
	
	/**
	 * Cellule de couleur rouge avec le chemin d'accès vers l'image correspondante
	 */
	RED("/cells/redcell.png",new Vector2(Board.WIDTH-1,Board.HEIGHT-1)),
	
	/**
	 * Cellule de couleur verte avec le chemin d'accès vers l'image correspondante
	 */
	GREEN("/cells/greencell.png",new Vector2(0,Board.HEIGHT-1)),
	
	/**
	 * Cellule de couleur jaune avec le chemin d'accès vers l'image correspondante
	 */
	YELLOW("/cells/yellowcell.png",new Vector2(Board.WIDTH-1,0)),
	
	GREY("",new Vector2(Board.WIDTH-1,0));
	
	/**
	 * attribut représentant le chemin d'accès vers l'image correspondate au type de celulle
	 */
	private String path;
	
	/**
	 * Le rendu de la cellule
	 */
	private BufferedImage image;
	
	/**
	 * La position de début
	 */
	private Vector2 startPosition;
	
	/**
	 * Constante de la largeur d'une cellule
	 */
	public final static int CELL_WIDTH = 20;
	
	/**
	 * Constante de la hauteur d'une cellule
	 */
	public final static int CELL_HEIGHT = 20;
	
	/**
	 * Constructeur de CellColor
	 * 
	 * @param path le chemin d'accès vers une image
	 * @param startPos la position de début
	 */
	private CellColor(String path, Vector2 startPos){
		this.path = path;
		try 
		{
			this.image = ImageIO.read(getClass().getResource(path));
		} 
		catch (IOException e) 
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		this.startPosition = startPos;
	}
	
	/**
	 * accesseur de path
	 * @return le path
	 */
	public String getPath(){
		return this.path;
	}
	
	/**
	 * Obtient le rendu de la cellule
	 * @return Le rendu
	 */
	public BufferedImage getImage()
	{
		return this.image;
	}

	public boolean equals(CellColor c)
	{
		return this.getPath() == c.getPath();
	}

	/**
	 * Getter de la positon de début
	 * 
	 * @return la positon de début
	 */
	public Vector2 getStartPosition() {
		return this.startPosition;
	}
}
