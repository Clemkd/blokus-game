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
import gui.BlokusCheckBox;
import gui.BlokusLabel;
import gui.BlokusMessageBox;
import gui.BlokusNumericUpDown;
import gui.BlokusText;
import gui.BlokusWaitPress;
import gui.GraphicsPanel;
import gui.Window;
import program.Program;
import utilities.Vector2;
import utilities.BlokusMessageBoxButtonState;
import utilities.BlokusMessageBoxResult;
import utilities.BufferedHelper;
import utilities.OptionConfiguration;

public class OptionPage extends Page implements ActionListener {

	private static final int	POS_X_CHECKBOX	= 400;
	private BlokusButton		buttonGeneral;
	private BlokusButton		buttonControl;
	private BlokusButton		buttonRules;
	private BlokusButton		buttonToValid;
	private BlokusButton		buttonToCancel;

	private BlokusButton		buttonHelp1;
	private BlokusButton		buttonHelp2;
	private BlokusButton		buttonHelp3;

	private BlokusCheckBox		checkBoxDisplayPossibleMoves;
	private BlokusCheckBox		checkBoxAutoSave;
	private BlokusCheckBox		checkBoxActivateAudio;
	private BlokusCheckBox		checkBoxDaltonienMode;

	private BlokusLabel			titleGame;
	private BlokusLabel			titleAudio;
	private BlokusLabel			titleVideo;

	private BlokusLabel			titleKeyboard;
	
	private BlokusLabel			titleGoal;
	private BlokusLabel			titleRunning;
	private BlokusLabel			titleEnd;
	private BlokusLabel			titleAdvices;

	private BlokusText			textRunning1;
	private BlokusText			textRunning2;
	private BlokusText			textRunning3;
	private BlokusText			textRunning4;

	private BlokusText			textEnd;
	private BlokusText			textAdvices;

	private BlokusNumericUpDown	upDownVolume;
	private boolean				onControl;
	private boolean				onRules;
	private boolean				onGeneral;

	private Font				customFontCheckbox;
	private Font				customFontTitle;
	private Font				customFontText;

	private BufferedImage		imageHelp1, imageHelp2, imageHelp3;

	private static final int	POS_X_BOUTONS	= 72;

	private static final int	POS_X_PANEL		= 341;
	private static final int	POS_Y_PANEL		= 72;
	private static final int	PANEL_WIDTH		= 867;
	private static final int	PANEL_HEIGHT	= 656;

	private static final int	POS_X_TITLE		= 371;

	private OptionConfiguration	option;
	private BlokusWaitPress		keyReturnButton;
	private BlokusWaitPress		keyRotateClockwiseButton;
	private BlokusWaitPress		keyRotateCounterClockwiseButton;
	private BlokusWaitPress		keySymetryClockwiseButton;
	private BlokusWaitPress		keySymetryCounterClockwiseButton;

