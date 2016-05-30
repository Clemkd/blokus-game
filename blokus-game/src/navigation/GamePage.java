package navigation;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import entities.CellColor;
import entities.Game;
import entities.PlayerHuman;
import entities.Tile;
import gui.BlokusBoard;
import gui.BlokusButton;
import gui.BlokusTile;
import gui.Mouse;
import gui.PlayerPanel;
import gui.Window;
import utilities.Move;
import utilities.Vector2;

public class GamePage extends Page implements ActionListener {

	/**
	 * Constante pour la position en Y des boutons, permet un alignement correct
	 */
	private static final int	BUTTONS_Y_POSITION	= 725;

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

	private BlokusBoard			blokusBoard;

	private Game				game;

	private boolean				flagLoad;

	private Vector2				selectedTileHeldCell;

	/**
	 * Constructeur
	 */
	public GamePage() {
		super();
		this.inDragAndDrop = false;
		this.selectedTile = null;
		this.selectedTileHeldCell = null;
		this.flagLoad = false;
	}

	@Override
	public void update(float elapsedTime) {
		this.buttonOption.update(elapsedTime);
		this.buttonUndo.update(elapsedTime);
		this.buttonRedo.update(elapsedTime);
		this.buttonSave.update(elapsedTime);
		this.buttonExit.update(elapsedTime);
		this.game.update();
		this.blokusBoard.setBoard(this.game.getBoard());
		this.blokusBoard.showValidMoves(this.inDragAndDrop, this.game.getCurrentColor());
		this.blokusBoard.update(elapsedTime);

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

		this.updatePlayerPanels(elapsedTime);
		this.updateMouse(elapsedTime);
	}

	/**
	 * Gestion de la souris lors du mode drag&drop
	 * 
	 * @param elapsedTime
	 */
	private void processDragAndDrop(float elapsedTime) {
		Vector2 mPos = Mouse.getPosition();

		// Gestion des clics
		if (!Mouse.isReleased() && Mouse.getLastMouseButton() == Mouse.LEFT) // Bouton souris gauche enfoncé
		{
			if (!this.blokusBoard.isInBounds(mPos)) // Clic en dehors du plateau, on remet la pièce dans le PlayerPanel
			{
				this.game.getCurrentPlayer().addTileToInventory(this.selectedTile.getTile());

				this.selectedTile = null;
				this.inDragAndDrop = false;
			}
			else // Clic dans le plateau, on tente de poser la pièce
			{
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
						((PlayerHuman) this.game.getCurrentPlayer())
								.setChosenMove(new Move(position, this.selectedTile.getTile(), tileOrigin));
						this.selectedTile = null;
						this.inDragAndDrop = false;
					}
				}
			}

