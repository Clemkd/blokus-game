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

public class IASelectionPage extends Page implements ActionListener{
	
	private BlokusButton buttonIAEasy;
	private BlokusButton buttonIAMedium;
	private BlokusButton buttonIAHard;
	private BufferedImage titre;
	private BufferedImage background;
	
	public IASelectionPage() {
		super();
		
		try {
			this.titre = ImageIO.read(new File("./resources/images/logo.png"));
		} catch (IOException e) {
			this.titre = null;
			e.printStackTrace();
		}
		
		try {
			this.background = ImageIO.read(new File("resources/images/backgroundsolo.png"));
		} catch (IOException e) {
			this.background = null;
			e.printStackTrace();
		}
		
		this.buttonIAEasy = new BlokusButton("./resources/boutons/iaeasy.png");
		this.buttonIAEasy.setPosition(new Vector2<Integer>(395, 345));
		this.buttonIAEasy.addListener(this);
		
		this.buttonIAMedium = new BlokusButton("./resources/boutons/iamedium.png");
		this.buttonIAMedium.setPosition(new Vector2<Integer>(395, 461));
		this.buttonIAMedium.addListener(this);
		
		this.buttonIAHard = new BlokusButton("./resources/boutons/iahard.png");
		this.buttonIAHard.setPosition(new Vector2<Integer>(395, 576));
		this.buttonIAHard.addListener(this);
	}
	
	@Override
	public void update(float elapsedTime) {
		this.buttonIAEasy.update(elapsedTime);
		this.buttonIAMedium.update(elapsedTime);
		this.buttonIAHard.update(elapsedTime);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(this.titre,500, 51, null);
		g.drawImage(this.background, 371, 267, null);
		this.buttonIAEasy.draw(g);
		this.buttonIAMedium.draw(g);
		this.buttonIAHard.draw(g);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof BlokusButton){
			if(e.getSource().equals(this.buttonIAEasy)){
				//TODO lancer une nouvelle partie avec une ia facile
			}else if(e.getSource().equals(this.buttonIAMedium)){
				//TODO lancer une nouvelle partie avec une ia moyenne
			}else if(e.getSource().equals(this.buttonIAHard)){
				//TODO lancer une nouvelle partie avec une ia difficile
			}
		}
		
	}

}
