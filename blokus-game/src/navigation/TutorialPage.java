package navigation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.BlokusButton;
import gui.GraphicsPanel;

import utilities.BufferedHelper;
import utilities.Vector2;

public class TutorialPage extends Page implements ActionListener{
	
	private static final int	POS_X_PANEL	= 258;
	private static final int	POS_Y_PANEL	= 79;
	private static final int	PANEL_WIDTH	= 758;
	private static final int	PANEL_HEIGHT	= 599;
	
	private static final int	POS_X_BUTTONLEFT	= 72;
	private static final int	POS_Y_BUTTONLEFT	= 348;
	
	private static final int	POS_X_BUTTONRIGHT	= 1016;
	private static final int	POS_Y_BUTTONRIGHT	= 348;
	
	private BlokusButton		buttonInterfaceLeft;
	private BlokusButton		buttonRulesLeft;
	private BlokusButton		buttonControlsLeft;
	private BlokusButton		buttonHomeLeft;
	
	private BlokusButton		buttonInterfaceRight;
	private BlokusButton		buttonRulesRight;
	private BlokusButton		buttonControlsRight;
	private BlokusButton		buttonHomeRight;
	
	private boolean				onControl;
	private boolean				onRules;
	private boolean				onInterface;
	
	private Font				customFontCheckbox;
	private Font				customFontTitle;
	private Font				customFontText;
	
	
	public TutorialPage() {
		super();
		this.onControl = false;
		this.onInterface = true;
		this.onRules = false;
		try {
			this.customFontCheckbox = BufferedHelper.getDefaultFont(16f);
			this.customFontTitle = BufferedHelper.getDefaultFont(20f);
			this.customFontText = BufferedHelper.getDefaultFont(14f);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void loadContents() {		
		this.buttonInterfaceLeft = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"interfaceleft.png"));
		this.buttonInterfaceLeft.setPosition(new Vector2(POS_X_BUTTONLEFT,POS_Y_BUTTONLEFT));
		this.buttonInterfaceLeft.addListener(this);
		
		this.buttonRulesLeft = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"reglesleft.png"));
		this.buttonRulesLeft.setPosition(new Vector2(POS_X_BUTTONLEFT,POS_Y_BUTTONLEFT));
		this.buttonRulesLeft.addListener(this);
		
		this.buttonControlsLeft = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"controlesleft.png"));
		this.buttonControlsLeft.setPosition(new Vector2(POS_X_BUTTONLEFT,POS_Y_BUTTONLEFT));
		this.buttonControlsLeft.addListener(this);
		
		this.buttonHomeLeft = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"accueilleft.png"));
		this.buttonHomeLeft.setPosition(new Vector2(POS_X_BUTTONLEFT,POS_Y_BUTTONLEFT));
		this.buttonHomeLeft.addListener(this);
		
		this.buttonInterfaceRight = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"interfaceright.png"));
		this.buttonInterfaceRight.setPosition(new Vector2(POS_X_BUTTONRIGHT,POS_Y_BUTTONRIGHT));
		this.buttonInterfaceRight.addListener(this);
		
		this.buttonRulesRight = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"reglesright.png"));
		this.buttonRulesRight.setPosition(new Vector2(POS_X_BUTTONRIGHT,POS_Y_BUTTONRIGHT));
		this.buttonRulesRight.addListener(this);
		
		this.buttonControlsRight = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"controlesright.png"));
		this.buttonControlsRight.setPosition(new Vector2(POS_X_BUTTONRIGHT,POS_Y_BUTTONRIGHT));
		this.buttonControlsRight.addListener(this);
		
		this.buttonHomeRight = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"accueilright.png"));
		this.buttonHomeRight.setPosition(new Vector2(POS_X_BUTTONRIGHT,POS_Y_BUTTONRIGHT));
		this.buttonHomeRight.addListener(this);
		
	}

	@Override
	public void unloadContents() {
		


		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof BlokusButton) {
			if ((e.getSource().equals(this.buttonHomeLeft)) || (e.getSource().equals(this.buttonHomeRight))) {
				Navigation.NavigateTo(Navigation.previous);
			}
			else if ((e.getSource().equals(this.buttonInterfaceLeft)) || (e.getSource().equals(this.buttonInterfaceRight))) {
				this.onInterface = true;
				this.onRules = false;
				this.onControl = false;
			}
			else if ((e.getSource().equals(this.buttonRulesLeft)) || (e.getSource().equals(this.buttonRulesRight))) {
				this.onInterface = false;
				this.onRules = true;
				this.onControl = false;
			}
			else if ((e.getSource().equals(this.buttonControlsLeft)) || (e.getSource().equals(this.buttonControlsRight))) {
				this.onInterface = false;
				this.onRules = false;
				this.onControl = true;
			}
		}
	}

	@Override
	public void updatePage(float elapsedTime) {
		GraphicsPanel.newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		this.buttonInterfaceLeft.update(elapsedTime);
		this.buttonRulesLeft.update(elapsedTime);
		this.buttonControlsLeft.update(elapsedTime);
		this.buttonHomeLeft.update(elapsedTime);
		this.buttonInterfaceRight.update(elapsedTime);
		this.buttonRulesRight.update(elapsedTime);
		this.buttonControlsRight.update(elapsedTime);
		this.buttonHomeRight.update(elapsedTime);
	}

	@Override
	public void drawPage(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();

		if (this.onInterface) {
			g2d.setColor(new Color(0, 93, 188));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);
			this.buttonHomeLeft.draw(g2d);
			this.buttonControlsRight.draw(g2d);
		}
		else if (this.onControl) {
			g2d.setColor(new Color(0, 141, 44));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);
			this.buttonInterfaceLeft.draw(g2d);
			this.buttonRulesRight.draw(g2d);

		}
		else if (this.onRules) {
			g2d.setColor(new Color(233, 188, 0));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);
			this.buttonControlsLeft.draw(g2d);
			this.buttonHomeRight.draw(g2d);

		}


		g2d.dispose();
		
	}
	
}
