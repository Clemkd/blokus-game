package entities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum CellColor {
	
	/**
	 * Cellule de couleur bleu avec le chemin d'accès vers l'image correspondante
	 */
	BLUE("./resources/cells/bluecell.png"),
	
	/**
	 * Cellule de couleur rouge avec le chemin d'accès vers l'image correspondante
	 */
	RED("./resources/cells/redcell.png"),
	
	/**
	 * Cellule de couleur verte avec le chemin d'accès vers l'image correspondante
	 */
	GREEN("./resources/cells/greencell.png"),
	
	/**
	 * Cellule de couleur jaune avec le chemin d'accès vers l'image correspondante
	 */
	YELLOW("./resources/cells/yellowcell.png");
	
	/**
	 * attribut représentant le chemin d'accès vers l'image correspondate au type de celulle
	 */
	private String path;
	
	/**
	 * constante qui défini la largeur en pixel d'une cellulle d'une pièce
	 */
	public static final int CELL_WIDTH = 16;
	
	/**
	 * constante qui défini la hauteur en pixel d'une cellulle d'une pièce
	 */
	public static final int CELL_HEIGHT = 16;
	
	/**
	 * Le rendu de la cellule
	 */
	private BufferedImage image;
	
	/**
	 * Constructeur de CellColor
	 * @param path le chemin d'accès vers une image
	 */
	private CellColor(String path){
		this.path = path;
		try 
		{
			this.image = ImageIO.read(new File(path));
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
	
}
