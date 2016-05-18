package gui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import utilities.Vector2;

public class HomePage extends Page implements ActionListener{
	
	private BlokusButton buttonOnePLayer;
	private BlokusButton buttonTwoPlayer;
	private BlokusButton buttonLoad;
	private BlokusButton buttonTutorial;
	private BlokusButton buttonOption;
	private BlokusButton buttonExit;
	private BufferedImage titre;
	
	public HomePage() {
		super();
		//y = 51 et x = 500
		
		try {
			this.titre = ImageIO.read(new File("./resources/images/logo.png"));
		} catch (IOException e) {
			this.titre = null;
			e.printStackTrace();
		}
		
		this.buttonOnePLayer = new BlokusButton("./resources/boutons/oneplayer.png");
		this.buttonOnePLayer.setPosition(new Vector2<Integer>(488, 233));
		this.buttonOnePLayer.addListener(this);
		
		this.buttonTwoPlayer = new BlokusButton("./resources/boutons/twoplayers.png");
		this.buttonTwoPlayer.setPosition(new Vector2<Integer>(488, 318));
		this.buttonTwoPlayer.addListener(this);
		
		this.buttonLoad = new BlokusButton("./resources/boutons/load.png");
		this.buttonLoad.setPosition(new Vector2<Integer>(488, 406));
		this.buttonLoad.addListener(this);
		
		this.buttonTutorial = new BlokusButton("./resources/boutons/tutorial.png");
		this.buttonTutorial.setPosition(new Vector2<Integer>(488, 492));
		this.buttonTutorial.addListener(this);
		
		this.buttonOption = new BlokusButton("./resources/boutons/options.png");
		this.buttonOption.setPosition(new Vector2<Integer>(488, 577));
		this.buttonOption.addListener(this);
		
		this.buttonExit = new BlokusButton("./resources/boutons/exit.png");
		this.buttonExit.setPosition(new Vector2<Integer>(488, 662));
		this.buttonExit.addListener(this);
		
		
		
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
		if(e.getSource().getClass().isInstance(BlokusButton.class)){
			
		}
	}

}
