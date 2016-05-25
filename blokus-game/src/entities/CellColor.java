package entities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import navigation.Page;

public enum CellColor {
	
	/**
	 * Cellule de couleur bleu avec le chemin d'accès vers l'image correspondante
	 */
	BLUE("/cells/bluecell.png"),
	
	/**
	 * Cellule de couleur rouge avec le chemin d'accès vers l'image correspondante
	 */
	RED("/cells/redcell.png"),
	
	/**
	 * Cellule de couleur verte avec le chemin d'accès vers l'image correspondante
	 */
	GREEN("/cells/greencell.png"),
	
	/**
	 * Cellule de couleur jaune avec le chemin d'accès vers l'image correspondante
	 */
	YELLOW("/cells/yellowcell.png");
	
	/**
	 * attribut représentant le chemin d'accès vers l'image correspondate au type de celulle
	 */
	private String path;
	
	/**
	 * Le rendu de la cellule
	 */
	private BufferedImage image;
	
	public final static int CELL_WIDTH = 20;
	public final static int CELL_HEIGHT = 20;
	
	/**
	 * Constructeur de CellColor
	 * @param path le chemin d'accès vers une image
	 */
	private CellColor(String path){
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
}
