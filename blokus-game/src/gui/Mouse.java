package gui;

import javax.xml.crypto.dsig.CanonicalizationMethod;

import utilities.Vector2;

public class Mouse 
{
	public static final int RIGHT = 0;
	public static final int LEFT = 2;
	public static final int WHEEL_SCROLL = 3;
	
	private static int lastScrollClicks = 0;
	private static int mouseState = LEFT;
	private static boolean mouseReleased = true;
	private static Vector2<Integer> mousePosition = new Vector2<Integer>(0, 0);

	public static void privateSetMousePosition(Vector2<Integer> position)
	{
		Mouse.mousePosition = position;
	}
	
	public static Vector2<Integer> getPosition() {
		return mousePosition;
	}
	
	public static boolean isReleased()
	{
		return mouseReleased;
	}

	public static int getState() {
		return mouseState;
	}
	
	/**
	 * /!\ Réservé à l'écouteur de clics de souris /!\
	 * Change l'état de la souris(appuyé/relaché)
	 * @param mouseReleased Nouvel état
	 */
	public static void setMouseReleased(boolean mouseReleased) {
		Mouse.mouseReleased = mouseReleased;
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
