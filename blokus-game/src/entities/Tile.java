package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utilities.Vector2;

public class Tile implements Serializable{
	
	private static final long serialVersionUID = 8909911977070049898L;
	/**
	 * Le nombre maximal de tiles
	 */
	public static final int MAX_COUNT = 21;
	/**
	 * Constante pour la hauteur de la matrice d'une Tile
	 */
	public static final int HEIGHT = 5;

	/**
	 * Constante pour la matrice de la matrice d'une Tile
	 */
	public static final int WIDTH = 5;
	public static final int SINGLE_CELL_ID = 0;

	/**
	 * Matrice d'entier permettant de représenter la forme d'une tuile et les
	 * possibilités d'adjacence
	 */
	private CellType[][] matrix = new CellType[HEIGHT][WIDTH];

	/**
	 * Couleur de la Tile
	 */
	private CellColor couleur;

	/**
	 * identifiant de la tuile
	 */
	private int id;

	/**
	 * Emplacement dans la matrice de la premiere case de la piece
	 */
	private Vector2 firstCase;

	/**
	 * La liste des rotations et flips du tile
	 */
	private ArrayList<Tile> tilesRotationsAndFlipsList;

	/**
	 * 
	 * @param matrix
	 *            la matrice représentant la forme de la Tile à instancier
	 * @param couleur
	 *            le type de cellule de la Tile (couleur de la cellule)
	 */
	public Tile(CellType[][] matrix, CellColor couleur, int id) {
		this.matrix = matrix;
		this.couleur = couleur;
		this.id = id;
		this.tilesRotationsAndFlipsList = new ArrayList<Tile>();
	}

	/**
	 * Accesseur de matrix
	 * 
	 * @return la matrice de représentation de la Tile
	 */
	public CellType[][] getMatrix() {
		return matrix;
	}

	public int getId() {
		return id;
	}

	/**
	 * Accessseur de couleur
	 * 
	 * @return le type de cellule
	 */
	public CellColor getColor() {
		return couleur;
	}

	/**
	 * mutateur de couleur
	 * 
	 * @param couleur
	 *            la nouvelle type de cellule de la Tile
	 */
	public void setCouleur(CellColor couleur) {
		this.couleur = couleur;
	}

