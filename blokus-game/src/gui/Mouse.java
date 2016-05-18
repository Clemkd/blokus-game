package gui;

import utilities.Vector2;

public class Mouse 
{
	public static final int RIGHT = 0;
	public static final int LEFT = 2;
	public static final int WHEEL_SCROLL = 3;
	
	private static final int mouseState = LEFT;
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

	public static void privateSetMouseReleased(boolean mouseReleased) {
		Mouse.mouseReleased = mouseReleased;
	}
}
