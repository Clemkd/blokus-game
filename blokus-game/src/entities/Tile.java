package entities;

public class Tile {
	/**
	 * Constante pour la hauteur de la matrice d'une Tile
	 */
	public static final int HEIGHT = 7;
	
	/**
	 * Constante pour la matrice de la matrice d'une Tile
	 */
	public static final int WIDTH = 7;
	
	/**
	 * Matrice d'entier permettant de représenter la forme d'une tuile et les possibilités d'adjacence
	 */
	private int[][] matrix = new int[HEIGHT][WIDTH] ;
	
	/**
	 * Couleur de la Tile
	 */
	CellColor couleur;
	
	/**
	 * 
	 * @param matrix la matrice représentant la forme de la Tile à instancier
	 * @param couleur le type de cellule de la Tile (couleur de la cellule)
	 */
	public Tile(int[][] matrix, CellColor couleur) {
		this.matrix = matrix;
		this.couleur = couleur;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public CellColor getCouleur() {
		return couleur;
	}

	public void setCouleur(CellColor couleur) {
		this.couleur = couleur;
	}
	
	
	
	
	
	
	
	
}
