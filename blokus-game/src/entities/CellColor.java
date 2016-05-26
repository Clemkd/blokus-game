package entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import utilities.Vector2;

public enum CellColor {
	
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
	YELLOW("/cells/yellowcell.png",new Vector2(Board.WIDTH-1,0));
	
	/**
	 * attribut représentant le chemin d'accès vers l'image correspondate au type de celulle
	 */
	private String path;
	
	/**
	 * Le rendu de la cellule
	 */
	private BufferedImage image;
	
	private Vector2 startPosition;
	
	public final static int CELL_WIDTH = 20;
	public final static int CELL_HEIGHT = 20;
	
	/**
	 * Constructeur de CellColor
	 * @param path le chemin d'accès vers une image
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

	public Vector2 getStartPosition() {
		return this.startPosition;
	}
}
