package gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.CellColor;
import entities.Tile;
import utilities.BufferedImageHelper;
import utilities.Vector2;

/**
 * @author Groupe 1
 *
 */
public class TilePanel implements DrawableInterface{
	
	/**
	 * La liste des positions relatives
	 */
	private static Vector2[] tilesPosition =
	{
		new Vector2(0,0),
		new Vector2(10,20),
		new Vector2(100,230),
		new Vector2(20,210),
		new Vector2(-10,90),
		new Vector2(190,150),
		new Vector2(190,220),
		new Vector2(60,0),
		new Vector2(60,70),
		new Vector2(-40,70),
		new Vector2(170,20),
		new Vector2(130,270),
		new Vector2(0,140),
		new Vector2(130,-20),
		new Vector2(70,120),
		new Vector2(120,130),
		new Vector2(0,230),
		new Vector2(60,140),
		new Vector2(170,100),
		new Vector2(140,70),
		new Vector2(50,240)
	};
	
	private final static int TILE_PANEL_WIDTH = 267;
	private final static int TILE_PANEL_HEIGHT = 380;
	
	/**
	 * La position courante du panel
	 */
	private Vector2 position;
	
	/**
	 * La taille du panel
	 */
	private Dimension size;

	/**
	 * Etat du panel des pièce
	 */
	private boolean state;
	
	/**
	 * La liste des pièces
	 */
	private BlokusTile[] tiles;

	@SuppressWarnings("unused")
	private BufferedImage cellMaskImage;
	
	/**
	 * Constructeur de TilePanel
	 * @param color la couleur des pièces dans le panel
	 */
	public TilePanel() 
	{
		this.setState(false);
		this.position = new Vector2();
		this.tiles = new BlokusTile[Tile.MAX_COUNT];
		this.size = new Dimension(TILE_PANEL_WIDTH, TILE_PANEL_HEIGHT);
		
		this.cellMaskImage = BufferedImageHelper.generateSampleMask(CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, 0.5f);
	}
	
	/**
	 * Vide le panel de toutes ses tuiles
	 */
	public void clear()
	{
		for(int i = 0; i < this.tiles.length; i++)
		{
			this.tiles[i] = null;
		}
	}
	
	/**
	 * Fonction qui ajoute une pièce dans le panel
	 * @param t la pièce concernée
	 */
	public void addTile(Tile t)
	{
		Vector2 tilePosition = tilesPosition[t.getId()];
		Vector2 absoluteTilePosition = new Vector2(tilePosition.getX() + this.position.getX(), tilePosition.getY() + this.position.getY());
		this.tiles[t.getId()] = new BlokusTile(t, absoluteTilePosition);
	}
	
	/**
	 * Charge la liste des tiles d'un inventaire de joueur dans le panel
	 * @param tiles La liste des tiles de l'inventaire du joueur
	 */
	public void loadTileInventory(ArrayList<Tile> tiles)
	{
		for(Tile t : tiles)
		{
			this.addTile(t);
		}
	}
	
	/**
	 * Supprime la tuile spécifiée si existante
	 * @param t La pièce spécifiée
	 */
	public void removeTile(BlokusTile t)
	{
		int index = -1;
		for(int i = 0; i < this.tiles.length; i++)
		{
			if(this.tiles[i] == t)
			{
				this.tiles[index] = null;
				return;
			}
		}
	}
	
	/**
	 * Fonction qui renvoie la pièce sélectionnée lors du clic grâce à la position v
	 * @param v la position du clic
	 * @return la pièce cliquée
	 */
	public BlokusTile getTile(Vector2 v)
	{
		if(!this.isInBounds(v))
			return null;
		
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
	 * Determine l'état du panel
	 */
	public void setEnabled(boolean state)
	{
		this.setState(state);
	}
	

	@Override
	public void update(float elapsedTime) 
	{	
		if(this.getState())
		{
			for(int i = 0; i < this.tiles.length; i++)
			{
				if(tiles[i] != null)
				{
					tiles[i].update(elapsedTime);
				}
			}
		}
	}

	
	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		for(int i = 0; i < this.tiles.length; i++)
		{
			if(tiles[i] != null)
				tiles[i].draw(g2d);
		}
		g2d.dispose();
	}
	
	/**
	 * Determine si la position est située sur le panel
	 * @param position La position à tester
	 * @return Vrai si la position donnée est disposée sur le panel, Faux dans le cas contraire
	 */
	public boolean isInBounds(Vector2 p)
	{
		return p.getX() >= this.position.getX() &&
				p.getX() < this.getSize().getWidth() + this.position.getX() &&
				p.getY() >= this.position.getY() &&
				p.getY() < this.getSize().getHeight() + this.position.getY();
	}
	
	/**
	 * Obtient la position du panel
	 * @return La position du panel
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Determine la position du panel
	 * @param position La nouvelle position du panel
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
		this.refreshTilesPositions();
	}
	
	/**
	 * Obtient la taille du panel
	 * @return La taille du panel
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * Determine la taille du panel
	 * @param size La nouvelle taille du panel
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}
	
	/**
	 * Obtient l'état du panel
	 * @return L'état courant du panel
	 */
	public boolean getState() {
		return state;
	}

	/**
	 * Determine l'état du panel
	 * @param state Le nouvel état du panel
	 */
	public void setState(boolean state) {
		this.state = state;
	}
	
	/**
	 * Met à jour la position des tuiles du panel
	 */
	private void refreshTilesPositions()
	{
		for(int i = 0; i < this.tiles.length; i++)
		{
			if(tiles[i] != null)
			{
				tiles[i].setPosition(new Vector2(
						tilesPosition[i].getX() + this.position.getX(),
						tilesPosition[i].getY() + this.position.getY()));
			}
		}
	}
}
