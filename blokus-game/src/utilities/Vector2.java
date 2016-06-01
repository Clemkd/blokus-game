package utilities;

import java.io.Serializable;

public class Vector2 implements Serializable {

	private static final long serialVersionUID = 1753883029768467866L;

	/**
	 * Premier élément du couple
	 */
	private int x;
	
	/**
	 * Second élément du couple
	 */
	private int y;

	/**
	 * Constructeur de Vecteur2
	 */
	public Vector2() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Constructeur de Vecteur2
	 * 
	 * @param x abscisse
	 * @param y ordonnée
	 */
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Fonction d'addition de 2 vecteur
	 * 
	 * @param v un vecteur
	 * @return un vecteur
	 */
	public Vector2 plus(Vector2 v)
	{
		return new Vector2(this.x+v.getX(), this.y+v.getY());
	}
	
	/**
	 * Fonction d'addition de 2 vecteur
	 * 
	 * @param v un vecteur
	 * @param v2 un autre vecteur
	 * @return un vecteur
	 */
	public static Vector2 plus(Vector2 v, Vector2 v2)
	{
		return new Vector2(v2.getX()+v.getX(), v2.getY()+v.getY());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector2))
			return false;
		Vector2 other = (Vector2) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vector2 [x: " + this.x + " y: " + this.y + "]";
	}

	/**
	 * Obtient le premier élément du couple
	 * @return L'élément
	 */
	public int getX() {
		return x;
	}

	/**
	 * Définit le premier élément du couple
	 * @param x Le nouvel élément
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Obtient le second élément du couple
	 * @return L'élément
	 */
	public int getY() {
		return y;
	}

	/**
	 * Définit le second élément du couple
	 * @param y Le nouvel élément
	 */
	public void setY(int y) {
		this.y = y;
	}
}
