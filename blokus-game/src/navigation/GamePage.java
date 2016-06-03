package navigation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import entities.CellColor;
import entities.Game;
import entities.Player;
import entities.PlayerHuman;
import entities.PlayerIA;
import entities.PlayerMCIA;
import entities.PlayerMedium;
import entities.PlayerRandom;
import entities.Tile;
import gui.BlokusBoard;
import gui.BlokusButton;
import gui.BlokusMessageBox;
import gui.BlokusTile;
import gui.GraphicsPanel;
import gui.Keyboard;
import gui.Mouse;
import gui.MusicPlayer;
import gui.PlayerPanel;
import gui.Window;
import program.Program;
import utilities.BlokusMessageBoxButtonState;
import utilities.BlokusMessageBoxResult;
import utilities.BufferedHelper;
import utilities.CSSColors;
import utilities.Move;
import utilities.Vector2;

public class GamePage extends Page implements ActionListener {

	/**
	 * Constante pour la position en Y des boutons, permet un alignement correct
	 */
	private static final int	BUTTONS_Y_POSITION	= 700;	// 725

	/**
	 * Flag du Drag&Drop
	 */
	private boolean				inDragAndDrop;

	/**
	 * Image correspondante au drag and drop courant
	 */
	private BlokusTile			selectedTile;

	/**
	 * Bouton représentant le choix option
	 */
	private BlokusButton		buttonOption;

	/**
	 * Bouton représentant l'annulation d'un coup
	 */
	private BlokusButton		buttonUndo;

	/**
	 * Bouton représentant l'action "refaire" d'un coup
	 */
	private BlokusButton		buttonRedo;

	/**
	 * Bouton représentant la sauvegarde
	 */
	private BlokusButton		buttonSave;

	/**
	 * Bouton représentant le choix de quitter le jeux
	 */
	private BlokusButton		buttonExit;

	/**
	 * Logo du jeu
	 */
	private BufferedImage		titre;

	/**
	 * Panel des pièces du joueur 1
	 */
	private PlayerPanel			panelJoueur1;

	/**
	 * Panel des pièces du joueur 2
	 */
	private PlayerPanel			panelJoueur2;

	/**
	 * Le plateau du jeu
	 */
	private BlokusBoard			blokusBoard;

	/**
	 * La partie
	 */
	private Game				game;

	/**
	 * Flag du chargement d'une partie
	 */
	private boolean				flagLoad;

	/**
	 * La position de la case de la pièce selectionnée
	 */
	private Vector2				selectedTileHeldCell;

	/**
	 * La police de caractère
	 */
	private Font				font;

	/**
	 * Le player des effets sonores
	 */
	private MusicPlayer			soundPlayer;

	/**
	 * L'image de chargement
	 */
	private BufferedImage		loading;

	/**
	 * Indice d'affichage du chargement
	 */
	private int					showTickAnimation;

	/**
	 * Le temps sauvegardé
	 */
	private float				elapsedTimeSaved;

	/**
	 * Le temps total
	 */
	private float				elapsedTimeTotal;

	private boolean				messageBoxIsClosed;

	/**
	 * Constructeur
	 */
	public GamePage() {
		super();
		this.font = null;
		this.inDragAndDrop = false;
		this.selectedTile = null;
		this.selectedTileHeldCell = null;
		this.flagLoad = false;
		this.soundPlayer = new MusicPlayer();
	}

