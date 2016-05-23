package navigation;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import gui.BlokusButton;
import utilities.Vector2;

public class HomePage extends Page implements ActionListener{
	

	private static final int POS_X = 488;

	/**
	 * Bouton représentant le choix de 1 joueur
	 */
	private BlokusButton buttonOnePLayer;
	
	/**
	 * Bouton représentant le choix de 2 joueurs
	 */
	private BlokusButton buttonTwoPlayer;
	
	/**
	 * Bouton représentant le choix de charger une partie
	 */
	private BlokusButton buttonLoad;
	
	/**
	 * Bouton représentant le choix du tutoriel
	 */
	private BlokusButton buttonTutorial;
	
	/**
	 * Bouton représentant le choix des options du jeu
	 */
	private BlokusButton buttonOption;
	
	/**
	 * Bouton représentant le choix de quitter le jeu
	 */
	private BlokusButton buttonExit;
	
	/**
	 * Image représentant le logo du jeu
	 */
	private BufferedImage titre;
	
	/**
	 * Constructeur
	 */
	public HomePage() {
		super();
	}

	@Override
	public void update(float elapsedTime) {
		this.buttonOnePLayer.update(elapsedTime);
		this.buttonTwoPlayer.update(elapsedTime);
		this.buttonLoad.update(elapsedTime);
		this.buttonTutorial.update(elapsedTime);
		this.buttonOption.update(elapsedTime);
		this.buttonExit.update(elapsedTime);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(this.titre,500, 51, null);
		this.buttonOnePLayer.draw(g);
		this.buttonTwoPlayer.draw(g);
		this.buttonLoad.draw(g);
		this.buttonTutorial.draw(g);
		this.buttonOption.draw(g);
		this.buttonExit.draw(g);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof BlokusButton){
			if(e.getSource().equals(this.buttonOnePLayer)){
				Navigation.NavigateTo(Navigation.iaPage);
			}else if(e.getSource().equals(this.buttonTwoPlayer)){
				Navigation.NavigateTo(Navigation.gamePage);
			}else if(e.getSource().equals(this.buttonLoad)){
				//TODO navigation option
			}else if(e.getSource().equals(this.buttonTutorial)){
				//TODO navigation Tutorial
			}else if(e.getSource().equals(this.buttonOption)){
				Navigation.NavigateTo(Navigation.optionPage);
			}else if(e.getSource().equals(this.buttonExit)){
				System.exit(0);
			}
		}
	}

	@Override
	public void loadContents() {
		try {
			this.titre = ImageIO.read(new File(Page.PATH_RESOURCES_IMAGES+"logo.png"));
		} catch (IOException e) {
			this.titre = null;
			e.printStackTrace();
		}
		
		this.buttonOnePLayer = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"oneplayer.png");
		this.buttonOnePLayer.setPosition(new Vector2(POS_X, 233));
		this.buttonOnePLayer.addListener(this);
		
		this.buttonTwoPlayer = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"twoplayers.png");
		this.buttonTwoPlayer.setPosition(new Vector2(POS_X, 318));
		this.buttonTwoPlayer.addListener(this);
		
		this.buttonLoad = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"load.png");
		this.buttonLoad.setPosition(new Vector2(POS_X, 406));
		this.buttonLoad.addListener(this);
		
		this.buttonTutorial = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"tutorial.png");
		this.buttonTutorial.setPosition(new Vector2(POS_X, 492));
		this.buttonTutorial.addListener(this);
		
		this.buttonOption = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"options.png");
		this.buttonOption.setPosition(new Vector2(POS_X, 577));
		this.buttonOption.addListener(this);
		
		this.buttonExit = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"exit.png");
		this.buttonExit.setPosition(new Vector2(POS_X, 662));
		this.buttonExit.addListener(this);
		
	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub
		
	}

}
