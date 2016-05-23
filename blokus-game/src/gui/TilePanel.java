package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.CellColor;
import entities.Player;
import entities.Tile;
import utilities.BufferedImageHelper;
import utilities.Vector2;

/**
 * @author Groupe 1
 *
 */
public class TilePanel implements DrawableInterface{
	
	private final static int OFFSET_X_P1_BLUE = 48;
	private final static int OFFSET_Y_P1_BLUE = 91;
	
	private final static int OFFSET_X_P2_YELLOW = 995;
	private final static int OFFSET_Y_P2_YELLOW = 91;
	
	private final static int OFFSET_X_P1_RED = 48;
	private final static int OFFSET_Y_P1_RED = 400;
	
	private final static int OFFSET_X_P2_GREEN = 995;
	private final static int OFFSET_Y_P2_GREEN = 400;
	/**
	 * Etat du panel des pièce
	 */
	private boolean state;
	
	private boolean wasClicked;
	
	/**
	 * La couleur des pièces
	 */
	private CellColor tileColor;
	
	/**
	 * La liste des pièces
	 */
	private ArrayList<BlokusTile> tiles;

	private BufferedImage cellMaskImage;
	
	private Player player;

	private BlokusTile bt;
	
	/**
	 * Constructeur de TilePanel
	 * @param color la couleur des pièces dans le panel
	 */
	public TilePanel(CellColor color, Player p) {
		this.state = false;
		this.tileColor = color;
		
		this.player = p;
		this.tiles = new ArrayList<BlokusTile>();
		this.wasClicked = false;
		
		ArrayList<Tile> listOfTiles = Tile.getListOfNeutralTile(color);
		this.initColor(listOfTiles);
		
		this.cellMaskImage = BufferedImageHelper.generateMask(color.getImage(), Color.BLACK, 0.5f);
	}

	/**
	 * Fonction qui ajoute une pièce dans le panel
	 * @param t la pièce concernée
	 */
	public void addTile(Tile t)
	{
		this.tiles.add(new BlokusTile(t));
	}
	
	/**
	 * Fonction qui retire une pièce du panelbTilesLine<=4)
	 * @param t la pièce concernée
	 */
	public void removeTile(BlokusTile t)
	{
		this.tiles.remove(t);
	}
	
	/**
	 * Fonction qui renvoie la pièce sélectionnée lors du clic grâce à la position v
	 * @param v la position du clic
	 * @return la pièce cliquée
	 */
	public BlokusTile getTile(Vector2 v)
	{
		BlokusTile res = null;
		
		for(BlokusTile entry : this.tiles)
		{
			if(entry.isInBounds(v))
			{
				res = entry;
				break;
			}
		}
		return res;
	}
	
	/**
	 * Fonction qui rend la main au panel joueur
	 */
	public void enable()
	{
		this.state = true;
	}
	
	/**
	 * Fonction qui désactive le panel joueur
	 */
	public void disable()
	{
		this.state = false;
	}
	
	private int basePosCellX = 0;
	private int basePosCellY = 0;
	

	@Override
	public void update(float elapsedTime) {
		
		BlokusTile tileRemoved = null;
		for(BlokusTile entry : this.tiles)
		{
			if(this.wasClicked)
			{
				if(Mouse.isReleased())
				{
					this.wasClicked = false;
					if(entry.isInBounds(Mouse.getPosition()))
					{
						tileRemoved = entry;
					}
				}
			}
			else
			{
				this.wasClicked = true;
			}
			
			entry.update(elapsedTime);
		}
		if(this.wasClicked && Mouse.isReleased())
		{
			this.wasClicked = false;
		}
		this.removeTile(tileRemoved);
	}

	
	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		for(BlokusTile entry : this.tiles)
		{
			entry.draw(g2d);
		}
		g2d.dispose();
	}
	
	public void initColor(ArrayList<Tile> listOfTiles)
	{
		if(this.tileColor == CellColor.BLUE)
		{
			basePosCellX = OFFSET_X_P1_BLUE;
			basePosCellY = OFFSET_Y_P1_BLUE;
		}
		else if(this.tileColor == CellColor.YELLOW)
		{
			basePosCellX = OFFSET_X_P2_YELLOW;
			basePosCellY = OFFSET_Y_P2_YELLOW;
		}
		else if(this.tileColor == CellColor.RED)
		{
			basePosCellX = OFFSET_X_P1_RED;
			basePosCellY = OFFSET_Y_P1_RED; 
		}
		else if(this.tileColor == CellColor.GREEN)
		{
			basePosCellX = OFFSET_X_P2_GREEN;
			basePosCellY = OFFSET_Y_P2_GREEN;
		}
		
		this.tiles.add(new BlokusTile(listOfTiles.get(0), new Vector2(basePosCellX+0,basePosCellY+0)));
		this.tiles.add(new BlokusTile(listOfTiles.get(1), new Vector2(basePosCellX+10,basePosCellY+20)));
		this.tiles.add(new BlokusTile(listOfTiles.get(2), new Vector2(basePosCellX+100,basePosCellY+230)));
		this.tiles.add(new BlokusTile(listOfTiles.get(3), new Vector2(basePosCellX+20,basePosCellY+210)));
		this.tiles.add(new BlokusTile(listOfTiles.get(4), new Vector2(basePosCellX-10,basePosCellY+90)));
		this.tiles.add(new BlokusTile(listOfTiles.get(5), new Vector2(basePosCellX+190,basePosCellY+150)));
		this.tiles.add(new BlokusTile(listOfTiles.get(6), new Vector2(basePosCellX+190,basePosCellY+220)));
		this.tiles.add(new BlokusTile(listOfTiles.get(7), new Vector2(basePosCellX+60,basePosCellY+0)));
		this.tiles.add(new BlokusTile(listOfTiles.get(8), new Vector2(basePosCellX+60,basePosCellY+70)));
		this.tiles.add(new BlokusTile(listOfTiles.get(9), new Vector2(basePosCellX-40,basePosCellY+70))); //-40,70
		this.tiles.add(new BlokusTile(listOfTiles.get(10), new Vector2(basePosCellX+170,basePosCellY+20)));
		this.tiles.add(new BlokusTile(listOfTiles.get(11), new Vector2(basePosCellX+130,basePosCellY+270)));
		this.tiles.add(new BlokusTile(listOfTiles.get(12), new Vector2(basePosCellX+0,basePosCellY+140)));
		this.tiles.add(new BlokusTile(listOfTiles.get(13), new Vector2(basePosCellX+130,basePosCellY-20)));
		this.tiles.add(new BlokusTile(listOfTiles.get(14), new Vector2(basePosCellX+70,basePosCellY+120)));
		this.tiles.add(new BlokusTile(listOfTiles.get(15), new Vector2(basePosCellX+120,basePosCellY+130)));
		this.tiles.add(new BlokusTile(listOfTiles.get(16), new Vector2(basePosCellX+00,basePosCellY+230)));
		this.tiles.add(new BlokusTile(listOfTiles.get(17), new Vector2(basePosCellX+60,basePosCellY+140)));
		this.tiles.add(new BlokusTile(listOfTiles.get(18), new Vector2(basePosCellX+170,basePosCellY+100)));
		this.tiles.add(new BlokusTile(listOfTiles.get(19), new Vector2(basePosCellX+140,basePosCellY+70)));
		this.tiles.add(new BlokusTile(listOfTiles.get(20), new Vector2(basePosCellX+50,basePosCellY+240)));
	}
}
