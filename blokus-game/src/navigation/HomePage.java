package navigation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import entities.CellColor;
import entities.Game;
import entities.PlayerHuman;
import gui.BlokusButton;
import gui.BlokusCheckBox;
import gui.Window;
import utilities.Vector2;

public class HomePage extends Page implements ActionListener {

	private static final int	POS_X	= 488;

	private BlokusCheckBox		buttonMusic;

	/**
	 * Bouton représentant le choix de 1 joueur
	 */
	private BlokusButton		buttonOnePLayer;

	/**
	 * Bouton représentant le choix de 2 joueurs
	 */
	private BlokusButton		buttonTwoPlayer;

	/**
	 * Bouton représentant le choix de charger une partie
	 */
	private BlokusButton		buttonLoad;

	/**
	 * Bouton représentant le choix du tutoriel
	 */
	private BlokusButton		buttonTutorial;

	/**
	 * Bouton représentant le choix des options du jeu
	 */
	private BlokusButton		buttonOption;

	/**
	 * Bouton représentant le choix de quitter le jeu
	 */
	private BlokusButton		buttonExit;

	/**
	 * Image représentant le logo du jeu
	 */
	private BufferedImage		titre;

	/**
	 * Constructeur
	 */
	public HomePage() {
		super();
	}

	@Override
	public void update(float elapsedTime) {
		this.buttonMusic.update(elapsedTime);
		this.buttonOnePLayer.update(elapsedTime);
		this.buttonTwoPlayer.update(elapsedTime);
		this.buttonLoad.update(elapsedTime);
		this.buttonTutorial.update(elapsedTime);
		this.buttonOption.update(elapsedTime);
		this.buttonExit.update(elapsedTime);

	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D batch = (Graphics2D) g.create();
		batch.drawImage(this.titre, 500, 51, null);
		this.buttonMusic.draw(batch);
		this.buttonOnePLayer.draw(batch);
		this.buttonTwoPlayer.draw(batch);
		this.buttonLoad.draw(batch);
		this.buttonTutorial.draw(batch);
		this.buttonOption.draw(batch);
		this.buttonExit.draw(batch);
		batch.dispose();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof BlokusButton) {
			if (e.getSource().equals(this.buttonMusic)) {
				System.out.println("ddddd");
				Window.getMusicPlayer().stopSound();
			}
			else if (e.getSource().equals(this.buttonOnePLayer)) {
				Navigation.NavigateTo(Navigation.iaPage);
			}
			else if (e.getSource().equals(this.buttonTwoPlayer)) {
				ArrayList<CellColor> colorsP1 = new ArrayList<CellColor>();
				colorsP1.add(CellColor.BLUE);
				colorsP1.add(CellColor.RED);
				ArrayList<CellColor> colorsP2 = new ArrayList<CellColor>();
				colorsP2.add(CellColor.YELLOW);
				colorsP2.add(CellColor.GREEN);

				((GamePage) Navigation.gamePage).setGame(
						new Game(new PlayerHuman("Joueur 1", colorsP1), new PlayerHuman("Joueur 2", colorsP2)));
				Navigation.NavigateTo(Navigation.gamePage);
			}
			else if (e.getSource().equals(this.buttonLoad)) {
				((GamePage) Navigation.gamePage).setGame(Game.load());
				//JFileChooser jFileChooser = new JFileChooser();
				//jFileChooser.showOpenDialog(jFileChooser);
			}
			else if (e.getSource().equals(this.buttonTutorial)) {
				Navigation.NavigateTo(Navigation.tutorialPage);
			}
			else if (e.getSource().equals(this.buttonOption)) {
				Navigation.previous = this;
				Navigation.NavigateTo(Navigation.optionPage);
			}
			else if (e.getSource().equals(this.buttonExit)) {
				System.exit(0);
			}
		}
	}

	@Override
	public void loadContents() {
		try {
			this.titre = ImageIO.read(getClass().getResourceAsStream(Page.PATH_RESOURCES_IMAGES + "logo.png"));
		}
		catch (IOException e) {
			this.titre = null;
			e.printStackTrace();
		}

		// this.buttonOnePLayer = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"oneplayer.png");

		BufferedImage checked = null;
		BufferedImage nochecked = null;

		try {
			checked = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "musicon.png"));
			nochecked = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "musicoff.png"));
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}

		this.buttonMusic = new BlokusCheckBox(true, true, checked, nochecked);
		this.buttonMusic.setPosition(new Vector2(1190, 50));
		this.buttonMusic.setSize(new Dimension(checked.getWidth(), checked.getHeight()));
		this.buttonMusic.addListener(this);

		this.buttonOnePLayer = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "oneplayer.png"));
		this.buttonOnePLayer.setPosition(new Vector2(POS_X, 233));
		this.buttonOnePLayer.addListener(this);

		this.buttonTwoPlayer = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "twoplayers.png"));
		this.buttonTwoPlayer.setPosition(new Vector2(POS_X, 318));
		this.buttonTwoPlayer.addListener(this);

		this.buttonLoad = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "load.png"));
		this.buttonLoad.setPosition(new Vector2(POS_X, 406));
		this.buttonLoad.addListener(this);

		this.buttonTutorial = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "tutorial.png"));
		this.buttonTutorial.setPosition(new Vector2(POS_X, 492));
		this.buttonTutorial.addListener(this);

		this.buttonOption = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "options.png"));
		this.buttonOption.setPosition(new Vector2(POS_X, 577));
		this.buttonOption.addListener(this);

		this.buttonExit = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "exit.png"));
		this.buttonExit.setPosition(new Vector2(POS_X, 662));
		this.buttonExit.addListener(this);

	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub

	}

}
