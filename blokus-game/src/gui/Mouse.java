package gui;

import javax.xml.crypto.dsig.CanonicalizationMethod;

import utilities.Vector2;

public class Mouse 
{
	public static final int NONE = -1;
	public static final int RIGHT = 1;
	public static final int LEFT = 3;
	public static final int WHEEL_SCROLL = 2;
	
	public static final boolean MOUSE_PRESSED = false;
	public static final boolean MOUSE_RELEASED = true;
	
	private static int lastScrollClicks = 0;
	private static int lastMouseButton = NONE;
	private static boolean mouseState = MOUSE_RELEASED;
	private static Vector2<Integer> mousePosition = new Vector2<Integer>(0, 0);

	/**
	 * /!\ Réservé à l'écouteur de clics de souris /!\
	 * Change la position du curseur de la souris
	 * @param mouseReleased Nouvelle position
	 */
	public static void setMousePosition(Vector2<Integer> position)
	{
		mousePosition = position;
	}
	
	/**
	 * Renvoi la position du curseur de la souris sous la forme de deux entiers
	 * @return Position curseur
	 */
	public static Vector2<Integer> getPosition() {
		return mousePosition;
	}

	/**
	 * /!\ Réservé à l'écouteur de clics de souris /!\
	 * Change l'état de la souris(appuyé/relaché)
	 * @param mouseReleased Nouvel état
	 */
	public static void setLastMouseButton(int b) {
		lastMouseButton = b;
	}
	
	/**
	 * Transmet le dernier bouton utilisé sur la souris.
	 * @return Bouton
	 */
	public static int getLastMouseButton() {
		return lastMouseButton;
	}
	
	/**
	 * Remet à NONE le dernier bouton utilisé, à utiliser après avoir traité celui-ci.
	 */
	public static void consumeLastMouseButton() {
		lastMouseButton = NONE;
	}
	
	/**
	 * /!\ Réservé à l'écouteur de clics de souris /!\
	 * Change l'état de la souris(appuyé/relaché)
	 * @param mouseReleased Nouvel état
	 */
	public static void setMouseState(boolean mouseReleased) {
		mouseState = mouseReleased;
	}
	
	/**
	 * Revoie MOUSE_RELEASED si les boutons de la souris sont relachés.
	 * @return Etat de la souris
	 */
	public static boolean isReleased()
	{
		return mouseState==MOUSE_RELEASED;
	}
	
	/**
	 * /!\ Réservé à l'écouteur de molette de souris /!\
	 * Change le nombre de clics perçus par la molette
	 * @param clicks Nouveau nombre de clics
	 */
	public static void setLastScrollClicks(int clicks) {
		lastScrollClicks = clicks;
	}
	
	/**
	 * Transmet le nombre de clics(=crans de rotation) enregistrés par la molette de la souris lors de son dernier évènement.
	 * @return Nombre de clics
	 */
	public static int getLastScrollClicks() {
		return lastScrollClicks;
	}
	
	/**
	 * Remet à 0 le compteur de clics de la molette, à utiliser après avoir traité ceux-ci.
	 */
	public static void consumeLastScroll() {
		lastScrollClicks = 0;
	}
}
