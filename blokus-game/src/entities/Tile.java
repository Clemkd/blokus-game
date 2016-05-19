package entities;

import java.awt.Color;
import java.util.ArrayList;

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
	private CellType[][] matrix = new CellType[HEIGHT][WIDTH] ;

	/**
	 * Couleur de la Tile
	 */
	private CellColor couleur;

	/**
	 * Emplacement dans la matrice de la premiere case de la piece
	 */
	private Vector2 firstCase;

	/**
	 * 
	 * @param matrix la matrice représentant la forme de la Tile à instancier
	 * @param couleur le type de cellule de la Tile (couleur de la cellule)
	 */
	public Tile(CellType[][] matrix, CellColor couleur) {
		this.matrix = matrix;
		this.couleur = couleur;
	}

	/**
	 * Accesseur de matrix
	 * @return la matrice de représentation de la Tile
	 */
	public CellType[][] getMatrix() {
		return matrix;
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
		CellType[][] temp = new CellType[WIDTH][HEIGHT];

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
		CellType[][] temp = new CellType[WIDTH][HEIGHT];

		for (int x = 0; x < WIDTH; x++){
			for (int y = 0; y < HEIGHT; y++){
				temp[y][WIDTH - x - 1] = matrix[x][y];
			}
		}


		this.matrix = temp;
	}


	/**
	 * Méthode permettant d'effectuer une symétrie de la Tile
	 */
	public void flip(){
		CellType[][] temp = new CellType[WIDTH][HEIGHT];

		for (int x = 0; x < WIDTH; x++)
			for (int y = 0; y < HEIGHT; y++)
				temp[WIDTH - x - 1][y] = matrix[x][y];

		this.matrix = temp;
	}

	/**
	 * Méthode indiquant la position de la première cellule de type piece dans la matrice
	 * @return un Vector2 
	 */
	public Vector2 getFirstCase(){
		if(this.firstCase == null){
			for(int i=0; i<WIDTH; i++){
				for(int j=0; j<HEIGHT; j++){
					if(this.matrix[i][j] == CellType.PIECE){
						return new Vector2(i, j);
					}
				}
			}
		}
		return firstCase;
	}

	/** 
	 * Méthode permettant d'accéder au type de cellule dans une matrice
	 * @param x la ligne dans la matrice
	 * @param y la colonne dans la matrice
	 * @return un CellType
	 */
	public CellType getCellType(int x, int y){
		return this.matrix[x][y];
	}

	/**
	 * Méthode qui dit si le clic est sur la pièce
	 * @param v la position du clic
	 * @param positionTile 
	 * @return vrai si la position est dans la pièce, faux sinon
	 */
	public boolean isInBounds(Vector2 v, Vector2 positionTile)
	{
		for(int i=0; i<WIDTH; i++){
			for(int j=0; j<HEIGHT; j++){
				if(this.getCellType(i, j) == CellType.PIECE)
				{
					if(positionTile.getX()+i*CellColor.BLUE.getImage().getWidth() < v.getX() 
							&& positionTile.getX()+(i+1)*CellColor.BLUE.getImage().getWidth() < v.getX() 
							&& positionTile.getY()+i*CellColor.BLUE.getImage().getHeight() < v.getY() 
							&& positionTile.getY()+(i+1)*CellColor.BLUE.getImage().getHeight() < v.getY())
					{
						return true;
					}
				}
			}

		}
		return false;
	}

	public int getTileWidth(){
		int width = 0;
		int temp;
		for(int i=0; i<Tile.HEIGHT; i++){
			temp = 0;
			for(int j=0; j<Tile.WIDTH; j++){
				if(this.matrix[j][i] == CellType.PIECE){
					temp++;
				}
			}
			if(temp>width){
				width = temp;
			}
		}
		return width;
	}
	/**
	 * Fonction permettant de recupérer une liste des tile en fonction d'une couleur
	 * @param cellColor
	 * @return une liste de toute les Tile possible
	 */
	public static ArrayList<Tile> getListOfNeutralTile(CellColor cellColor){
		ArrayList<Tile> listTile = new ArrayList<Tile>();

		// *
		CellType[][] matrix21 = { 
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}, 
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix21, cellColor));

		// *
		// *
		CellType[][] matrix20 = { 
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},  
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix20, cellColor));

		// * * *
		CellType[][] matrix18 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix18, cellColor));

		// *
		// * *
		CellType[][] matrix19 = { 
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}, 
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix19, cellColor));

		// *
		// *
		// *
		// *
		CellType[][] matrix13 = {
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK}, 
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix13, cellColor));

		// * * *
		//     * 
		CellType[][] matrix17 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix17, cellColor));

		//   *
		// * * *
		CellType[][] matrix16 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};	

		listTile.add(new Tile(matrix16, cellColor));

		// * *
		// * *
		CellType[][] matrix15 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix15, cellColor));


		// 	 * *
		// * *
		CellType[][] matrix14 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}, 
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix14, cellColor));


		// * * * * *
		CellType[][] matrix = { 
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER},
				{CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT},
				{CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix, cellColor));



		// *
		// * * * *
		CellType[][] matrix2 = { 						 
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}, 				
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix2, cellColor));
		
		
		// *
		// *
		// * *
		//   *
		CellType[][] matrix3 = {
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},   
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix3, cellColor));


		// * * *
		//   * *
		CellType[][] matrix8 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix8, cellColor));


		// * * *
		// *   *
		CellType[][] matrix7 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix7, cellColor));


		//   *
		// * * * *
		CellType[][] matrix4 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix4, cellColor));


		//   *
		//   *
		// * * *
		CellType[][] matrix10 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},   
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},   
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix10, cellColor));


		// *
		// *
		// * * *
		CellType[][] matrix11 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK, CellType.BLANK},  
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK, CellType.BLANK},   
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}

		};

		listTile.add(new Tile(matrix11, cellColor));


		//     *
		//   * *
		// * *
		CellType[][] matrix9 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix9, cellColor));


		//   * *
		//   *
		// * *
		CellType[][] matrix12 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},  
				{CellType.BLANK, CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix12, cellColor));


		//    *
		//  * * *
		//      *
		CellType[][] matrix5 = { 
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}, 
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK}, 
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix5, cellColor));








		//   *
		// * * *
		//   *
		CellType[][] matrix6 = {
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.ADJACENT, CellType.PIECE, CellType.PIECE, CellType.PIECE, CellType.ADJACENT, CellType.BLANK},
				{CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.PIECE, CellType.ADJACENT, CellType.CORNER, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.CORNER, CellType.ADJACENT, CellType.CORNER, CellType.BLANK, CellType.BLANK},
				{CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK}
		};

		listTile.add(new Tile(matrix6, cellColor));



























		return listTile;
	}

	@Override
	public String toString() {
		return "Tile [couleur=" + couleur + "]";
	}
}
