package navigation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import gui.BlokusButton;
import gui.BlokusCheckBox;
import gui.BlokusLabel;
import utilities.Vector2;
import utilities.BufferedHelper;

public class OptionPage extends Page implements ActionListener{
	
	private static final int POS_X_CHECKBOX = 400;
	private BlokusButton buttonGeneral;
	private BlokusButton buttonControl;
	private BlokusButton buttonRules;
	private BlokusButton buttonToValid;
	
	private BlokusCheckBox checkBoxGeneral1;
	private BlokusCheckBox checkBoxAutoSave;
	private BlokusCheckBox checkBoxActivateAudio;
	private BlokusCheckBox checkBoxDaltonienMode;
	
	private BlokusLabel titleGame;
	private BlokusLabel titleAudio;
	private BlokusLabel titleVideo;
	
	private boolean onControl;
	private boolean onRules;
	private boolean onGeneral;
	
	private Font customFontCheckbox;
	private Font customFontTitle;
	
	private static final int POS_X_BOUTONS = 72;
	
	private static final int POS_X_PANEL = 341;
	private static final int POS_Y_PANEL = 72;
	private static final int PANEL_WIDTH = 867;
	private static final int PANEL_HEIGHT = 656;
	
	private static final int POS_X_TITLE = 371;
	
	public OptionPage() {
		super();
		this.onControl = false;
		this.onGeneral = true;
		this.onRules = false;
		try {
			this.customFontCheckbox = BufferedHelper.getFontFromFile(new File(OptionPage.class.getClass().getResource(Page.PATH_RESOURCES_FONTS+"LEMONMILK.ttf").toURI()), 16f);
			this.customFontTitle = BufferedHelper.getFontFromFile(new File(OptionPage.class.getClass().getResource(Page.PATH_RESOURCES_FONTS+"LEMONMILK.ttf").toURI()), 20f);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void update(float elapsedTime) {
		this.buttonControl.update(elapsedTime);
		this.buttonGeneral.update(elapsedTime);
		this.buttonRules.update(elapsedTime);
		this.buttonToValid.update(elapsedTime);
		this.checkBoxGeneral1.update(elapsedTime);
		this.checkBoxAutoSave.update(elapsedTime);
		this.titleGame.update(elapsedTime);
		this.titleAudio.update(elapsedTime);
		this.checkBoxActivateAudio.update(elapsedTime);
		this.titleVideo.update(elapsedTime);
		this.checkBoxDaltonienMode.update(elapsedTime);
		
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		this.buttonControl.draw(g2d);
		this.buttonGeneral.draw(g2d);
		this.buttonRules.draw(g2d);
		
		
		if(this.onGeneral){
			g2d.setColor(new Color(0, 93, 188));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);
			
			this.titleGame.draw(g2d);
			this.checkBoxGeneral1.draw(g2d);
			this.checkBoxAutoSave.draw(g2d);
			
			this.titleAudio.draw(g2d);
			this.checkBoxActivateAudio.draw(g2d);
			
			this.titleVideo.draw(g2d);
			this.checkBoxDaltonienMode.draw(g2d);
			
			
		}else if(this.onControl){
			g2d.setColor(new Color(0, 141,44));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);
		}else if(this.onRules){
			g2d.setColor(new Color(233,188,0));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);
		}
		this.buttonToValid.draw(g2d);

		
		g2d.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof BlokusButton){
			if(e.getSource().equals(this.buttonGeneral)){
				this.onGeneral = true;
				this.onRules = false;
				this.onControl = false;
			}else if(e.getSource().equals(this.buttonControl)){
				this.onGeneral = false;
				this.onRules = false;
				this.onControl = true;
			}else if(e.getSource().equals(this.buttonRules)){
				this.onGeneral = false;
				this.onRules = true;
				this.onControl = false;
			}else if(e.getSource().equals(this.buttonToValid))
				Navigation.NavigateTo(Navigation.previous);
		}
	}
	

	@Override
	public void loadContents() {
		
		
		this.buttonGeneral = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"general.png"));
		this.buttonGeneral.setPosition(new Vector2(POS_X_BOUTONS,314));
		this.buttonGeneral.addListener(this);
		
		this.buttonRules = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"regles.png"));
		this.buttonRules.setPosition(new Vector2(POS_X_BOUTONS, 374));
		this.buttonRules.addListener(this);
		
		this.buttonControl = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"controles.png"));
		this.buttonControl.setPosition(new Vector2(POS_X_BOUTONS, 433));
		this.buttonControl.addListener(this);
		
		this.buttonToValid = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"valider.png"));
		this.buttonToValid.setPosition(new Vector2(1035, 653));
		this.buttonToValid.addListener(this);
		
		this.titleGame = new BlokusLabel("JEU", customFontTitle);
		this.titleGame.setPosition(new Vector2(POS_X_TITLE, 102));
		
		this.checkBoxGeneral1 = new BlokusCheckBox(true, false, "METTRE EN SURBRILLANCE LES COUPS POSSIBLES", this.customFontCheckbox);
		this.checkBoxGeneral1.setPosition(new Vector2(POS_X_CHECKBOX, 150));
		
		this.checkBoxAutoSave = new BlokusCheckBox(true, false, "SAUVEGARDE AUTOMATIQUE", this.customFontCheckbox);
		this.checkBoxAutoSave.setPosition(new Vector2(POS_X_CHECKBOX, 180));
		
		this.titleAudio = new BlokusLabel("AUDIO", customFontTitle);
		this.titleAudio.setPosition(new Vector2(POS_X_TITLE, 252));
		
		this.checkBoxActivateAudio = new BlokusCheckBox(true, false, "MUSIQUE DE FOND", this.customFontCheckbox);
		this.checkBoxActivateAudio.setPosition(new Vector2(POS_X_CHECKBOX, 300));
		
		this.titleVideo= new BlokusLabel("VIDEO", customFontTitle);
		this.titleVideo.setPosition(new Vector2(POS_X_TITLE, 402));
		
		this.checkBoxDaltonienMode = new BlokusCheckBox(true, false, "MODE DALTONIEN", this.customFontCheckbox);
		this.checkBoxDaltonienMode.setPosition(new Vector2(POS_X_CHECKBOX, 450));

	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub
		
	}

}
