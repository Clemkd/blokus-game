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
import entities.PlayerHuman;
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
	private static final int POS_Y = 725;
	
	private boolean inDragAndDrop;
	
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
	 * Plateau de jeu
	 */
	private BufferedImage board;
	
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
	
	/**
	 * Constructeur
	 */
	public GamePage() {
		super();
		this.inDragAndDrop = false;
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
				this.inDragAndDrop = this.selectedTile != null;
			}
			else
			{
				this.inDragAndDrop = false;
				this.selectedTile = null;
			}
		}
		
		if(this.selectedTile != null)
		{
			int xMatrix = 0;
			int yMatrix = 0;
			int xMatrix2 = 0;
			int yMatrix2 = 0;
			
			CellType[][] matrix = this.selectedTile.getTile().getMatrix();
			
			xMatrix = this.selectedTile.getTile().getFirstCase().getX();
			yMatrix = this.selectedTile.getTile().getFirstCase().getY();
			
			for(int i = 0; i<Tile.WIDTH; i++)
			{
				for (int j=0; j<Tile.HEIGHT; j++)
				{
					if(matrix[j][i] == CellType.PIECE)
					{
						xMatrix2 = i;
						yMatrix2 = j;
						break;
					}
				}
			}			
			int offsetPosX = (xMatrix-xMatrix2)*CellColor.CELL_WIDTH;
			int offsetPosY = (yMatrix-xMatrix2)*CellColor.CELL_HEIGHT;
			
			if(this.blokusBoard.isInBounds(Mouse.getPosition()))
			{
				
				
				
				int x = (Mouse.getPosition().getX() - this.blokusBoard.getPosition().getX()) / CellColor.CELL_WIDTH;
				int y = (Mouse.getPosition().getY() - this.blokusBoard.getPosition().getY()) / CellColor.CELL_HEIGHT;
				
				
				
				this.selectedTile.setPosition(new Vector2(
						(x * CellColor.CELL_WIDTH) + this.blokusBoard.getPosition().getX()+offsetPosX,
						(y * CellColor.CELL_HEIGHT) + this.blokusBoard.getPosition().getY()+offsetPosY));
				
			}
			else
			{
				//this.selectedTile.setPosition(Mouse.getPosition());
				this.selectedTile.setPosition(new Vector2(Mouse.getPosition().getX()+offsetPosX, Mouse.getPosition().getY()+offsetPosY));
			}
			offsetPosX = 0;
			offsetPosY = 0;
			this.selectedTile.update(elapsedTime);
			/**********************************************/
			/************** Drag and drop *****************/
			/**********************************************/
		}
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D)g.create();
		
		g.drawImage(this.titre,500, 51, null);
		g.drawImage(this.board, this.blokusBoard.getPosition().getX(), this.blokusBoard.getPosition().getY(), null, null);
		this.buttonOption.draw(g);
		this.buttonUndo.draw(g);
		this.buttonRedo.draw(g);
		this.buttonSave.draw(g);
		this.buttonExit.draw(g);
		this.panelJoueur1.draw(g);
		this.panelJoueur2.draw(g);
		this.blokusBoard.draw(g);
		
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
		try {
			this.titre = ImageIO.read(new File(Page.PATH_RESOURCES_IMAGES+"logo.png"));
			this.board = ImageIO.read(new File(Page.PATH_RESOURCES_IMAGES+"plateau.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.selectedTile = null;
		this.game = new Game();
		
		this.buttonOption = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"optionsig.png");
		this.buttonOption.setPosition(new Vector2(1143, POS_Y));
		this.buttonOption.addListener(this);
		
		this.buttonUndo = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"annulerig.png");
		this.buttonUndo.setPosition(new Vector2(526, POS_Y));
		this.buttonUndo.addListener(this);
		
		this.buttonRedo = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"refaireig.png");
		this.buttonRedo.setPosition(new Vector2(647, POS_Y));
		this.buttonRedo.addListener(this);
		
		this.buttonSave = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"sauvegarder.png");
		this.buttonSave.setPosition(new Vector2(980, POS_Y));
		this.buttonSave.addListener(this);
		
		this.buttonExit = new BlokusButton(Page.PATH_RESOURCES_BOUTONS+"accueil.png");
		this.buttonExit.setPosition(new Vector2(32, POS_Y));
		this.buttonExit.addListener(this);
		ArrayList<CellColor> listColorsJ1 = new ArrayList<>();
		listColorsJ1.add(CellColor.BLUE);
		listColorsJ1.add(CellColor.RED);
		
		ArrayList<CellColor> listColorsJ2 = new ArrayList<>();
		listColorsJ2.add(CellColor.YELLOW);
		listColorsJ2.add(CellColor.GREEN);
		
		
		this.panelJoueur1 = new PlayerPanel(new PlayerHuman("Moi", listColorsJ1));
		this.panelJoueur2 = new PlayerPanel(new PlayerHuman("Lui", listColorsJ2));
		
		this.blokusBoard = new BlokusBoard(this.game.getBoard());
		this.blokusBoard.setPosition(new Vector2(Window.WIDTH/2 - this.board.getWidth()/2,212));
		try {
			this.blokusBoard.getBoard().addTile(Tile.getListOfNeutralTile(CellColor.BLUE).get(5), new Vector2());
		} catch (InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void unloadContents() {
		// TODO Auto-generated method stub
		
	}

}
