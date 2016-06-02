package navigation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import gui.BlokusButton;
import gui.GraphicsPanel;

import utilities.BufferedHelper;
import utilities.Vector2;

public class TutorialPage extends Page implements ActionListener{
	
	private static final int	POS_X_PANEL	= 258;
	private static final int	POS_Y_PANEL	= 79;
	
	private static final int	POS_X_BUTTONLEFT	= 72;
	private static final int	POS_Y_BUTTONLEFT	= 348;
	
	private static final int	POS_X_BUTTONRIGHT	= 1016;
	private static final int	POS_Y_BUTTONRIGHT	= 348;
	
	private BlokusButton		buttonInterfaceLeft;
	private BlokusButton		buttonControlsLeft;
	private BlokusButton		buttonHomeLeft;
	
	private BlokusButton		buttonRulesRight;
	private BlokusButton		buttonControlsRight;
	private BlokusButton		buttonHomeRight;
	
	private boolean				onControl;
	private boolean				onRules;
	private boolean				onInterface;
	
	private BufferedImage		interfaceImage;
	private BufferedImage		controlsImage;
	private BufferedImage		rulesImage;
	
	
	public TutorialPage() {
		super();
		this.onControl = false;
		this.onInterface = true;
		this.onRules = false;
	}
	

	@Override
	public void loadContents() {		
		this.buttonInterfaceLeft = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"interfaceleft.png"));
		this.buttonInterfaceLeft.setPosition(new Vector2(POS_X_BUTTONLEFT,POS_Y_BUTTONLEFT));
		this.buttonInterfaceLeft.addListener(this);
		this.buttonInterfaceLeft.setEnabled(false);
		
		this.buttonRulesRight = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"reglesright.png"));
		this.buttonRulesRight.setPosition(new Vector2(POS_X_BUTTONRIGHT,POS_Y_BUTTONRIGHT));
		this.buttonRulesRight.addListener(this);
		this.buttonRulesRight.setEnabled(false);
		
		this.buttonControlsLeft = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"controlesleft.png"));
		this.buttonControlsLeft.setPosition(new Vector2(POS_X_BUTTONLEFT,POS_Y_BUTTONLEFT));
		this.buttonControlsLeft.addListener(this);
		this.buttonControlsLeft.setEnabled(false);
		
		this.buttonHomeLeft = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"accueilleft.png"));
		this.buttonHomeLeft.setPosition(new Vector2(POS_X_BUTTONLEFT,POS_Y_BUTTONLEFT));
		this.buttonHomeLeft.addListener(this);
		
		this.buttonControlsRight = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"controlesright.png"));
		this.buttonControlsRight.setPosition(new Vector2(POS_X_BUTTONRIGHT,POS_Y_BUTTONRIGHT));
		this.buttonControlsRight.addListener(this);
		
		this.buttonHomeRight = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"accueilright.png"));
		this.buttonHomeRight.setPosition(new Vector2(POS_X_BUTTONRIGHT,POS_Y_BUTTONRIGHT));
		this.buttonHomeRight.addListener(this);
		this.buttonHomeRight.setEnabled(false);
		
		try
		{
			this.interfaceImage = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_IMAGES + "interfaceimage.png"));
			//this.controlsImage = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_IMAGES + "controlsimage.png"));
			//this.rulesImage = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_IMAGES + "rulesimage.png"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void unloadContents() {
		


		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof BlokusButton) {
			if ((e.getSource().equals(this.buttonHomeLeft)) || (e.getSource().equals(this.buttonHomeRight))) {
				Navigation.NavigateTo(Navigation.homePage);
			}
			else if (e.getSource().equals(this.buttonControlsRight))
			{
				this.onInterface = false;
				this.onRules = false;
				this.onControl = true;
				this.buttonHomeLeft.setEnabled(false);				
				this.buttonControlsRight.setEnabled(false);
				this.buttonInterfaceLeft.setEnabled(true);
				this.buttonRulesRight.setEnabled(true);
			}
			else if (e.getSource().equals(this.buttonInterfaceLeft))
			{
				this.onInterface = true;
				this.onRules = false;
				this.onControl = false;
				this.buttonHomeLeft.setEnabled(true);				
				this.buttonControlsRight.setEnabled(true);
				this.buttonInterfaceLeft.setEnabled(false);
				this.buttonRulesRight.setEnabled(false);
			}	
			else if (e.getSource().equals(this.buttonRulesRight))
			{
				this.onInterface = false;
				this.onRules = true;
				this.onControl = false;
				this.buttonControlsLeft.setEnabled(true);				
				this.buttonHomeRight.setEnabled(true);
				this.buttonInterfaceLeft.setEnabled(false);
				this.buttonRulesRight.setEnabled(false);
			}
			else if (e.getSource().equals(this.buttonControlsLeft))
			{
				this.onInterface = false;
				this.onRules = false;
				this.onControl = true;
				this.buttonControlsLeft.setEnabled(false);				
				this.buttonHomeRight.setEnabled(false);
				this.buttonInterfaceLeft.setEnabled(true);
				this.buttonRulesRight.setEnabled(true);				
			}
			
		}
	}

	@Override
	public void updatePage(float elapsedTime) {
		GraphicsPanel.newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		this.buttonInterfaceLeft.update(elapsedTime);
		this.buttonControlsLeft.update(elapsedTime);
		this.buttonHomeLeft.update(elapsedTime);
		this.buttonRulesRight.update(elapsedTime);
		this.buttonControlsRight.update(elapsedTime);
		this.buttonHomeRight.update(elapsedTime);
	}

	@Override
	public void drawPage(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();

		if (this.onInterface) {
			g2d.drawImage(this.interfaceImage, POS_X_PANEL, POS_Y_PANEL, null);
			this.buttonHomeLeft.draw(g2d);
			this.buttonControlsRight.draw(g2d);
		}
		else if (this.onControl) {
			//g2d.drawImage(this.controlsImage, POS_X_PANEL, POS_Y_PANEL, null);
			this.buttonInterfaceLeft.draw(g2d);
			this.buttonRulesRight.draw(g2d);
		}
		else if (this.onRules) {
			//g2d.drawImage(this.rulesImage, POS_X_PANEL, POS_Y_PANEL, null);
			this.buttonControlsLeft.draw(g2d);
			this.buttonHomeRight.draw(g2d);
		}

		g2d.dispose();
		
	}
	
}
