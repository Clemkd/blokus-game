package navigation;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import entities.CellColor;
import entities.CellType;
import entities.Game;
import entities.Tile;
import gui.BlokusBoard;
import gui.BlokusButton;
import gui.BlokusTile;
import gui.Mouse;
import gui.PlayerPanel;
import gui.Window;
import utilities.InvalidMoveException;
import utilities.Vector2;

public class GamePage extends Page implements ActionListener{

	/**
	 * Constante pour la position en Y des boutons, permet un alignement correct
	 */
	private static final int BUTTONS_Y_POSITION = 725;

	private boolean inDragAndDrop;
	
	private ArrayList<Vector2> validMoves;
	
	private ArrayList<Vector2> cellsOfTile;
	
	private Vector2 mousePosInBoard;

	/**
	 * Image correspondante au drag and drop courant
	 */
	private BlokusTile selectedTile;

	/**
	 *Bouton représentant le choix option
	 */
	private BlokusButton buttonOption;

	/**
	 * Bouton représentant l'annulation d'un coup
	 */
	private BlokusButton buttonUndo;

	/**
	 * Bouton représentant l'action "refaire" d'un coup
	 */
	private BlokusButton buttonRedo;

	/**
	 * Bouton représentant la sauvegarde
	 */
	private BlokusButton buttonSave;

	/**
	 * Bouton représentant le choix de quitter le jeux
	 */
	private BlokusButton buttonExit;

	/**
	 * Logo du jeu
	 */
	private BufferedImage titre;

	/**
	 * Panel des pièces du joueur 1
	 */
	private PlayerPanel panelJoueur1;

	/**
	 * Panel des pièces du joueur 2
	 */
	private PlayerPanel panelJoueur2;

	private BlokusBoard blokusBoard;

	private Game game;

	private boolean flagLoad;

	private Vector2 initialPositionTile;
	
	private Vector2 oldVector;
	
	/**
	 * Constructeur
	 */
	public GamePage() {
		super();
		this.inDragAndDrop = false;
		this.flagLoad = false;
	}


	@Override
	public void update(float elapsedTime) {
		this.buttonOption.update(elapsedTime);
		this.buttonUndo.update(elapsedTime);
		this.buttonRedo.update(elapsedTime);
		this.buttonSave.update(elapsedTime);
		this.buttonExit.update(elapsedTime);
		this.panelJoueur1.update(elapsedTime);
		this.panelJoueur2.update(elapsedTime);
		this.game.update();
		this.blokusBoard.setBoard(this.game.getBoard());
		this.blokusBoard.update(elapsedTime);
		
		/**********************************************/
		/************** Drag and drop *****************/
		/**********************************************/
		if(!Mouse.isReleased() && Mouse.getLastMouseButton() == Mouse.LEFT)
		{
			Mouse.consumeLastMouseButton();
			if(!this.inDragAndDrop)
			{
				BlokusTile tile = this.panelJoueur1.getTile(Mouse.getPosition());
				if(tile != null)
				{
					this.selectedTile = tile;
				}
				else
				{
					this.selectedTile = this.panelJoueur2.getTile(Mouse.getPosition());
				}
				
				if(this.selectedTile != null)
				{
					this.initialPositionTile = selectedTile.getPosition();
				}
				this.inDragAndDrop = this.selectedTile != null;
			}
			else
			{
				if(!this.blokusBoard.isInBounds(Mouse.getPosition()))
				{
					this.selectedTile.setPosition(initialPositionTile);
					this.initialPositionTile = null;
				}
				else
				{
					// TODO tester le placement pour chaque extrémité du Tile en main 
					Vector2 tileOrigin = this.selectedTile.getTile().getExtremities().get(0);
					if(this.blokusBoard.getBoard().isValidMove(this.selectedTile.getTile(), tileOrigin, this.mousePosInBoard))
					{
						for(int k = 0; k<this.validMoves.size();k++)
						{
							for(int l = 0; l<this.cellsOfTile.size(); l++)
							{
								if(this.validMoves.get(k).getX() == this.cellsOfTile.get(l).getX() && this.validMoves.get(k).getY() == this.cellsOfTile.get(l).getY())
								{
									
									try {
										this.blokusBoard.getBoard().addTile(this.selectedTile.getTile(), tileOrigin, this.mousePosInBoard);
									} catch (InvalidMoveException e) {
										e.printStackTrace();
									}
									break;
								}
							}
						}
					}
					this.cellsOfTile = new ArrayList<Vector2>();
				}
				this.inDragAndDrop = false;
				this.selectedTile = null;
				
			}
		}
		if(this.selectedTile != null)
		{
			if(this.blokusBoard.isInBounds(Mouse.getPosition()))
			{
				int x = (Mouse.getPosition().getX() - (this.blokusBoard.getPosition().getX() + BlokusBoard.OFFSET_X)) / CellColor.CELL_WIDTH;
				int y = (Mouse.getPosition().getY() - (this.blokusBoard.getPosition().getY() + BlokusBoard.OFFSET_Y)) / CellColor.CELL_HEIGHT;
				
				if(x!=oldVector.getX()||y!=oldVector.getY())
				{
					oldVector.setX(x);
					oldVector.setY(y);
					
					this.validMoves = this.blokusBoard.getBoard().getFreePositions(this.selectedTile.getTile().getCouleur());
					this.cellsOfTile = new ArrayList<Vector2>();
					
					for(int k =0;k<this.validMoves.size();k++)
					{
						System.out.println(this.validMoves.get(k).toString());
					}

					Vector2 fc = this.selectedTile.getTile().getFirstCase();

					for(int offsetX = 0; offsetX < Tile.WIDTH; offsetX++)
					{
						for(int offsetY = 0; offsetY < Tile.HEIGHT; offsetY++)
						{	
							if(this.selectedTile.getTile().getCellType(offsetX, offsetY) != CellType.BLANK)
							{
								Vector2 v = new Vector2(
										x - fc.getY() + offsetY,
										y - fc.getX() + offsetX);
								this.cellsOfTile.add(v);
								
							}
						}
					}
					this.mousePosInBoard = new Vector2(x,y);
				}
				this.selectedTile.setPosition(new Vector2(
						(x * CellColor.CELL_WIDTH) + (this.blokusBoard.getPosition().getX() + BlokusBoard.OFFSET_X),
						(y * CellColor.CELL_HEIGHT) + (this.blokusBoard.getPosition().getY() + BlokusBoard.OFFSET_Y)));
			}
			else
			{
				this.selectedTile.setPosition(new Vector2(Mouse.getPosition().getX(), Mouse.getPosition().getY()));
			}
			this.selectedTile.update(elapsedTime);
			/**********************************************/
			/************** Drag and drop *****************/
			/**********************************************/
		}
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D)g.create();

