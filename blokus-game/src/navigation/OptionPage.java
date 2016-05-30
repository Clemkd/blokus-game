package navigation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import gui.BlokusButton;
import gui.BlokusCheckBox;
import gui.BlokusLabel;
import gui.BlokusText;
import utilities.Vector2;
import utilities.BufferedHelper;

public class OptionPage extends Page implements ActionListener{
	
	private static final int POS_X_CHECKBOX = 400;
	private BlokusButton buttonGeneral;
	private BlokusButton buttonControl;
	private BlokusButton buttonRules;
	private BlokusButton buttonToValid;
	private BlokusButton buttonToCancel;
	
	private BlokusCheckBox checkBoxGeneral1;
	private BlokusCheckBox checkBoxAutoSave;
	private BlokusCheckBox checkBoxActivateAudio;
	private BlokusCheckBox checkBoxDaltonienMode;
	
	private BlokusLabel titleGame;
	private BlokusLabel titleAudio;
	private BlokusLabel titleVideo;
	
	private BlokusLabel titleKeyboard;
	private BlokusLabel titleMouse;
	
	private BlokusLabel titleGoal;
	private BlokusLabel titleRunning;
	private BlokusLabel titleEnd;
	private BlokusLabel titleAdvices;
	
	private BlokusText textRunning;
	private BlokusText textEnd;
	private BlokusText textAdvices;
	
	private boolean onControl;
	private boolean onRules;
	private boolean onGeneral;
	
	private Font customFontCheckbox;
	private Font customFontTitle;
	private Font customFontText;
	
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
			this.customFontText = BufferedHelper.getFontFromFile(new File(OptionPage.class.getClass().getResource(Page.PATH_RESOURCES_FONTS+"LEMONMILK.ttf").toURI()), 14f);
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
		this.titleKeyboard.update(elapsedTime);
		this.titleMouse.update(elapsedTime);
		this.titleGoal.update(elapsedTime);
		this.titleRunning.update(elapsedTime);
		this.titleEnd.update(elapsedTime);
		this.titleAdvices.update(elapsedTime);
		this.buttonToCancel.update(elapsedTime);
		this.textRunning.update(elapsedTime);
		this.textEnd.update(elapsedTime);
		this.textAdvices.update(elapsedTime);
		
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
			
			this.titleKeyboard.draw(g2d);
			this.titleMouse.draw(g2d);
		}else if(this.onRules){
			g2d.setColor(new Color(233,188,0));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);

			this.titleGoal.draw(g2d);
			this.titleRunning.draw(g2d);
			this.titleEnd.draw(g2d);
			this.titleAdvices.draw(g2d);
			
			this.textRunning.draw(g2d);
			this.textEnd.draw(g2d);
			this.textAdvices.draw(g2d);
		}
		
		this.buttonToCancel.draw(g2d);
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
			} else if(e.getSource().equals(this.buttonToCancel)) {
				/**
				 * 
				 *  TODO : Doit revenir a l'etat precedent des parametres
				 * 
				 */
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
		
		this.buttonToCancel = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"annuleroptions.png"));
		this.buttonToCancel.setPosition(new Vector2(371, 653));
		this.buttonToCancel.addListener(this);
		
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
		
		
		this.titleKeyboard = new BlokusLabel("CLAVIER", customFontTitle);
		this.titleKeyboard.setPosition(new Vector2(POS_X_TITLE, 102));
		
		this.titleMouse = new BlokusLabel("SOURIS", customFontTitle);
		this.titleMouse.setPosition(new Vector2(POS_X_TITLE, 163));
		
		this.titleGoal = new BlokusLabel("BUT du jeu : Pour chaque joueur, placer ses 21 pièces sur le plateau", customFontTitle);
		this.titleGoal.setPosition(new Vector2(POS_X_TITLE, 102));
		
		
		this.titleRunning = new BlokusLabel("DÉROULEMENT", customFontTitle);
		this.titleRunning.setPosition(new Vector2(POS_X_TITLE, 163));
		

		this.textRunning = new BlokusText("L’ordre dans lequel on joue est le suivant: bleu, jaune, rouge, vert." 
				+ "1) Au premier tour, chaque joueur place chacun son tour la première pièce de son choix sur le plateau de telle sorte que celle-ci recouvre une case d’angle du plateau."
				+ "2) Pour les tours suivants, chaque nouvelle pièce posée doit toucher une pièce de la même couleur par un ou plusieurs coins et jamais par les cotés."
				+ "3) Les pièces du jeu peuvent être disposées dans n’importe quel sens sur le plateau. Une pièce posée sur le plateau reste en place jusqu’à la fin de la partie.", customFontText, 770);
		this.textRunning.setPosition(new Vector2(POS_X_TITLE, 207));
		
		this.titleEnd = new BlokusLabel("FIN", customFontTitle);
		this.titleEnd.setPosition(new Vector2(POS_X_TITLE, 393));
		
		this.textEnd = new BlokusText("• Lorsqu’un joueur est bloqué et ne peut plus placer de pièce, il est obligé de passer son tour.", customFontText, 770);
		this.textEnd.setPosition(new Vector2(POS_X_TITLE, 437));
		
		this.titleAdvices = new BlokusLabel("CONSEILS", customFontTitle);
		this.titleAdvices.setPosition(new Vector2(POS_X_TITLE, 506));
		
		this.textAdvices = new BlokusText("• cherchez à progresser vers le centre du plateau, de manière à occuper un maximum de place.", customFontText, 770);
		this.textAdvices.setPosition(new Vector2(POS_X_TITLE, 550));

	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub
		
	}

}
