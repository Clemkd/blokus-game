package program;
import javax.swing.SwingUtilities;

import gui.Window;
import utilities.OptionConfiguration;

public class Program 
{
	OptionConfiguration optionConfiguration = OptionConfiguration.loadOption();
	
	public static void main(String[] args) {
		Window window = new Window("Blokus");
		SwingUtilities.invokeLater(window);
	}
}