	@Override
	public void updatePage(float elapsedTime) {
		GraphicsPanel.newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		
		if (this.game.update()) {
			if (Program.optionConfiguration.isPlaySFX()) {
				this.soundPlayer.changeMusic("effect04");
				this.soundPlayer.setVolume(Program.optionConfiguration.getVolumeSFX());
				this.soundPlayer.playOnce();
			}
		}
		this.blokusBoard.setBoard(this.game.getBoard());

		this.blokusBoard.showValidMoves(this.inDragAndDrop && Program.optionConfiguration.isHelp(),
				this.game.getCurrentColor());
		this.blokusBoard.update(elapsedTime);
		
		if(this.game.getCurrentPlayer() instanceof PlayerHuman) {
			this.buttonOption.setEnabled(true);
			this.buttonRedo.setEnabled(this.game.canRedo());
			this.buttonUndo.setEnabled(this.game.canUndo());
			this.buttonSave.setEnabled(true);
		} else {
			this.buttonOption.setEnabled(false);
			this.buttonUndo.setEnabled(false);
			this.buttonRedo.setEnabled(false);
			this.buttonSave.setEnabled(false);
		}
		this.buttonOption.update(elapsedTime);
		this.buttonUndo.update(elapsedTime);
		this.buttonRedo.update(elapsedTime);
		this.buttonSave.update(elapsedTime);
		this.buttonExit.update(elapsedTime);

		if (this.game.isTerminated() == false) {
			if (this.game.getCurrentPlayer() == this.panelJoueur1.getAssociatedPlayer()) {
				if (this.panelJoueur1.getAssociatedPlayer() instanceof PlayerHuman) {
					this.panelJoueur1.setEnabled(true);
				}
			}
			else if (this.game.getCurrentPlayer() == this.panelJoueur2.getAssociatedPlayer()) {
				if (this.panelJoueur2.getAssociatedPlayer() instanceof PlayerHuman) {
					this.panelJoueur2.setEnabled(true);
				}
			}
		}
		else {
			this.panelJoueur1.setEnabled(false);
			this.panelJoueur2.setEnabled(false);
		}

		this.updatePlayerPanels(elapsedTime);
		this.updateMouse(elapsedTime);

		if (this.game.isTerminated()) {
			if (this.messageBoxIsClosed) {
				int scoreP1 = this.game.getScore(this.game.getPlayers().get(0));
				int scoreP2 = this.game.getScore(this.game.getPlayers().get(1));
				String vict = "";
				if (scoreP1 > scoreP2) {
					vict = this.game.getPlayers().get(0).getName() + " à gagné";
				}
				else {
					vict = this.game.getPlayers().get(1).getName() + " à gagné";
				}
				BlokusMessageBox msgbox = new BlokusMessageBox(null,
						vict + "\nScore : " + scoreP1 + " - " + scoreP2 + "\n\nVoulez vous rejouer ?", this.font,
						BlokusMessageBoxButtonState.YES_OR_NO);
				msgbox.setBackColor(Color.WHITE);
				msgbox.setStrokeColor(CSSColors.DARKGREEN.color());
				msgbox.setStroke(3);
				msgbox.show(this);
			}
		}

		this.elapsedTimeTotal += elapsedTime;
		if (this.elapsedTimeTotal > this.elapsedTimeSaved + 100) {
			this.showTickAnimation = (this.showTickAnimation + 1) % 8;
			this.elapsedTimeSaved = this.elapsedTimeTotal;
		}
	}

	@Override
	public void drawPage(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.drawImage(this.titre, 500, 51, null);
		this.blokusBoard.draw(g2d);
		this.buttonOption.draw(g2d);
		this.buttonUndo.draw(g2d);
		this.buttonRedo.draw(g2d);
		this.buttonSave.draw(g2d);
		this.buttonExit.draw(g2d);
		this.panelJoueur1.draw(g2d);
		this.panelJoueur2.draw(g2d);

		if (this.selectedTile != null) {
			this.selectedTile.draw(g2d);
		}

		if (!(this.game.getPlayers().get(0) instanceof PlayerHuman) && !(this.game.isTerminated())) {
			if (this.game.getCurrentPlayer().getName() == this.game.getPlayers().get(0).getName())
				g2d.drawImage(this.loading, 305, 10, 305 + 48, 10 + 48, 0 + (this.showTickAnimation * 48), 0,
						48 + (this.showTickAnimation * 48), 48, null);
		}
		if (!(this.game.getPlayers().get(1) instanceof PlayerHuman) && !(this.game.isTerminated())) {
			if (this.game.getCurrentPlayer().getName() == this.game.getPlayers().get(1).getName())
				g2d.drawImage(this.loading, 925, 10, 925 + 48, 10 + 48, 0 + (this.showTickAnimation * 48), 0,
						48 + (this.showTickAnimation * 48), 48, null);
		}

		g2d.dispose();
	}