	/**
	 * Retourne la rotation horaire du tile
	 */
	public Tile rotateClockwise() {
		CellType[][] temp = new CellType[WIDTH][HEIGHT];

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				temp[WIDTH - y - 1][x] = this.matrix[x][y];
			}
		}

		return new Tile(temp, this.getColor(), this.id);
	}

	/**
	 * Retourne la rotation anti-horaire du tile
	 */
	public Tile rotateCounterClockwise() {
		CellType[][] temp = new CellType[WIDTH][HEIGHT];

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				temp[y][WIDTH - x - 1] = matrix[x][y];
			}
		}

		return new Tile(temp, this.getColor(), this.id);
	}

	/**
	 * Retourne la symétrie du tile
	 */
	public Tile flip() {
		CellType[][] temp = new CellType[WIDTH][HEIGHT];

		for (int x = 0; x < WIDTH; x++)
			for (int y = 0; y < HEIGHT; y++)
				temp[WIDTH - x - 1][y] = matrix[x][y];

		return new Tile(temp, this.getColor(), this.id);
	}

	/**
	 * Méthode indiquant la position de la première cellule de type piece dans
	 * la matrice
	 * 
	 * @return un Vector2
	 */
	public Vector2 getFirstCase() {
		if (this.firstCase == null) {
			for (int i = 0; i < WIDTH; i++) {
				for (int j = 0; j < HEIGHT; j++) {
					if (this.matrix[i][j] != CellType.BLANK) {
						return new Vector2(i, j);
					}
				}
			}
		}
		return firstCase;
	}

	/**
	 * Méthode permettant d'accéder au type de cellule dans une matrice
	 * 
	 * @param x
	 *            la ligne dans la matrice
	 * @param y
	 *            la colonne dans la matrice
	 * @return un CellType
	 */
	public CellType getCellType(int x, int y) {
		return this.matrix[x][y];
	}

	public int getTileWidth() {
		int width = 0;
		int temp;
		for (int i = 0; i < Tile.HEIGHT; i++) {
			temp = 0;
			for (int j = 0; j < Tile.WIDTH; j++) {
				if (this.matrix[j][i] != CellType.BLANK) {
					temp++;
				}
			}
			if (temp > width) {
				width = temp;
			}
		}
		return width;
	}

	public ArrayList<Tile> getTilesListOfRotationsAndFlips() {
		if (this.tilesRotationsAndFlipsList.isEmpty()) {
			Tile t = this;
			this.tilesRotationsAndFlipsList.add(t);
			this.tilesRotationsAndFlipsList.add(t.flip());

			for (int i = 0; i < 3; i++) {
				t = t.rotateClockwise();
				this.tilesRotationsAndFlipsList.add(t);
				this.tilesRotationsAndFlipsList.add(t.flip());
			}
		}
		return this.tilesRotationsAndFlipsList;
	}

	/**
	 * Fonction permettant de recupérer une liste des tile en fonction d'une
	 * couleur
	 * 
	 * @param cellColor
	 * @return une liste de toute les Tile possible
	 */
	public static ArrayList<Tile> getListOfNeutralTile(CellColor cellColor) {
		ArrayList<Tile> listTile = new ArrayList<Tile>();

		// *
		CellType[][] matrix21 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix21, cellColor, SINGLE_CELL_ID));

		// *
		// *
		CellType[][] matrix20 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix20, cellColor, 1));

		// *
		// *
		// *
		CellType[][] matrix18 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix18, cellColor, 2));

		// * *
		// *
		CellType[][] matrix19 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix19, cellColor, 3));

		// *
		// *
		// *
		// *
		CellType[][] matrix13 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix13, cellColor, 4));

		// * * *
		// *
		CellType[][] matrix17 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.BODY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix17, cellColor, 5));

		// *
		// * *
		// *
		CellType[][] matrix16 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix16, cellColor, 6));

		// * *
		// * *
		CellType[][] matrix15 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix15, cellColor, 7));

		// * *
		// * *
		CellType[][] matrix14 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix14, cellColor, 8));

		// *
		// *
		// *
		// *
		// *
		CellType[][] matrix = { { CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix, cellColor, 9));

		// * *
		// *
		// *
		// *
		CellType[][] matrix2 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix2, cellColor, 10));

		// * *
		// * * *
		CellType[][] matrix3 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BODY, CellType.EXTREMITY },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix3, cellColor, 11));

		// * *
		// * *
		// *
		CellType[][] matrix8 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix8, cellColor, 12));

		// * * *
		// * *
		CellType[][] matrix7 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.BODY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix7, cellColor, 13));

		// *
		// * * * *
		CellType[][] matrix4 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.EXTREMITY, CellType.BODY, CellType.BODY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix4, cellColor, 14));

		// * * *
		// *
		// *
		CellType[][] matrix10 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.BODY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix10, cellColor, 15));

		// *
		// *
		// * * *
		CellType[][] matrix11 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.BODY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK }

		};

		listTile.add(new Tile(matrix11, cellColor, 16));

		// * *
		// * *
		// *
		CellType[][] matrix9 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix9, cellColor, 17));

		// * *
		// *
		// * *
		CellType[][] matrix12 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BODY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix12, cellColor, 18));

		// *
		// * * *
		// *
		CellType[][] matrix5 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.BODY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix5, cellColor, 19));

		// *
		// * * *
		// *
		CellType[][] matrix6 = { { CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.EXTREMITY, CellType.BODY, CellType.EXTREMITY, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.EXTREMITY, CellType.BLANK, CellType.BLANK },
				{ CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK, CellType.BLANK } };

		listTile.add(new Tile(matrix6, cellColor, 20));

		return listTile;
	}

	@Override
	public String toString() {
		String string ="";
		for (int i = 0; i < Tile.WIDTH; ++i) {
			string = string+'{';
	        for (int j = 0; j < Tile.HEIGHT; ++j) {
	        	string = string+Tile.getListOfNeutralTile(this.couleur).get(this.id).getMatrix()[i][j];
	            if(j+1!=Tile.WIDTH)
	            	string = string+", ";
	        }
	        string = string+"}\n";
	    }
		string = string+'\n';
		return string;
	}

	public List<Vector2> getExtremities() {
		ArrayList<Vector2> res = new ArrayList<Vector2>();

		for (int i = 0; i < Tile.WIDTH; i++) {
			for (int j = 0; j < Tile.HEIGHT; j++) {
				if (this.matrix[i][j] == CellType.EXTREMITY) {
					res.add(new Vector2(i, j));
				}
			}
		}

		return res;
	}
}
