package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import entities.CellColor;
import entities.CellType;
import entities.Player;
import entities.Tile;
import navigation.Page;
import utilities.BufferedImageHelper;
import utilities.Vector2;

/*
id: colonne,ligne
1:	10,10
2:	40,10
3:	130,220
4:	50,200
5:	40,60
6: 	180,180
7: 	220,210
8: 	70,10
9: 	90,60
10:	10,40
11:	200,10
12:	160,260
13:	10,150
14:	120,10
15:	120,90
16:	130,140
17:	10,240
18:	70,150
19:	180,110
20:	190,40
21:	80,230
 */

/**
 * @author Groupe 1
 *
 */
public class TilePanel implements DrawableInterface{
	
	private final static int OFFSET_X = 48;
	private final static int OFFSET_Y = 91;
	/**
	 * Etat du panel des pièce
	 */
	private boolean state;
	
	/**
	 * La couleur des pièces
	 */
	//private Color tileColor;
	
	/**
	 * La liste des pièces
	 */
	private HashMap<Tile, Vector2> tiles;
	
	/**
	 * La longueur du panel
	 */
	private int width;

	/**
	 * La largeur du panel
	 */
	private int height;
	
	/**
	 * Position du panel dans le panel joueur (PlayerPanel)
	 */
	private Vector2 pos;
	
	private BufferedImage cellMaskImage;
	
	private Player player;
	
	/**
	 * Constructeur de TilePanel
	 * @param color la couleur des pièces dans le panel
	 */
	public TilePanel(CellColor color, int width, int height, Vector2 pos, Player p) {
		this.state = false;
		//this.tileColor = color;
		this.width = width;
		this.height = height;
		this.pos = pos;
		this.player = p;
		this.tiles = new HashMap<Tile, Vector2>();
		
		ArrayList<Tile> listOfTiles = Tile.getListOfNeutralTile(color);
		for(int i=0; i<listOfTiles.size();i++)
		{
			this.tiles.put(listOfTiles.get(i), new Vector2(0,0));
		}
		
		this.cellMaskImage = BufferedImageHelper.generateMask(color.getImage(), Color.BLACK, 0.5f);
	}

	/**
	 * Fonction qui ajoute une pièce dans le panel
	 * @param t la pièce concernée
	 */
	public void addTile(Tile t, Vector2 v)
	{
		this.tiles.put(t, v);
	}
	
	/**
	 * Fonction qui retire une pièce du panelbTilesLine<=4)
	 * @param t la pièce concernée
	 */
	public void removeTile(Tile t)
	{
		this.tiles.remove(t);
	}
	
	/**
	 * Fonction qui renvoie la pièce sélectionnée lors du clic grâce à la position v
	 * @param v la position du clic
	 * @return la pièce cliquée
	 */
	public Tile getTile(Vector2 v)
	{
		Tile res = null;
		
		for(Entry<Tile, Vector2> entry : this.tiles.entrySet())
		{
			if(entry.getKey().isInBounds(v, entry.getValue()))
			{
				res = entry.getKey();
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
	
	/**
	 * Getter de la longueur
	 * @return la longueur
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Settter de la longueur
	 * @param width la longueur
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Getter de la largeur
	 * @return la largeur
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Setter de la largeur
	 * @param height la largeur
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Getter de la position du panel
	 * @return la position du panel
	 */
	public Vector2 getPos() {
		return pos;
	}

	/**
	 * Setter de la position du panel
	 * @param pos la position du panel
	 */
	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

	@Override
	public void update(float elapsedTime) {
		
	}

	private boolean aze = true;
	
	@Override
	public void draw(Graphics2D g) {
		if(aze)
		{
			aze = false;
		Graphics2D g2d = (Graphics2D) g.create();
		
		Tile t;
		Vector2 posCell = new Vector2(OFFSET_X, OFFSET_Y);
		int nbTilesLine = 1;
		ArrayList<Tile> ap = (ArrayList<Tile>) this.player.getTileInventory();
		boolean firstMeet = false;
		
		for(Entry<Tile, Vector2> entry : this.tiles.entrySet())
		{
			t = entry.getKey();
			
			//if(ap.contains(entry.getKey()))
			//{
				for(int i=0; i<Tile.WIDTH; i++)
				{
					for(int j=0; j< Tile.HEIGHT; j++)
					{
						System.out.println(t.getMatrix());
						if(t.getMatrix()[i][j] == CellType.PIECE)
						{
							firstMeet = true;
							System.out.println("Y = "+posCell.getY()+" X = "+posCell.getX());
							g2d.drawImage(t.getCouleur().getImage(), posCell.getX(), posCell.getY(), CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, null);
	
							if(!this.state)
							{
								g2d.drawImage(this.cellMaskImage, posCell.getX(), posCell.getY(), CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, null);
							}
						}
						if(firstMeet)
						{
							
							posCell.setY(posCell.getY()+CellColor.CELL_HEIGHT);
							
						}
					}
					if(firstMeet)
					{	

						posCell.setX(posCell.getX()+CellColor.CELL_WIDTH);
					}
					
				}
				firstMeet = false;
			//}
			nbTilesLine++;
			
			if(nbTilesLine<=4)
			{
				posCell.setX((OFFSET_X*nbTilesLine)+(CellColor.CELL_WIDTH*3));
				posCell.setY(OFFSET_Y);
			}
			else
			{
				posCell.setX(OFFSET_X);
				posCell.setY(OFFSET_Y + (CellColor.CELL_HEIGHT*4));
				nbTilesLine = 1;
			}			
		}
		g2d.dispose();
		}
	}
		
}
