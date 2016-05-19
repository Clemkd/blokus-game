package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import entities.CellColor;
import entities.CellType;
import entities.Player;
import entities.Tile;
import utilities.BufferedImageHelper;
import utilities.Vector2;

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
	private HashMap<Tile, Vector2<Integer>> tiles;
	
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
	private Vector2<Integer> pos;
	
	private BufferedImage cellMaskImage;
	
	private Player player;
	
	/**
	 * Constructeur de TilePanel
	 * @param color la couleur des pièces dans le panel
	 */
	public TilePanel(CellColor color, int width, int height, Vector2<Integer> pos, Player p) {
		this.state = false;
		//this.tileColor = color;
		this.width = width;
		this.height = height;
		this.pos = pos;
		this.player = p;
		
		ArrayList<Tile> listOfTiles = Tile.getListOfNeutralTile(color);
		for(int i=0; i<listOfTiles.size();i++)
		{
			this.tiles.put(listOfTiles.get(i), new Vector2<Integer>(0,0));
		}
		
		this.cellMaskImage = BufferedImageHelper.generateMask(color.getImage(), Color.BLACK, 0.5f);
	}

	/**
	 * Fonction qui ajoute une pièce dans le panel
	 * @param t la pièce concernée
	 */
	public void addTile(Tile t, Vector2<Integer> v)
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
	public Tile getTile(Vector2<Integer> v)
	{
		Tile res = null;
		
		for(Entry<Tile, Vector2<Integer>> entry : this.tiles.entrySet())
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
	public Vector2<Integer> getPos() {
		return pos;
	}

	/**
	 * Setter de la position du panel
	 * @param pos la position du panel
	 */
	public void setPos(Vector2<Integer> pos) {
		this.pos = pos;
	}

	@Override
	public void update(float elapsedTime) {
		
	}


	
	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Tile t;
		Vector2<Integer> posCell = new Vector2<Integer>(OFFSET_X, OFFSET_Y);
		int nbTilesLine = 1;
		ArrayList<Tile> ap = (ArrayList<Tile>) this.player.getTileInventory();

		
		for(Entry<Tile, Vector2<Integer>> entry : this.tiles.entrySet())
		{
			t = entry.getKey();
			
			if(ap.contains(entry.getKey()))
			{
				for(int i=t.getFirstCase().getX(); i<Tile.WIDTH; i++)
				{
					for(int j=0; j< Tile.HEIGHT; i++)
					{
						if(t.getMatrix()[i][j] == CellType.PIECE)
						{
							g.drawImage(t.getCouleur().getImage(), posCell.getX(), posCell.getY(), CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, null);
	
							if(!this.state)
							{
								g.drawImage(this.cellMaskImage, posCell.getX(), posCell.getY(), CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, null);
							}
						}
						posCell.setY(posCell.getY()+CellColor.CELL_HEIGHT);
					}
					posCell.setX(posCell.getX()+CellColor.CELL_WIDTH);
				}
			}
			nbTilesLine++;
			
			if(nbTilesLine<=4)
			{
				posCell.setX((OFFSET_X*nbTilesLine)+(CellColor.CELL_WIDTH*4));
				posCell.setY(OFFSET_Y);
			}
			else
			{
				posCell.setX(OFFSET_X);
				posCell.setY(OFFSET_Y + (CellColor.CELL_HEIGHT*5));
				nbTilesLine = 1;
			}			
		}
		
		g2d.dispose();
	}
		
}
