package utilities;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;

public class TestsIAStatsFromFile extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1426242134735692708L;

	public TestsIAStatsFromFile() {
		super();
		
		setTitle("Statistiques IAs");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 125);

		String[][] donnees = null;

		File file = new File("blokusTests.res");
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream("blokusTests.res");
				ObjectInputStream in = new ObjectInputStream(fis);
				donnees = (String[][]) in.readObject();
				in.close();
				fis.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (ClassNotFoundException c) {
				c.printStackTrace();
			}
		}

		String[] entetes = { "P1 \\ P2", "IA Random 2", "IA Medium 2", "IA Monte Carlo 2", "IA AlphaBeta 2" };

		JLabel nbGame = new JLabel();
		nbGame.setText("Nombre de parties : x");
		JTable tableau = new JTable(donnees, entetes);

		getContentPane().add(nbGame, BorderLayout.SOUTH);
		getContentPane().add(tableau.getTableHeader(), BorderLayout.NORTH);
		getContentPane().add(tableau, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		new TestsIAStatsFromFile().setVisible(true);
	}
}
