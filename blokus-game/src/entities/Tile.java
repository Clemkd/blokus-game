package entities;

import utilities.Vector2;

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
	
	/**
	 * Accesseur de matrix
	 * @return la matrice de représentation de la Tile
	 */
	public int[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * Mutateur de matrix
	 * @param matrix nouvelle matrice pour la Tile
	 */
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	/**
	 * Accessseur de couleur
	 * @return le type de cellule
	 */
	public CellColor getCouleur() {
		return couleur;
	}
	
	/**
	 * mutateur de couleur
	 * @param couleur la nouvelle type de cellule de la Tile
	 */
	public void setCouleur(CellColor couleur) {
		this.couleur = couleur;
	}
	
	/**
	 * Méthode permettant la rotation dans un sens horaire de la matrice de la Tile
	 */
	public void rotateClockwise() {
		int[][] temp = new int[WIDTH][HEIGHT];

		for (int x = 0; x < WIDTH; x++){
			for (int y = 0; y < HEIGHT; y++){
				temp[WIDTH - y - 1][x] = this.matrix[x][y];
			}
		}

		matrix = temp;
	}
	
	/**
	 * Méthode permettant la rotation dans un sens anti-horaire de la matrice de la Tile
	 */
	public void rotateCounterClockwise()
	{
		int[][] temp = new int[WIDTH][HEIGHT];
	
		for (int x = 0; x < WIDTH; x++){
			for (int y = 0; y < HEIGHT; y++){
				temp[y][WIDTH - x - 1] = matrix[x][y];
			}
		}
	
	
		this.setMatrix(temp);
	}
	
	
	/**
	 * Méthode permettant d'effecture une symétrie de la Tile
	 */
	public void flip()
	   {
	      int[][] temp = new int[WIDTH][HEIGHT];
	      
	      for (int x = 0; x < WIDTH; x++)
	         for (int y = 0; y < HEIGHT; y++)
	            temp[WIDTH - x - 1][y] = matrix[x][y];
	            
	      this.setMatrix(temp);
	   }
}
