package utilities;

public class TestDivers {

	public static void main(String[] args) {
		int[][] matrice = {
				{100,100,100,100,100},
				{777,777,666,666,100},
				{777,777,666,666,100},
				{100,100,555,555,100},
				{100,100,555,555,100}
		};
		displayMatrix(matrice, 5);
		
		int[][] matrice2 = flip(matrice);
		displayMatrix(matrice2, 5);
		
		int[][] matrice3 = flip(matrice2);
		displayMatrix(matrice3, 5);
		
		int[][] matrice4 = flip(matrice3);
		displayMatrix(matrice4, 5);
		
		int[][] matrice5 = flip(matrice4);
		displayMatrix(matrice5, 5);
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
	
	public static int[][] flip(int[][] matrix) {
		int[][] temp = new int[5][5];

		for (int x = 0; x < 5; x++)
			for (int y = 0; y < 5; y++)
				temp[5 - x - 1][y] = matrix[x][y];

		return temp;
	}
}
