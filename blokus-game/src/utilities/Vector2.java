package utilities;

public class Vector2<T> {

	/**
	 * Premier élément du couple
	 */
	private T x;
	
	/**
	 * Second élément du couple
	 */
	private T y;

	public Vector2(T x, T y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x.hashCode();
		result = prime * result + this.y.hashCode();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector2<?>))
			return false;
		Vector2<T> other = (Vector2<T>) obj;
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
	public T getX() {
		return x;
	}

	/**
	 * Définit le premier élément du couple
	 * @param x Le nouvel élément
	 */
	public void setX(T x) {
		this.x = x;
	}

	/**
	 * Obtient le second élément du couple
	 * @return L'élément
	 */
	public T getY() {
		return y;
	}

	/**
	 * Définit le second élément du couple
	 * @param y Le nouvel élément
	 */
	public void setY(T y) {
		this.y = y;
	}
}
