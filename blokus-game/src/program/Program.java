package program;
import javax.swing.SwingUtilities;

import gui.Window;

public class Program 
{
	public static void main(String[] args) {
		Window window = new Window("Blokus");
		SwingUtilities.invokeLater(window);
	}
}
