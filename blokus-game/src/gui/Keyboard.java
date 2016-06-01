package gui;

public class Keyboard 
{
	public static final int NONE = 0;
	
	private static int lastKeyTyped = 0;

	/**
	 * /!\ Réservé à l'écouteur de clavier /!\
	 * Change la dernière touche pressée
	 * @param k Id de la touche
	 */
	public static void setLastKeyTyped(int k) {
		lastKeyTyped = k;
	}
	
	/**
	 * Transmet la dernière touche tapée au clavier
	 * @return Id de la touche
	 */
	public static int getLastKeyTyped() {
		return lastKeyTyped;
	}
	
	/**
	 * Remet à NONE la dernière touche tapée, à utiliser après avoir traité celle-ci.
	 */
	public static void consumeLastKeyTyped() {
		lastKeyTyped = NONE;
	}
}
