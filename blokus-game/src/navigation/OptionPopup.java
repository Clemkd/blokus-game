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
import gui.DrawableInterface;
import gui.Window;
import utilities.Vector2;

public class OptionPopup implements ActionListener, DrawableInterface {
	
	BufferedImage imageOption;
	
	public OptionPopup(){
		this.imageOption = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_ARGB);
	}
	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override 
	public void draw(Graphics2D g) {
		
		Graphics2D g2 = (Graphics2D) g.create();
		Graphics2D batch = this.imageOption.createGraphics();
		batch.fillRect(10, 10, 1200, 800);
		batch.dispose();
		g2.drawImage(this.imageOption, 10, 10, 1200, 800, null);
		g2.dispose();
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}