			Mouse.consumeLastMouseButton();
		}
		else if (Mouse.getLastScrollClicks() != 0) // Gestion de la rotation de la pièce
		{
			Vector2 fc = this.selectedTile.getTile().getFirstCase();
			Vector2 fc2;
			Vector2 v = new Vector2();
			if (Mouse.getLastScrollClicks() > 0) {
				this.selectedTile.setTile(this.selectedTile.getTile().rotateClockwise());
				fc2 = this.selectedTile.getTile().getFirstCase();
				v.setX((Tile.WIDTH - (this.selectedTileHeldCell.getY() + fc.getY()) - 1) - fc2.getX());
				v.setY(this.selectedTileHeldCell.getX() + fc.getX() - fc2.getY());
				this.selectedTileHeldCell = v;
			}
			else if (Mouse.getLastScrollClicks() < 0) {
				this.selectedTile.setTile(this.selectedTile.getTile().rotateCounterClockwise());
				fc2 = this.selectedTile.getTile().getFirstCase();
				v.setX(this.selectedTileHeldCell.getY() + fc.getY() - fc2.getX());
				v.setY((Tile.WIDTH - (this.selectedTileHeldCell.getX() + fc.getX()) - 1) - fc2.getY());
				this.selectedTileHeldCell = v;
			}

			Mouse.consumeLastScroll();
		}
		else if (!Mouse.isReleased() && Mouse.getLastMouseButton() == Mouse.RIGHT) // Bouton droit enfoncé, gestion de la symétrie
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
		}

		if (this.inDragAndDrop) // Si on est toujours en drag&drop après le traitement des clics
		{
			// Gestion du déplacement de la pièce avec le curseur
			if (this.blokusBoard.isInBounds(mPos)) // La souris survole le plateau
			{
				int innerX = (mPos.getX() - (this.blokusBoard.getPosition().getX() + BlokusBoard.OFFSET_X))
						% CellColor.CELL_HEIGHT;
				int innerY = (mPos.getY() - (this.blokusBoard.getPosition().getY() + BlokusBoard.OFFSET_Y))
						% CellColor.CELL_HEIGHT;

				this.selectedTile.getPosition()
						.setX(mPos.getX() - (CellColor.CELL_HEIGHT * this.selectedTileHeldCell.getY()) - innerX);
				this.selectedTile.getPosition()
						.setY(mPos.getY() - (CellColor.CELL_HEIGHT * this.selectedTileHeldCell.getX()) - innerY);
			}
			else // La souris survole autre chose
			{
				this.selectedTile.getPosition().setX(mPos.getX()
						- (CellColor.CELL_HEIGHT * this.selectedTileHeldCell.getY()) - CellColor.CELL_HEIGHT / 2);
				this.selectedTile.getPosition().setY(mPos.getY()
						- (CellColor.CELL_HEIGHT * this.selectedTileHeldCell.getX()) - CellColor.CELL_HEIGHT / 2);
			}

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
		if (!Mouse.isReleased() && Mouse.getLastMouseButton() == Mouse.LEFT) // Bouton souris gauche enfoncé
		{
			Vector2 mPos = Mouse.getPosition();

			if (this.game.getCurrentPlayer() == this.panelJoueur1.getAssociatedPlayer()) {
				this.selectedTile = this.panelJoueur1.getTile(mPos);
			}
			else if (this.game.getCurrentPlayer() == this.panelJoueur2.getAssociatedPlayer()) {
				this.selectedTile = this.panelJoueur2.getTile(mPos);
			}

			if (this.selectedTile != null) {
				this.game.getCurrentPlayer().removeTileFromInventory(this.selectedTile.getTile());
				this.selectedTileHeldCell = this.selectedTile.getCellOffset(mPos);
				this.inDragAndDrop = true;
			}

			Mouse.consumeLastMouseButton();
		}
	}

	private void updateMouse(float elapsedTime) {
		if (this.inDragAndDrop) {
			this.processDragAndDrop(elapsedTime);
		}
		else {
			this.processTileSelection(elapsedTime);
		}
	}

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
	public void draw(Graphics2D g) {
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

		g2d.dispose();
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
					this.panelJoueur1.setPosition(new Vector2(32, 32));
					this.panelJoueur2 = new PlayerPanel(this.game.getPlayers().get(1));
					this.panelJoueur2.setPosition(new Vector2(980, 32));
				}
			}
			else if (e.getSource().equals(this.buttonRedo)) {
				if (this.game.canRedo()) {
					this.game.redoMove();
					this.panelJoueur1 = new PlayerPanel(this.game.getPlayers().get(0));
					this.panelJoueur1.setPosition(new Vector2(32, 32));
					this.panelJoueur2 = new PlayerPanel(this.game.getPlayers().get(1));
					this.panelJoueur2.setPosition(new Vector2(980, 32));
				}
			}
			else if (e.getSource().equals(this.buttonSave)) {
				this.game.save();
				// JFileChooser jFileChooser = new JFileChooser();
				// jFileChooser.showSaveDialog(jFileChooser);
			}
			else if (e.getSource().equals(this.buttonExit)) {
				Navigation.NavigateTo(Navigation.homePage);
			}
		}

	}

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
				this.titre = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_IMAGES + "logo.png"));
			}
			catch (IOException e) {
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
			this.panelJoueur1.setPosition(new Vector2(32, 32));
			this.panelJoueur2 = new PlayerPanel(this.game.getPlayers().get(1));
			this.panelJoueur2.setPosition(new Vector2(980, 32));

			this.blokusBoard = new BlokusBoard(this.game.getBoard());
			this.blokusBoard
					.setPosition(new Vector2(Window.WIDTH / 2 - (int) this.blokusBoard.getSize().getWidth() / 2, 212));

			this.flagLoad = true;
		}
	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub

	}
}