		g2d.drawImage(this.titre,500, 51, null);
		this.blokusBoard.draw(g2d);
		this.buttonOption.draw(g2d);
		this.buttonUndo.draw(g2d);
		this.buttonRedo.draw(g2d);
		this.buttonSave.draw(g2d);
		this.buttonExit.draw(g2d);
		this.panelJoueur1.draw(g2d);
		this.panelJoueur2.draw(g2d);
		

		if(this.selectedTile != null)
		{
			this.selectedTile.draw(g2d); 
		}

		g2d.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof BlokusButton){
			if(e.getSource().equals(this.buttonOption)){
				Navigation.previous = this;
				
			}else if(e.getSource().equals(this.buttonUndo)){
				if(this.game.canUndo()){
					this.game.undoMove();
				}
			}else if(e.getSource().equals(this.buttonRedo)){
				if(this.game.canRedo()){
					this.game.redoMove();
				}
			}else if(e.getSource().equals(this.buttonSave)){
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.showSaveDialog(jFileChooser);
			}else if(e.getSource().equals(this.buttonExit)){
				Navigation.NavigateTo(Navigation.homePage);
			}
		}

	}

	@Override
	public void loadContents() {
		if(!flagLoad){
			try {
				this.titre = ImageIO.read(new File(Page.PATH_RESOURCES_IMAGES+"logo.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			this.selectedTile = null;
			this.game = new Game();

			this.buttonOption = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"optionsig.png");
			this.buttonOption.setPosition(new Vector2(1143, BUTTONS_Y_POSITION));
			this.buttonOption.addListener(this);

			this.buttonUndo = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"annulerig.png");
			this.buttonUndo.setPosition(new Vector2(526, BUTTONS_Y_POSITION));
			this.buttonUndo.addListener(this);

			this.buttonRedo = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"refaireig.png");
			this.buttonRedo.setPosition(new Vector2(647, BUTTONS_Y_POSITION));
			this.buttonRedo.addListener(this);

			this.buttonSave = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"sauvegarder.png");
			this.buttonSave.setPosition(new Vector2(980, BUTTONS_Y_POSITION));
			this.buttonSave.addListener(this);

			this.buttonExit = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"accueil.png");
			this.buttonExit.setPosition(new Vector2(32, BUTTONS_Y_POSITION));
			this.buttonExit.addListener(this);

			this.panelJoueur1 = new PlayerPanel(this.game.getPlayers().get(0));
			this.panelJoueur1.setPosition(new Vector2(32, 32));
			this.panelJoueur2 = new PlayerPanel(this.game.getPlayers().get(1));
			this.panelJoueur2.setPosition(new Vector2(980, 32));

			
			this.blokusBoard = new BlokusBoard(this.game.getBoard());
			this.blokusBoard.setPosition(new Vector2(Window.WIDTH / 2 - (int)this.blokusBoard.getSize().getWidth() / 2, 212));
			
			this.oldVector = new Vector2(-1,-1);
			this.cellsOfTile = new ArrayList<Vector2>();
			this.mousePosInBoard = new Vector2(0,0);
			this.validMoves = new ArrayList<Vector2>();
			
			flagLoad = true;
		}
	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub

	}

}