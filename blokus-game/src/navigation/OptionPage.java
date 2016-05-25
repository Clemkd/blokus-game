package navigation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.BlokusButton;
import gui.BlokusCheckBox;
import utilities.Vector2;
import utilities.BufferedHelper;

public class OptionPage extends Page implements ActionListener{
	
	private BlokusButton buttonGeneral;
	private BlokusButton buttonControl;
	private BlokusButton buttonRules;
	private BlokusButton buttonToValid;
	private BlokusCheckBox checkBox;
	
	private Font font;

	private boolean onControl;
	private boolean onRules;
	private boolean onGeneral;
	
	private static final int POS_X_BOUTONS = 72;
	
	private static final int POS_X_PANEL = 341;
	private static final int POS_Y_PANEL = 72;
	private static final int PANEL_WIDTH = 867;
	private static final int PANEL_HEIGHT = 656;
	
	public OptionPage() {
		super();
		this.onControl = false;
		this.onGeneral = true;
		this.onRules = false;
	}
	@Override
	public void update(float elapsedTime) {
		this.buttonControl.update(elapsedTime);
		this.buttonGeneral.update(elapsedTime);
		this.buttonRules.update(elapsedTime);
		this.buttonToValid.update(elapsedTime);
		this.checkBox.update(elapsedTime);
		
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		this.buttonControl.draw(g2d);
		this.buttonGeneral.draw(g2d);
		this.buttonRules.draw(g2d);
		this.checkBox.draw(g2d);
		
		if(this.onGeneral){
			g2d.setColor(new Color(0, 93, 188));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);
			
			g2d.setFont(this.font);
			g2d.setColor(Color.WHITE);
			g2d.drawString("JEU", 391, 112);
			
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
		
        this.font = BufferedHelper.getFontFromFile(Page.PATH_RESOURCES_FONTS+"LEMONMILK.ttf");
		
		this.buttonGeneral = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"general.png");
		this.buttonGeneral.setPosition(new Vector2(POS_X_BOUTONS,314));
		this.buttonGeneral.addListener(this);
		
		this.buttonRules = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"regles.png");
		this.buttonRules.setPosition(new Vector2(POS_X_BOUTONS, 374));
		this.buttonRules.addListener(this);
		
		this.buttonControl = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"controles.png");
		this.buttonControl.setPosition(new Vector2(POS_X_BOUTONS, 433));
		this.buttonControl.addListener(this);
		
		this.buttonToValid = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"valider.png");
		this.buttonToValid.setPosition(new Vector2(1035, 653));
		this.buttonToValid.addListener(this);
		
		this.checkBox = new BlokusCheckBox(true, false);
		this.checkBox.setPosition(new Vector2(100, 100));

	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub
		
	}

}
