package entities;

import java.awt.Color;

public enum CellColor {
	
	/**
	 * Cellule de couleur bleu avec le chemin d'accès vers l'image correspondante
	 */
	BLUE(""),
	
	/**
	 * Cellule de couleur rouge avec le chemin d'accès vers l'image correspondante
	 */
	RED(""),
	
	/**
	 * Cellule de couleur verte avec le chemin d'accès vers l'image correspondante
	 */
	GREEN(""),
	
	/**
	 * Cellule de couleur jaune avec le chemin d'accès vers l'image correspondante
	 */
	YELLOW("");
	
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
	 * Constructeur de CellColor
	 * @param path le chemin d'accès vers une image
	 */
	private CellColor(String path){
		this.path = path;
	}
	
	/**
	 * accesseur de path
	 * @return le path
	 */
	public String getPath(){
		return this.path;
	}
	
	
}
