package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entities.CellColor;
import entities.Game;
import entities.Player;
import entities.PlayerIA;
import entities.PlayerMCIA;
import entities.PlayerMedium;
import entities.PlayerRandom;

public class TestsIAStatsTexte {
	private int finishedGamesCount;
	private final int gamesToSimulate;
	
	public TestsIAStatsTexte(int nbParties) {
		super();
		
		this.finishedGamesCount = 0;

		String[][] donnees = new String[5][5];

		ArrayList<CellColor> colorsP1 = new ArrayList<CellColor>();
		colorsP1.add(CellColor.BLUE);
		colorsP1.add(CellColor.RED);
		ArrayList<CellColor> colorsP2 = new ArrayList<CellColor>();
		colorsP2.add(CellColor.YELLOW);
		colorsP2.add(CellColor.GREEN);

		Player[] playersP1 = { new PlayerRandom("IA Random 1", colorsP1), new PlayerMedium("IA Medium 1", colorsP1),
				new PlayerMCIA("IA Monte Carlo 1", colorsP1), new PlayerIA("IA Alpha Beta 1", colorsP1) };

		Player[] playersP2 = { new PlayerRandom("IA Random 2", colorsP2), new PlayerMedium("IA Medium 2", colorsP2),
				new PlayerMCIA("IA Monte Carlo 2", colorsP2), new PlayerIA("IA Alpha Beta 2", colorsP2) };

		this.gamesToSimulate = (playersP1.length*playersP2.length)*nbParties;
		
		for (int i = 0; i < playersP1.length; i++) {
			donnees[i][0] = playersP1[i].getName();
			for (int j = 0; j < playersP2.length; j++) {		
				donnees[i][j + 1] = simulateGamesAndReturnP1sVictoryCount(nbParties, playersP1[i], playersP2[j]) + "/"
						+ nbParties;
			}
		}

		try {
			FileOutputStream fileOption = new FileOutputStream("blokusTests.res");
			ObjectOutputStream out = new ObjectOutputStream(fileOption);
			out.writeObject(donnees);
			out.close();
			fileOption.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int simulateGamesAndReturnP1sVictoryCount(int gamesCount, Player p1, Player p2)
    {
    	int p1Wins = 0;
    	for(int i = 0; i < gamesCount; i++)
    	{
			if(p1 instanceof PlayerRandom) {
				p1 = new PlayerRandom(p1.getName(), p1.getColors());
			}
			else if(p1 instanceof PlayerMCIA) {
				p1 = new PlayerMCIA(p1.getName(), p1.getColors());
			}
			else if(p1 instanceof PlayerMedium) {
				p1 = new PlayerMedium(p1.getName(), p1.getColors());
			}
			else if(p1 instanceof PlayerIA) {
				p1 = new PlayerIA(p1.getName(), p1.getColors());
			}
			else {
				System.err.println("Erreur recréation joueur 1");
				break;
			}
			
			if(p2 instanceof PlayerRandom) {
				p2 = new PlayerRandom(p2.getName(), p2.getColors());
			}
			else if(p2 instanceof PlayerMCIA) {
				p2 = new PlayerMCIA(p2.getName(), p2.getColors());
			}
			else if(p2 instanceof PlayerMedium) {
				p2 = new PlayerMedium(p2.getName(),p2.getColors());
			}
			else if(p2 instanceof PlayerIA) {
				p2 = new PlayerIA(p2.getName(), p2.getColors());
			}
			else {
				System.err.println("Erreur recréation joueur 2");
				break;
			}
    		
    		Game game = new Game(p1, p2);
    		while(!game.isTerminated())
    		{
    			game.update();
    		}
    		DateFormat df = new SimpleDateFormat("HH:mm:ss");
    		Date dateobj = new Date();
    		System.out.println("["+df.format(dateobj)+"] Partie "+(++this.finishedGamesCount)+"/"+this.gamesToSimulate);
    		if(game.getWinner().equals(p1))
    		{
    			p1Wins++;
    		}
    		else if(game.getWinner()==null) {
    			System.err.println(" /!\\  /!\\      /!\\  /!\\ ");
    			System.err.println(" /!\\ PAS DE GAGNANT /!\\");
    			System.err.println(" /!\\  /!\\      /!\\  /!\\ ");
    		}
    	}
    	
    	return p1Wins;
    }

	public static void main(String[] args) {
		new TestsIAStatsTexte(1);
	}
}
