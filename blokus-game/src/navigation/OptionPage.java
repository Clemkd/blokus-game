package navigation;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Action;

import gui.BlokusButton;
import gui.Window;
import utilities.Vector2;

public class OptionPage extends Page implements ActionListener {
	
	private BufferedImage optionImage;
	
	private BlokusButton buttonReturn;
	
	@Override
	public void update(float elapsedTime) {
		this.buttonReturn.update(elapsedTime);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(this.optionImage, 0, 0, Window.WIDTH, Window.HEIGHT-35, null);
		this.buttonReturn.draw(g);
	}

	@Override
	public void loadContents() {
		try {
			this.optionImage = ImageIO.read(new File(Page.PATH_RESOURCES_IMAGES+"options.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.buttonReturn = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"back.png");
		this.buttonReturn.setPosition(new Vector2(10,10));
		this.buttonReturn.addListener(this);
		
	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof BlokusButton){
			if(e.getSource().equals(this.buttonReturn)){
				Navigation.NavigateTo(Navigation.previous);
			}
		}
		
	}

}
