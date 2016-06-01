package entities;

import java.io.Serializable;

public enum CellType implements Serializable {
	
	/**
	 * Vide
	 */
	BLANK(0),
	
	/**
	 * Corps d'une piece
	 */
	BODY(1),
	
	/**
	 * Extrémité d'une piece
	 */
	EXTREMITY(2);
	
	/**
	 * Valeur de la cellule
	 */
	private int value;
	
	/**
	 * Constructeur
	 * 
	 * @param value la valeur de la cellule
	 */
	private CellType(int value){
		this.value = value;
	}
	
	/**
	 * Getter de la valeur de la cellule
	 * 
	 * @return la valeur de la cellule 
	 */
	public int getValue(){
		return this.value;
	}
}