	/**
	 * Gestion de la souris lors du mode drag&drop
	 * 
	 * @param elapsedTime
	 */
	private void processDragAndDrop(float elapsedTime) {
		Vector2 mPos = Mouse.getPosition();

		if (Keyboard.getLastKeyTyped() == Program.optionConfiguration.getKeyReturn()) {
			this.game.getCurrentPlayer().addTileToInventory(this.selectedTile.getTile());

			this.selectedTile = null;
			this.inDragAndDrop = false;
			Keyboard.consumeLastKeyTyped();
			Mouse.consumeLastScroll();
			Mouse.consumeLastMouseButton();
		}

		// Gestion de la rotation de la pièce
		if (Mouse.getLastScrollClicks() != 0
				|| Keyboard.getLastKeyTyped() == Program.optionConfiguration.getKeyRotateClockwise()
				|| Keyboard.getLastKeyTyped() == Program.optionConfiguration.getKeyRotateCounterClockwise()) {
			Vector2 fc = this.selectedTile.getTile().getFirstCase();
			Vector2 fc2;
			Vector2 v = new Vector2();
			if (Mouse.getLastScrollClicks() > 0
					|| Keyboard.getLastKeyTyped() == Program.optionConfiguration.getKeyRotateClockwise()) {
				this.selectedTile.setTile(this.selectedTile.getTile().rotateClockwise());
				fc2 = this.selectedTile.getTile().getFirstCase();
				v.setX(this.selectedTileHeldCell.getY() + fc.getY() - fc2.getX());
				v.setY((Tile.WIDTH - (this.selectedTileHeldCell.getX() + fc.getX()) - 1) - fc2.getY());
				this.selectedTileHeldCell = v;
			}
			else if (Mouse.getLastScrollClicks() < 0
					|| Keyboard.getLastKeyTyped() == Program.optionConfiguration.getKeyRotateCounterClockwise()) {
				this.selectedTile.setTile(this.selectedTile.getTile().rotateCounterClockwise());
				fc2 = this.selectedTile.getTile().getFirstCase();
				v.setX((Tile.WIDTH - (this.selectedTileHeldCell.getY() + fc.getY()) - 1) - fc2.getX());
				v.setY(this.selectedTileHeldCell.getX() + fc.getX() - fc2.getY());
				this.selectedTileHeldCell = v;
			}

			Keyboard.consumeLastKeyTyped();
			Mouse.consumeLastScroll();
		}
		else if ((!Mouse.isReleased() && Mouse.getLastMouseButton() == Mouse.RIGHT)
				|| Keyboard.getLastKeyTyped() == Program.optionConfiguration.getKeySymetryClockwise()
				|| Keyboard.getLastKeyTyped() == Program.optionConfiguration.getKeySymetryCounterClockwise()) // Bouton droit enfoncé, gestion de la symétrie
		{
			Vector2 fc = this.selectedTile.getTile().getFirstCase();
			Vector2 fc2;
			Vector2 v = new Vector2();

			this.selectedTile.setTile(this.selectedTile.getTile().flip());
			fc2 = this.selectedTile.getTile().getFirstCase();
			v.setX((Tile.WIDTH - (this.selectedTileHeldCell.getX() + fc.getX()) - 1) - fc2.getX());
			v.setY(this.selectedTileHeldCell.getY() + fc.getY() - fc2.getY());
			this.selectedTileHeldCell = v;

			Mouse.consumeLastMouseButton();
			Keyboard.consumeLastKeyTyped();
		}

		if (this.inDragAndDrop) // Si on est toujours en drag&drop après le traitement des clics
		{
			this.selectedTile.setEnabled(true);
			// Gestion du déplacement de la pièce avec le curseur
			if (this.blokusBoard.isInBounds(mPos)) // La souris survole le plateau
			{
				GraphicsPanel.newCursor = Program.NOT_ALLOWED_CURSOR;
				this.selectedTile.setEnabled(false);
				int innerX = (mPos.getX() - (this.blokusBoard.getPosition().getX() + BlokusBoard.OFFSET_X))
						% CellColor.CELL_HEIGHT;
				int innerY = (mPos.getY() - (this.blokusBoard.getPosition().getY() + BlokusBoard.OFFSET_Y))
						% CellColor.CELL_HEIGHT;

				this.selectedTile.getPosition()
						.setX(mPos.getX() - (CellColor.CELL_HEIGHT * this.selectedTileHeldCell.getY()) - innerX);
				this.selectedTile.getPosition()
						.setY(mPos.getY() - (CellColor.CELL_HEIGHT * this.selectedTileHeldCell.getX()) - innerY);

				// Tests pour determiner si on peut ou non poser la pièce ici
				int cellX = (mPos.getX() - (this.blokusBoard.getPosition().getX() + BlokusBoard.OFFSET_X))
						/ CellColor.CELL_HEIGHT;
				int cellY = (mPos.getY() - (this.blokusBoard.getPosition().getY() + BlokusBoard.OFFSET_Y))
						/ CellColor.CELL_HEIGHT;
				Vector2 cell = new Vector2(cellX, cellY);
				Vector2 fc = this.selectedTile.getTile().getFirstCase();

				List<Vector2> validDropCells = this.blokusBoard.getBoard()
						.getFreePositions(this.selectedTile.getTile().getColor());
				List<Vector2> extremities = this.selectedTile.getTile().getExtremities();
				Vector2 tileOrigin = null;
				Vector2 position = new Vector2();

				for (Vector2 e : extremities) {
					position.setX(cell.getX() + (e.getY() - this.selectedTileHeldCell.getY()) - fc.getY());
					position.setY(cell.getY() + (e.getX() - this.selectedTileHeldCell.getX()) - fc.getX());
					if (validDropCells.contains(position)) {
						tileOrigin = e;
						break;
					}
				}

				if (tileOrigin != null) {
					if (this.blokusBoard.getBoard().isValidMove(this.selectedTile.getTile(), tileOrigin, position)) {
						GraphicsPanel.newCursor = Program.POINTING_HAND_CURSOR;
						this.selectedTile.setEnabled(true);
						if (!Mouse.isReleased() && Mouse.getLastMouseButton() == Mouse.LEFT) // Bouton souris gauche enfoncé
						{
							((PlayerHuman) this.game.getCurrentPlayer())
									.setChosenMove(new Move(position, this.selectedTile.getTile(), tileOrigin));
							this.selectedTile = null;
							this.inDragAndDrop = false;
							Mouse.consumeLastMouseButton();
						}
					}
				}
			}
			else // La souris survole autre chose
			{
				GraphicsPanel.newCursor = Program.GRABBED_CURSOR;
				this.selectedTile.getPosition().setX(mPos.getX()
						- (CellColor.CELL_HEIGHT * this.selectedTileHeldCell.getY()) - CellColor.CELL_HEIGHT / 2);
				this.selectedTile.getPosition().setY(mPos.getY()
						- (CellColor.CELL_HEIGHT * this.selectedTileHeldCell.getX()) - CellColor.CELL_HEIGHT / 2);

				if (!Mouse.isReleased() && Mouse.getLastMouseButton() == Mouse.LEFT) // Bouton souris gauche enfoncé
				{
					this.game.getCurrentPlayer().addTileToInventory(this.selectedTile.getTile());

					this.selectedTile = null;
					this.inDragAndDrop = false;
					Mouse.consumeLastMouseButton();
				}
			}

			if (this.selectedTile != null)
				this.selectedTile.update(elapsedTime);
		}
	}