	public OptionPage() {
		super();
		this.option = Program.optionConfiguration;
		this.onControl = false;
		this.onGeneral = true;
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
	public void updatePage(float elapsedTime) {
		GraphicsPanel.newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

		this.buttonHelp1.setEnabled(this.onRules);
		this.buttonHelp2.setEnabled(this.onRules);
		this.buttonHelp3.setEnabled(this.onRules);

		this.checkBoxActivateAudio.setEnabled(this.onGeneral);
		this.checkBoxAutoSave.setEnabled(this.onGeneral);
		this.checkBoxDaltonienMode.setEnabled(this.onGeneral);
		this.checkBoxDisplayPossibleMoves.setEnabled(this.onGeneral);
		this.upDownVolume.setEnabled(this.onGeneral);

		this.buttonControl.update(elapsedTime);
		this.buttonGeneral.update(elapsedTime);
		this.buttonRules.update(elapsedTime);
		this.buttonToValid.update(elapsedTime);
		this.checkBoxDisplayPossibleMoves.update(elapsedTime);
		this.checkBoxAutoSave.update(elapsedTime);
		this.titleGame.update(elapsedTime);
		this.titleAudio.update(elapsedTime);
		this.checkBoxActivateAudio.update(elapsedTime);
		this.titleVideo.update(elapsedTime);
		this.checkBoxDaltonienMode.update(elapsedTime);
		
		this.titleGoal.update(elapsedTime);
		this.titleRunning.update(elapsedTime);
		this.titleEnd.update(elapsedTime);
		this.titleAdvices.update(elapsedTime);
		this.buttonToCancel.update(elapsedTime);
		this.textRunning1.update(elapsedTime);
		this.textRunning2.update(elapsedTime);
		this.textRunning3.update(elapsedTime);
		this.textRunning4.update(elapsedTime);
		this.textEnd.update(elapsedTime);
		this.textAdvices.update(elapsedTime);
		this.buttonHelp1.update(elapsedTime);
		this.buttonHelp2.update(elapsedTime);
		this.buttonHelp3.update(elapsedTime);
		this.upDownVolume.update(elapsedTime);
		
		// DEBUT PAGE CONTROLE
		this.titleKeyboard.update(elapsedTime);
		
		this.keyRotateClockwiseButton.setEnabled(this.onControl);
		this.keyRotateCounterClockwiseButton.setEnabled(this.onControl);
		this.keySymetryClockwiseButton.setEnabled(this.onControl);
		this.keySymetryCounterClockwiseButton.setEnabled(this.onControl);
		this.keyReturnButton.setEnabled(this.onControl);
		
		this.keyRotateClockwiseButton.update(elapsedTime);
		this.keyRotateCounterClockwiseButton.update(elapsedTime);
		this.keySymetryClockwiseButton.update(elapsedTime);
		this.keySymetryCounterClockwiseButton.update(elapsedTime);
		this.keyReturnButton.update(elapsedTime);
		// FIN PAGE CONTROLE
	}

	@Override
	public void drawPage(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		this.buttonControl.draw(g2d);
		this.buttonGeneral.draw(g2d);
		this.buttonRules.draw(g2d);

		if (this.onGeneral) {
			g2d.setColor(new Color(0, 93, 188));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);

			this.titleGame.draw(g2d);
			this.checkBoxDisplayPossibleMoves.draw(g2d);
			this.checkBoxAutoSave.draw(g2d);

			this.titleAudio.draw(g2d);
			this.checkBoxActivateAudio.draw(g2d);
			this.upDownVolume.draw(g2d);

			this.titleVideo.draw(g2d);
			this.checkBoxDaltonienMode.draw(g2d);
		}
		else if (this.onControl) {
			g2d.setColor(new Color(0, 141, 44));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);

			this.titleKeyboard.draw(g2d);

			this.keyRotateClockwiseButton.draw(g2d);
			this.keyRotateCounterClockwiseButton.draw(g2d);
			this.keySymetryClockwiseButton.draw(g2d);
			this.keySymetryCounterClockwiseButton.draw(g2d);
			this.keyReturnButton.draw(g2d);
		}
		else if (this.onRules) {
			g2d.setColor(new Color(233, 188, 0));
			g2d.fillRect(POS_X_PANEL, POS_Y_PANEL, PANEL_WIDTH, PANEL_HEIGHT);

			this.titleGoal.draw(g2d);
			this.titleRunning.draw(g2d);
			this.titleEnd.draw(g2d);
			this.titleAdvices.draw(g2d);

			this.textRunning1.draw(g2d);
			this.textRunning2.draw(g2d);
			this.textRunning3.draw(g2d);
			this.textRunning4.draw(g2d);

			this.textEnd.draw(g2d);
			this.textAdvices.draw(g2d);

			this.buttonHelp1.draw(g2d);
			this.buttonHelp2.draw(g2d);
			this.buttonHelp3.draw(g2d);
		}

