package utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;

import entities.CellColor;
import entities.Game;
import entities.Player;
import entities.PlayerIA;
import entities.PlayerMCIA;
import entities.PlayerMedium;
import entities.PlayerRandom;

public class TestsIAStats extends JFrame 
{
    public TestsIAStats(int nbParties) 
    {
        super();
 
        setTitle("Statistiques IAs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 125);
 
        Object[][] donnees = new Object[5][5];
        
        ArrayList<CellColor> colorsP1 = new ArrayList<CellColor>();
		colorsP1.add(CellColor.BLUE);
		colorsP1.add(CellColor.RED);
		ArrayList<CellColor> colorsP2 = new ArrayList<CellColor>();
		colorsP2.add(CellColor.YELLOW);
		colorsP2.add(CellColor.GREEN);
        
        Player[] playersP1 = 
    	{ 
    		new PlayerRandom("IA Random", colorsP1),
    		new PlayerMedium("IA Medium", colorsP1),
    		new PlayerMCIA("IA Monte Carlo", colorsP1),
    		new PlayerIA("IA Alpha Beta", colorsP1)
    	};
        
        Player[] playersP2 = 
    	{ 
    		new PlayerRandom("IA Random", colorsP2),
    		new PlayerMedium("IA Medium", colorsP2),
    		new PlayerMCIA("IA Monte Carlo", colorsP2),
    		new PlayerIA("IA Alpha Beta", colorsP2)
    	};
        
        for(int i = 0; i < playersP1.length; i++)
        {
        	donnees[i][0] = playersP1[i].getName();
        	for(int j = 0; j < playersP2.length; j++)
            {
        		donnees[i][j+1] = simulateGamesAndReturnP1sVictoryCount(nbParties, playersP1[i], playersP2[j]) + "/" + nbParties;
            }
        }
 
        String[] entetes = {"P1 \\ P2", "IA Random", "IA Medium", "IA Monte Carlo", "IA AlphaBeta"};
 
        JLabel nbGame = new JLabel();
        nbGame.setText("Nombre de parties : " + nbParties);
        JTable tableau = new JTable(donnees, entetes);
 
        getContentPane().add(nbGame, BorderLayout.SOUTH);
        getContentPane().add(tableau.getTableHeader(), BorderLayout.NORTH);
        getContentPane().add(tableau, BorderLayout.CENTER);
    }
    
    public int simulateGamesAndReturnP1sVictoryCount(int gamesCount, Player p1, Player p2)
    {
    	int p1Wins = 0;
    	for(int i = 0; i < gamesCount; i++)
    	{
    		Game game = new Game(p1, p2);
    		while(!game.isTerminated())
    		{
    			game.update();
    		}
    		if(game.getWinner() == p1)
    		{
    			p1Wins++;
    		}
    	}
    	
    	return p1Wins;
    }
 
    public static void main(String[] args) 
    {
        new TestsIAStats(1).setVisible(true);
    }
}