	/**
	 * Gestion de la souris en dehors du mode drag&drop
	 * 
	 * @param elapsedTime
	 */
	private void processTileSelection(float elapsedTime) // Gestion des clics
	{
		Vector2 mPos = Mouse.getPosition();

		if (this.game.getCurrentPlayer() == this.panelJoueur1.getAssociatedPlayer()) {
			this.selectedTile = this.panelJoueur1.getTile(mPos);
		}
		else if (this.game.getCurrentPlayer() == this.panelJoueur2.getAssociatedPlayer()) {
			this.selectedTile = this.panelJoueur2.getTile(mPos);
		}

		if (this.selectedTile != null) { // Tile survolé
			GraphicsPanel.newCursor = Program.GRAB_CURSOR;
			if (!Mouse.isReleased() && Mouse.getLastMouseButton() == Mouse.LEFT) // Bouton souris gauche enfoncé
			{
				this.game.getCurrentPlayer().removeTileFromInventory(this.selectedTile.getTile());
				this.selectedTileHeldCell = this.selectedTile.getCellOffset(mPos);
				this.inDragAndDrop = true;
				Mouse.consumeLastMouseButton();
				Keyboard.consumeLastKeyTyped();
				Mouse.consumeLastScroll();
			}
		}

		if (Keyboard.getLastKeyTyped() == Program.optionConfiguration.getKeyReturn()) {
			Keyboard.consumeLastKeyTyped();
			Mouse.consumeLastScroll();
			Mouse.consumeLastMouseButton();
			quitConfirm();
			Keyboard.consumeLastKeyTyped();
			Mouse.consumeLastScroll();
			Mouse.consumeLastMouseButton();
		}
	}

