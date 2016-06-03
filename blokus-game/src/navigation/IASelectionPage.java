package navigation;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.CellColor;
import entities.Game;
import entities.PlayerHuman;
import entities.PlayerIA;
import entities.PlayerMCIA;
import entities.PlayerRandom;
import gui.BlokusButton;
import gui.GraphicsPanel;
import utilities.Vector2;

public class IASelectionPage extends Page implements ActionListener{
	
	private static final int POS_X = 395;

	/**
	 * Bouton représentant l'IA facile
	 */
	private BlokusButton buttonIAEasy;
	
	/**
	 * Bouton représentant l'IA moyenne
	 */
	private BlokusButton buttonIAMedium;

	/**
	 * Bouton représentant l'IA difficile
	 */
	private BlokusButton buttonIAHard;
	
	/**
	 * Bouton représentant le retour
	 */
	private BlokusButton buttonReturn;
	
	/**
	 * Image représentant le logo du jeu
	 */
	private BufferedImage titre;
	
	/**
	 * Image représentant le fond opaque derriere les boutons de sélections de l'IA
	 */
	private BufferedImage background;
	
	/**
	 * Constructeur de la page
	 */
	public IASelectionPage() {
		super();
		
		
	}
	
	@Override
	public void updatePage(float elapsedTime) {
		GraphicsPanel.newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		this.buttonIAEasy.update(elapsedTime);
		this.buttonIAMedium.update(elapsedTime);
		this.buttonIAHard.update(elapsedTime);
		this.buttonReturn.update(elapsedTime);
	}

	@Override
	public void drawPage(Graphics2D g) {
		g.drawImage(this.titre,500, 51, null);
		g.drawImage(this.background, 371, 200, null);
		this.buttonIAEasy.draw(g);
		this.buttonIAMedium.draw(g);
		this.buttonIAHard.draw(g);
		this.buttonReturn.draw(g);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof BlokusButton){
			ArrayList<CellColor> colorsP1 = new ArrayList<CellColor>();
			colorsP1.add(CellColor.BLUE);
			colorsP1.add(CellColor.RED);
			ArrayList<CellColor> colorsP2 = new ArrayList<CellColor>();
			colorsP2.add(CellColor.YELLOW);
			colorsP2.add(CellColor.GREEN);
			
			if(e.getSource().equals(this.buttonIAEasy)){
				((GamePage) Navigation.gamePage).setGame(new Game(new PlayerHuman("Joueur", colorsP1), new PlayerRandom("IA Facile", colorsP2)));
				Navigation.NavigateTo(Navigation.gamePage);
			}else if(e.getSource().equals(this.buttonIAMedium)){
				((GamePage) Navigation.gamePage).setGame(new Game(new PlayerHuman("Joueur", colorsP1), new PlayerMCIA("IA Moyenne", colorsP2)));
				Navigation.NavigateTo(Navigation.gamePage);
			}else if(e.getSource().equals(this.buttonIAHard)){
				((GamePage) Navigation.gamePage).setGame(new Game(new PlayerHuman("Joueur", colorsP1), new PlayerIA("IA Difficile", colorsP2)));
				Navigation.NavigateTo(Navigation.gamePage);
			}else if(e.getSource().equals(this.buttonReturn)){
				Navigation.NavigateTo(Navigation.homePage);
			}
		}
		
	}

	@Override
	public void loadContents() {
		try {
			this.titre = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_IMAGES+"logo.png"));
		} catch (IOException e) {
			this.titre = null;
			e.printStackTrace();
		}
		
		try {
			this.background = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_IMAGES+"backgroundsolo.png"));
		} catch (IOException e) {
			this.background = null;
			e.printStackTrace();
		}
		
		this.buttonIAEasy = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"iaeasy.png"));
		this.buttonIAEasy.setPosition(new Vector2(POS_X, 275));
		this.buttonIAEasy.addListener(this);
		
		this.buttonIAMedium = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"iamedium.png"));
		this.buttonIAMedium.setPosition(new Vector2(POS_X, 391));
		this.buttonIAMedium.addListener(this);
		
		this.buttonIAHard = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"iahard.png"));
		this.buttonIAHard.setPosition(new Vector2(POS_X, 506));
		this.buttonIAHard.addListener(this);
		
		this.buttonReturn = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"accueil.png"));
		this.buttonReturn.setPosition(new Vector2(32, 700));
		this.buttonReturn.addListener(this);
		
	}

	@Override
	public void unloadContents() {}

}