		this.buttonToCancel.draw(g2d);
		this.buttonToValid.draw(g2d);
		g2d.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof BlokusButton) {
			if (e.getSource().equals(this.buttonGeneral)) {
				this.onGeneral = true;
				this.onRules = false;
				this.onControl = false;
			}
			else if (e.getSource().equals(this.buttonControl)) {
				this.onGeneral = false;
				this.onRules = false;
				this.onControl = true;
			}
			else if (e.getSource().equals(this.buttonRules)) {
				this.onGeneral = false;
				this.onRules = true;
				this.onControl = false;
			}
			else if (e.getSource().equals(this.buttonToValid)) {
				this.option.setHelp(this.checkBoxDisplayPossibleMoves.isChecked());
				this.option.setAutoSave(this.checkBoxAutoSave.isChecked());
				this.option.setDaltonienMode(this.checkBoxDaltonienMode.isChecked());
				this.option.setPlaySong(this.checkBoxActivateAudio.isChecked());
				this.option.setVolume(this.upDownVolume.getValue());
				
				this.option.setKeyRotateClockwise(this.keyRotateClockwiseButton.getKeyCode());
				this.option.setKeyRotateCounterClockwise(this.keyRotateCounterClockwiseButton.getKeyCode());
				this.option.setKeySymetryClockwise(this.keySymetryClockwiseButton.getKeyCode());
				this.option.setKeySymetryCounterClockwise(this.keySymetryCounterClockwiseButton.getKeyCode());
				this.option.setKeyReturn(this.keyReturnButton.getKeyCode());
				
				this.option.saveConfiguration();
				Navigation.NavigateTo(Navigation.previous);
			}
			else if (e.getSource().equals(this.buttonToCancel)) {
				Navigation.NavigateTo(Navigation.previous);
			}
			else if (e.getSource().equals(this.buttonHelp1)) {
				BlokusMessageBox imageBox = new BlokusMessageBox(imageHelp1, null, null,
						BlokusMessageBoxButtonState.VALID);
				imageBox.setStrokeColor(Color.ORANGE);
				imageBox.setStroke(3);
				imageBox.show(this);
			}
			else if (e.getSource().equals(this.buttonHelp2)) {
				BlokusMessageBox imageBox = new BlokusMessageBox(imageHelp2, null, null,
						BlokusMessageBoxButtonState.VALID);
				imageBox.setStrokeColor(Color.ORANGE);
				imageBox.setStroke(3);
				imageBox.show(this);
			}
			else if (e.getSource().equals(this.buttonHelp3)) {
				BlokusMessageBox imageBox = new BlokusMessageBox(imageHelp3, null, null,
						BlokusMessageBoxButtonState.VALID);
				imageBox.setStrokeColor(Color.ORANGE);
				imageBox.setStroke(3);
				imageBox.show(this);
			}
		}
		else if (e.getSource() instanceof BlokusMessageBox) {
			if (e.getActionCommand() == BlokusMessageBoxResult.YES.getActionCommand()) {
				Navigation.NavigateTo(Navigation.homePage);
			}

			// Fermeture de la message box
			if (this.getMessageBox() != null) {
				this.getMessageBox().close(this);
			}
		}
		else if (e.getSource() instanceof BlokusNumericUpDown) {
			if (e.getSource().equals(this.upDownVolume)) {
				this.option.setVolume(this.upDownVolume.getValue());
				Window.getMusicPlayer().setVolume(this.upDownVolume.getValue());
			}
		}
		else if (e.getSource() instanceof BlokusCheckBox) {
			if (e.getSource().equals(this.checkBoxActivateAudio)) {
				if (this.checkBoxActivateAudio.isChecked() && !Window.getMusicPlayer().isRunning())
					Window.getMusicPlayer().playContinuously();
				else if (!this.checkBoxActivateAudio.isChecked() && Window.getMusicPlayer().isRunning())
					Window.getMusicPlayer().stopSound();
			}
		}
	}

	@Override
	public void loadContents() {
		try {
			this.imageHelp1 = ImageIO
					.read(getClass().getResourceAsStream(Page.PATH_RESOURCES_IMAGES + "imagehelp1.png"));
			this.imageHelp2 = ImageIO
					.read(getClass().getResourceAsStream(Page.PATH_RESOURCES_IMAGES + "imagehelp2.png"));
			this.imageHelp3 = ImageIO
					.read(getClass().getResourceAsStream(Page.PATH_RESOURCES_IMAGES + "imagehelp3.png"));
		}
		catch (IOException e) {
			this.imageHelp1 = null;
			this.imageHelp2 = null;
			this.imageHelp3 = null;
			e.printStackTrace();
		}

		this.buttonHelp1 = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "help.png"));
		this.buttonHelp1.setPosition(new Vector2(350, 250));
		this.buttonHelp1.addListener(this);

		this.buttonHelp2 = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "help.png"));
		this.buttonHelp2.setPosition(new Vector2(350, 300));
		this.buttonHelp2.addListener(this);

		this.buttonHelp3 = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "help.png"));
		this.buttonHelp3.setPosition(new Vector2(350, 350));
		this.buttonHelp3.addListener(this);

		this.buttonGeneral = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "general.png"));
		this.buttonGeneral.setPosition(new Vector2(POS_X_BOUTONS, 314));
		this.buttonGeneral.addListener(this);

		this.buttonRules = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "regles.png"));
		this.buttonRules.setPosition(new Vector2(POS_X_BOUTONS, 374));
		this.buttonRules.addListener(this);

		this.buttonControl = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "controles.png"));
		this.buttonControl.setPosition(new Vector2(POS_X_BOUTONS, 433));
		this.buttonControl.addListener(this);

		this.buttonToValid = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "valider.png"));
		this.buttonToValid.setPosition(new Vector2(1035, 653));
		this.buttonToValid.addListener(this);

		this.buttonToCancel = new BlokusButton(
				getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "annuleroptions.png"));
		this.buttonToCancel.setPosition(new Vector2(371, 653));
		this.buttonToCancel.addListener(this);

		this.titleGame = new BlokusLabel("JEU", customFontTitle);
		this.titleGame.setPosition(new Vector2(POS_X_TITLE, 102));

		this.checkBoxDisplayPossibleMoves = new BlokusCheckBox(true, this.option.isHelp(),
				"METTRE EN SURBRILLANCE LES COUPS POSSIBLES", this.customFontCheckbox);
		this.checkBoxDisplayPossibleMoves.setPosition(new Vector2(POS_X_CHECKBOX, 150));

		this.checkBoxAutoSave = new BlokusCheckBox(true, this.option.isAutoSave(), "SAUVEGARDE AUTOMATIQUE",
				this.customFontCheckbox);
		this.checkBoxAutoSave.setPosition(new Vector2(POS_X_CHECKBOX, 180));

		this.titleAudio = new BlokusLabel("AUDIO", customFontTitle);
		this.titleAudio.setPosition(new Vector2(POS_X_TITLE, 252));

		this.checkBoxActivateAudio = new BlokusCheckBox(true, this.option.isPlaySong(), "MUSIQUE DE FOND",
				this.customFontCheckbox);
		this.checkBoxActivateAudio.setPosition(new Vector2(POS_X_CHECKBOX, 300));
		this.checkBoxActivateAudio.addListener(this);

		this.titleVideo = new BlokusLabel("VIDEO", customFontTitle);
		this.titleVideo.setPosition(new Vector2(POS_X_TITLE, 402));

		this.checkBoxDaltonienMode = new BlokusCheckBox(true, this.option.isDaltonienMode(), "MODE DALTONIEN",
				this.customFontCheckbox);
		this.checkBoxDaltonienMode.setPosition(new Vector2(POS_X_CHECKBOX, 450));

		this.titleGoal = new BlokusLabel("BUT du jeu : Pour chaque joueur, placer ses 21 pièces sur le plateau",
				customFontTitle);
		this.titleGoal.setPosition(new Vector2(POS_X_TITLE, 102));

		this.titleRunning = new BlokusLabel("DÉROULEMENT", customFontTitle);
		this.titleRunning.setPosition(new Vector2(POS_X_TITLE, 163));

		this.textRunning1 = new BlokusText("L'ordre dans lequel on joue est le suivant: bleu, jaune, rouge, vert.",
				customFontText);
		this.textRunning1.setPosition(new Vector2(POS_X_TITLE, 207));

		String s1 = new StringBuilder()
				.append("1) Au premier tour, chaque joueur place chacun son tour la première pièce de son choix sur le\n")
				.append("plateau de telle sorte que celle-ci recouvre une case d'angle du plateau.").toString();
		this.textRunning2 = new BlokusText(s1, customFontText);
		this.textRunning2.setPosition(new Vector2(POS_X_TITLE, 237));

		String s2 = new StringBuilder()
				.append("2) Pour les tours suivants, chaque nouvelle pièce posée doit toucher une pièce de la même couleur\n")
				.append("par un ou plusieurs coins et jamais par les cotés.").toString();
		this.textRunning3 = new BlokusText(s2, customFontText);
		this.textRunning3.setPosition(new Vector2(POS_X_TITLE, 287));

		String s3 = new StringBuilder()
				.append("3) Les pièces du jeu peuvent être disposées dans n'importe quel sens sur le plateau. Une pièce posée\n")
				.append("sur le plateau reste en place jusqu'à la fin de la partie.").toString();
		this.textRunning4 = new BlokusText(s3, customFontText);
		this.textRunning4.setPosition(new Vector2(POS_X_TITLE, 337));

		this.titleEnd = new BlokusLabel("FIN", customFontTitle);
		this.titleEnd.setPosition(new Vector2(POS_X_TITLE, 393));

		String s4 = new StringBuilder()
				.append("- Lorsqu'un joueur est bloqué et ne peut plus placer de pièce, il est obligé de passer son tour.\n")
				.append("- Lorsque tous les joueurs sont bloqués, le GAGNANT est celui qui a POSÉ LE PLUS DE pièces.")
				.toString();
		this.textEnd = new BlokusText(s4, customFontText);
		this.textEnd.setPosition(new Vector2(POS_X_TITLE, 437));

		this.titleAdvices = new BlokusLabel("CONSEILS", customFontTitle);
		this.titleAdvices.setPosition(new Vector2(POS_X_TITLE, 506));

		String s5 = new StringBuilder()
				.append("- cherchez à progresser vers le centre du plateau, de manière à occuper un maximum de place.\n")
				.append("- Essayez de placer vos grosses pièces sur le plateau en début de la partie.").toString();
		this.textAdvices = new BlokusText(s5, customFontText);
		this.textAdvices.setPosition(new Vector2(POS_X_TITLE, 550));

		this.upDownVolume = new BlokusNumericUpDown(" volume ", this.option.getVolume(), 0.05f, 0.0f, 1.0f, this.customFontCheckbox);
		this.upDownVolume.setPosition(new Vector2(POS_X_CHECKBOX, 330));
		this.upDownVolume.addListener(this);

		// DEBUT PAGE CONTROLES
		this.titleKeyboard = new BlokusLabel("CLAVIER", customFontTitle);
		this.titleKeyboard.setPosition(new Vector2(POS_X_TITLE, 100));

		this.keyRotateClockwiseButton = new BlokusWaitPress(this.onControl, this.option.getKeyRotateClockwise(),
				"Rotation horaire", this.customFontCheckbox);
		this.keyRotateClockwiseButton.setPosition(new Vector2(POS_X_CHECKBOX, 150));

		this.keyRotateCounterClockwiseButton = new BlokusWaitPress(this.onControl,
				this.option.getKeyRotateCounterClockwise(), "Rotation anti-horaire", this.customFontCheckbox);
		this.keyRotateCounterClockwiseButton.setPosition(new Vector2(POS_X_CHECKBOX, 200));

		this.keySymetryClockwiseButton = new BlokusWaitPress(this.onControl, this.option.getKeySymetryClockwise(), "Symétrie",
				this.customFontCheckbox);
		this.keySymetryClockwiseButton.setPosition(new Vector2(POS_X_CHECKBOX, 250));

		this.keySymetryCounterClockwiseButton = new BlokusWaitPress(this.onControl,
				this.option.getKeySymetryCounterClockwise(), "Symétrie", this.customFontCheckbox);
		this.keySymetryCounterClockwiseButton.setPosition(new Vector2(POS_X_CHECKBOX, 300));

		this.keyReturnButton = new BlokusWaitPress(this.onControl, this.option.getKeyReturn(), "Touche retour",
				this.customFontCheckbox);
		this.keyReturnButton.setPosition(new Vector2(POS_X_CHECKBOX, 350));
		
		//this.defaultControlsButton = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "defaut.png");
		//this.defaultControlsButton.setPosition(new Vector2(POS_X_CHECKBOX, 420));
		//this.defaultControlsButton.addListener(this);
		// FIN PAGE CONTROLES
	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub
	}
}
