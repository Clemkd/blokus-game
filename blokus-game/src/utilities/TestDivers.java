package utilities;

public class TestDivers {

	public static void main(String[] args) {
		int[][] matrice = {
				{101,102,103,104,105,106,107},
				{124,125,126,127,128,129,108},
				{123,141,142,143,144,130,109},
				{122,140,149,150,145,131,110},
				{121,139,148,147,146,132,111},
				{120,138,137,136,135,134,112},
				{119,118,117,116,115,114,113},
		};
		displayMatrix(matrice, 7);
		
		int[][] matrice2 = flip(matrice, 7);
		displayMatrix(matrice2, 7);
		
		int[][] matrice3 = flip(matrice2, 7);
		displayMatrix(matrice3, 7);
		
		int[][] matrice4 = flip(matrice3, 7);
		displayMatrix(matrice4, 7);
		
		int[][] matrice5 = flip(matrice4, 7);
		displayMatrix(matrice5, 7);
	}
	
	public static int[][] RotateMatrix(int[][] matrix, int n) {
	    int[][] ret = new int[n][n];

	    for (int i = 0; i < n; ++i) {
	        for (int j = 0; j < n; ++j) {
	            ret[i][j] = matrix[n - j - 1][i];
	        }
	    }

	    return ret;
	}
	
	public static void displayMatrix(int[][] matrix, int n) {
		for (int i = 0; i < n; ++i) {
			System.out.print('{');
	        for (int j = 0; j < n; ++j) {
	            System.out.print(matrix[i][j]);
	            if(j+1!=n)
	            	System.out.print(", ");
	        }
	        System.out.print("}\n");
	    }
		System.out.print('\n');
	}
	
	public static int[][] rotateCounterClockwise(int[][] matrix, int n) {
		int[][] temp = new int[n][n];

		for (int x = 0; x < n; x++){
			for (int y = 0; y < n; y++){
				temp[n - y - 1][x] = matrix[x][y];
			}
		}

		return temp;
	}

	/**
	 * Méthode permettant la rotation dans un sens anti-horaire de la matrice de la Tile
	 */
	public static int[][] rotateClockwise(int[][] matrix, int n)
	{
		int[][] temp = new int[n][n];

		for (int x = 0; x < n; x++){
			for (int y = 0; y < n; y++){
				temp[y][n - x - 1] = matrix[x][y];
			}
		}

		return temp;
	}


	/**
	 * Méthode permettant d'effectuer une symétrie de la Tile
	 */
	public static int[][] flip(int[][] matrix, int n){
		int[][] temp = new int[n][n];

		for (int x = 0; x < n; x++)
			for (int y = 0; y < n; y++)
				temp[n - x - 1][y] = matrix[x][y];

		return temp;
	}
}
