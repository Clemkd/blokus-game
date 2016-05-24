package gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import navigation.Page;
import utilities.Vector2;

public class BlokusCheckBox implements DrawableInterface {
	
	private boolean isEnabled;
	
	private boolean isChecked;
	
	private BufferedImage checked;
	
	private BufferedImage noChecked;
	
	private Vector2 position;
	
	private Dimension size;
	
	public BlokusCheckBox(String file, boolean enabled, boolean checked) {
		this.isChecked = checked;
		this.isEnabled = enabled;
		try {
			this.checked = ImageIO.read(new File(Page.PATH_RESOURCES_BOUTONS+"box.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