	/**
	 * Gestion de la souris en dehors du mode drag&drop
	 * 
	 * @param elapsedTime
	 */
	private void updateMouse(float elapsedTime) {
		if (this.inDragAndDrop) {
			this.processDragAndDrop(elapsedTime);
		}
		else {
			this.processTileSelection(elapsedTime);
		}
	}

	/**
	 * Gestion des panels joueurs
	 * 
	 * @param elapsedTime
	 */
	private void updatePlayerPanels(float elapsedTime) {
		this.panelJoueur1.update(elapsedTime);
		this.panelJoueur2.update(elapsedTime);

		boolean firstColor = this.game.getCurrentPlayer().getColors().get(0) == this.game.getCurrentColor();
		boolean secondColor = this.game.getCurrentPlayer().getColors().get(1) == this.game.getCurrentColor();

		if (this.game.getCurrentPlayer() == this.panelJoueur1.getAssociatedPlayer()) {
			this.panelJoueur1.setEnabled(firstColor, secondColor);
			this.panelJoueur2.setEnabled(false, false);
		}
		else if (this.game.getCurrentPlayer() == this.panelJoueur2.getAssociatedPlayer()) {
			this.panelJoueur1.setEnabled(false, false);
			this.panelJoueur2.setEnabled(firstColor, secondColor);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof BlokusButton) {
			if (e.getSource().equals(this.buttonOption)) {
				Navigation.previous = this;
				Navigation.NavigateTo(Navigation.optionPage);
			}
			else if (e.getSource().equals(this.buttonUndo)) {
				if (this.game.canUndo()) {
					this.game.undoMove();
					this.panelJoueur1 = new PlayerPanel(this.game.getPlayers().get(0));
					this.panelJoueur1.setPosition(new Vector2(32, 10));
					this.panelJoueur2 = new PlayerPanel(this.game.getPlayers().get(1));
					this.panelJoueur2.setPosition(new Vector2(980, 10));

					this.buttonRedo.setEnabled(this.game.canRedo());
					this.buttonUndo.setEnabled(this.game.canUndo());
				}
			}
			else if (e.getSource().equals(this.buttonRedo)) {
				if (this.game.canRedo()) {
					this.game.redoMove();
					this.panelJoueur1 = new PlayerPanel(this.game.getPlayers().get(0));
					this.panelJoueur1.setPosition(new Vector2(32, 10));
					this.panelJoueur2 = new PlayerPanel(this.game.getPlayers().get(1));
					this.panelJoueur2.setPosition(new Vector2(980, 10));

					this.buttonRedo.setEnabled(this.game.canRedo());
					this.buttonUndo.setEnabled(this.game.canUndo());
				}
			}
			else if (e.getSource().equals(this.buttonSave)) {
				this.game.save();
				// JFileChooser jFileChooser = new JFileChooser();
				// jFileChooser.showSaveDialog(jFileChooser);
			}
			else if (e.getSource().equals(this.buttonExit)) {
				quitConfirm();
			}
		}
		else if (e.getSource() instanceof BlokusMessageBox) {
			if (e.getActionCommand() == BlokusMessageBoxResult.YES.getActionCommand()) {
				if (!this.game.isTerminated() || !this.messageBoxIsClosed) {
					Navigation.NavigateTo(Navigation.homePage);
				}
				else {
					Player p1, p2;
					if (this.game.getPlayers().get(0) instanceof PlayerHuman) {
						p1 = new PlayerHuman(this.game.getPlayers().get(0).getName(),
								this.game.getPlayers().get(0).getColors());
					}
					else if (this.game.getPlayers().get(0) instanceof PlayerRandom) {
						p1 = new PlayerRandom(this.game.getPlayers().get(0).getName(),
								this.game.getPlayers().get(0).getColors());
					}
					else if (this.game.getPlayers().get(0) instanceof PlayerMCIA) {
						p1 = new PlayerMCIA(this.game.getPlayers().get(0).getName(),
								this.game.getPlayers().get(0).getColors());
					}
					else if (this.game.getPlayers().get(0) instanceof PlayerMedium) {
						p1 = new PlayerMedium(this.game.getPlayers().get(0).getName(),
								this.game.getPlayers().get(0).getColors());
					}
					else if (this.game.getPlayers().get(0) instanceof PlayerIA) {
						p1 = new PlayerIA(this.game.getPlayers().get(0).getName(),
								this.game.getPlayers().get(0).getColors());
					}
					else {
						System.err.println("Erreur recréation joueur 1");
						p1 = new PlayerHuman(this.game.getPlayers().get(0).getName(),
								this.game.getPlayers().get(0).getColors());
					}

					if (this.game.getPlayers().get(1) instanceof PlayerHuman) {
						p2 = new PlayerHuman(this.game.getPlayers().get(1).getName(),
								this.game.getPlayers().get(1).getColors());
					}
					else if (this.game.getPlayers().get(1) instanceof PlayerRandom) {
						p2 = new PlayerRandom(this.game.getPlayers().get(1).getName(),
								this.game.getPlayers().get(1).getColors());
					}
					else if (this.game.getPlayers().get(1) instanceof PlayerMCIA) {
						p2 = new PlayerMCIA(this.game.getPlayers().get(1).getName(),
								this.game.getPlayers().get(1).getColors());
					}
					else if (this.game.getPlayers().get(1) instanceof PlayerMedium) {
						p2 = new PlayerMedium(this.game.getPlayers().get(1).getName(),
								this.game.getPlayers().get(1).getColors());
					}
					else if (this.game.getPlayers().get(1) instanceof PlayerIA) {
						p2 = new PlayerIA(this.game.getPlayers().get(1).getName(),
								this.game.getPlayers().get(1).getColors());
					}
					else {
						System.err.println("Erreur recréation joueur 2");
						p2 = new PlayerHuman(this.game.getPlayers().get(1).getName(),
								this.game.getPlayers().get(1).getColors());
					}

					this.setGame(new Game(p1, p2));
					Navigation.NavigateTo(Navigation.gamePage);
				}
			}

			// Fermeture de la message box
			if (this.getMessageBox() != null) {
				this.getMessageBox().close(this);
			}
			if (e.getActionCommand() == BlokusMessageBoxResult.NO.getActionCommand()) {
				if (this.game.isTerminated() && this.messageBoxIsClosed) {
					this.messageBoxIsClosed = false;
				}
			}
		}
	}

