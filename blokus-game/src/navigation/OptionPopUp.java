package navigation;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.Window;

public class OptionPopUp extends JDialog {

	Window window;
	
	public OptionPopUp(JFrame parent, String title, boolean modal, Window window){
		super(parent, title, modal);
		
		this.window = window;
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.initComponent();
		this.setVisible(true);
	}
	
	
	private void initComponent(){
		JPanel panFond = new JPanel();
		panFond.setBackground(new Color(24, 99, 138));
		this.getContentPane().add(panFond, null);
		
		
	}
}
