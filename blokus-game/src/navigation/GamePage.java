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

public class GamePage extends Page implements ActionListener{
	
	private static final int POS_Y = 750;
	private BlokusButton buttonOption;
	private BlokusButton buttonUndo;
	private BlokusButton buttonRedo;
	private BlokusButton buttonSave;
	private BlokusButton buttonExit;
	private BufferedImage titre;
	private BufferedImage headerPlayerOne;
	private BufferedImage headerPlayerTwo;

	
	public GamePage() {
		super();
	}

	@Override
	public void update(float elapsedTime) {
		this.buttonOption.update(elapsedTime);
		this.buttonUndo.update(elapsedTime);
		this.buttonRedo.update(elapsedTime);
		this.buttonSave.update(elapsedTime);
		this.buttonExit.update(elapsedTime);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(this.titre,500, 51, null);
		g.drawImage(this.headerPlayerOne,32, 32, null);
		this.buttonOption.draw(g);
		this.buttonUndo.draw(g);
		this.buttonRedo.draw(g);
		this.buttonSave.draw(g);
		this.buttonExit.draw(g);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof BlokusButton){
			if(e.getSource().equals(this.buttonOption)){
				//TODO navigation menu options
			}else if(e.getSource().equals(this.buttonUndo)){
				//TODO annuler le coup
			}else if(e.getSource().equals(this.buttonRedo)){
				//TODO refaire le coup
			}else if(e.getSource().equals(this.buttonSave)){
				//TODO sauvegarder l'état du jeu
			}else if(e.getSource().equals(this.buttonExit)){
				Navigation.NavigateTo(Navigation.homePage);
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
		
		try {
			this.headerPlayerOne = ImageIO.read(new File(Page.PATH_RESOURCES_IMAGES+"greenJ1.png"));
		} catch (IOException e) {
			this.titre = null;
			e.printStackTrace();
		}
		
		this.buttonOption = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"optionsig.png");
		this.buttonOption.setPosition(new Vector2<Integer>(32, POS_Y));
		this.buttonOption.addListener(this);
		
		this.buttonUndo = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"annulerig.png");
		this.buttonUndo.setPosition(new Vector2<Integer>(505, POS_Y));
		this.buttonUndo.addListener(this);
		
		this.buttonRedo = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"refaireig.png");
		this.buttonRedo.setPosition(new Vector2<Integer>(650, POS_Y));
		this.buttonRedo.addListener(this);
		
		this.buttonSave = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"sauvegarder.png");
		this.buttonSave.setPosition(new Vector2<Integer>(940, POS_Y));
		this.buttonSave.addListener(this);
		
		this.buttonExit = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"exitig.png");
		this.buttonExit.setPosition(new Vector2<Integer>(1120, POS_Y));
		this.buttonExit.addListener(this);
		
	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub
		
	}

}