	/**
	 * 
	 */
	private void quitConfirm() {
		BlokusMessageBox msgbox = new BlokusMessageBox(null, "Êtes-vous sûr de vouloir retourner à l'accueil ?",
				this.font, BlokusMessageBoxButtonState.YES_OR_NO);
		msgbox.setBackColor(Color.WHITE);
		msgbox.setStrokeColor(Color.ORANGE);
		msgbox.setStroke(3);
		msgbox.show(this);
	}

	/**
	 * Setter de game
	 * 
	 * @param g
	 *            la partie
	 */
	public void setGame(Game g) {
		this.game = g;
		this.inDragAndDrop = false;
		this.selectedTile = null;
		this.selectedTileHeldCell = null;
		this.flagLoad = false;
	}

	@Override
	public void loadContents() {
		if (!flagLoad) {

			try {
				this.font = BufferedHelper.getDefaultFont(20f);
				this.titre = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_IMAGES + "logo.png"));
				this.loading = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_ANIMATION + "loading.png"));
			}
			catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			this.selectedTile = null;

			this.buttonOption = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "optionsig.png"));
			this.buttonOption.setPosition(new Vector2(1143, BUTTONS_Y_POSITION));
			this.buttonOption.addListener(this);

			this.buttonUndo = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "annulerig.png"));
			this.buttonUndo.setPosition(new Vector2(526, BUTTONS_Y_POSITION));
			this.buttonUndo.addListener(this);

			this.buttonRedo = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "refaireig.png"));
			this.buttonRedo.setPosition(new Vector2(647, BUTTONS_Y_POSITION));
			this.buttonRedo.addListener(this);

			this.buttonSave = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "sauvegarder.png"));
			this.buttonSave.setPosition(new Vector2(980, BUTTONS_Y_POSITION));
			this.buttonSave.addListener(this);

			this.buttonExit = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "accueil.png"));
			this.buttonExit.setPosition(new Vector2(32, BUTTONS_Y_POSITION));
			this.buttonExit.addListener(this);

			this.panelJoueur1 = new PlayerPanel(this.game.getPlayers().get(0));
			this.panelJoueur1.setPosition(new Vector2(32, 10));
			this.panelJoueur2 = new PlayerPanel(this.game.getPlayers().get(1));
			this.panelJoueur2.setPosition(new Vector2(980, 10));

			this.blokusBoard = new BlokusBoard(this.game.getBoard());
			this.blokusBoard
					.setPosition(new Vector2(Window.WIDTH / 2 - (int) this.blokusBoard.getSize().getWidth() / 2, 212));

			Window.getMusicPlayer().changeMusic("Basewalk");
			if (Program.optionConfiguration.isPlayMusic()) {
				Window.getMusicPlayer().playContinuously();
				Window.getMusicPlayer().setVolume(Program.optionConfiguration.getVolumeMusic());
			}
			this.flagLoad = true;
			this.messageBoxIsClosed = true;

			this.elapsedTimeSaved = 0;
			this.showTickAnimation = 0;
			this.elapsedTimeTotal = 0;
		}
	}

	@Override
	public void unloadContents() {}
}
