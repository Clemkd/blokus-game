package navigation;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import gui.BlokusButton;
import gui.Window;
import utilities.Vector2;

public class TutorialPage extends Page implements ActionListener{
	
	BufferedImage tutorialImage;
	BlokusButton buttonReturn;
	
	@Override
	public void updatePage(float elapsedTime) {
		this.buttonReturn.update(elapsedTime);
	
	}

	@Override
	public void drawPage(Graphics2D g) {
		g.drawImage(this.tutorialImage, 0, 0, Window.WIDTH, Window.HEIGHT-35, null);
		this.buttonReturn.draw(g);
	}

	@Override
	public void loadContents() {
		try {
			this.tutorialImage = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_IMAGES+"tutoriel.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.buttonReturn = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"back.png"));
		this.buttonReturn.setPosition(new Vector2(10,10));
		this.buttonReturn.addListener(this);
	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof BlokusButton ){
			if(e.getSource().equals(this.buttonReturn)){
				Navigation.NavigateTo(Navigation.homePage);
			}
		}
	}
	
}
