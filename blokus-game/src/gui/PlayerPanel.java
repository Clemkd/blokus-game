package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.net.URISyntaxException;

import entities.CellColor;
import entities.Player;
import entities.Tile;
import navigation.OptionPage;
import navigation.Page;
import utilities.BufferedHelper;
import utilities.CSSColors;
import utilities.Vector2;

/**
 * @author Groupe1
 *
 */
public class PlayerPanel implements DrawableInterface{
	
	private static final int OFFSET_NAME_Y = 40;
	private static final int OFFSET_NAME_X = 60;
	// p2 995
	private final static int OFFSET_X_TILE_PANEL = 20;
	private final static int OFFSET_Y_TILE_PANEL = 68;
	
	private final static int DEFAULT_WIDTH = 266;
	private final static int DEFAULT_HEIGHT = 632;
	
	private final static int HEADER_PANEL_WIDTH = 267;
	private final static int HEADER_PANEL_HEIGHT = 49;
	
	/**
	 * Etat du panel joueur
	 */
	private boolean state;
	
	/**
	 * Premier panel fils contenant les pieces du joueur
	 */
	private TilePanel tilePanel1;
	
	/**
	 * Deuxième panel fils contenant les pièces du joueur
	 */
	private TilePanel tilePanel2;
	
	//private BufferedImage headerPanelImage;

	private Player player;
	
	private Vector2 position;

	private Dimension size;
	
	private CellColor headerColor;
	
	
	/**
	 * Constructeur de PlayerPanel
	 * @param t1 le premier panel du joueur
	 * @param t2 le deuxième panel du joueur
	 */
	public PlayerPanel(Player p) {
		this.headerColor = CellColor.GREY;
		this.player = p;
		this.state = false;
		this.size = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.tilePanel1 = new TilePanel();
		this.tilePanel2 = new TilePanel();
		this.setPosition(new Vector2());
		
//		try 
//		{
//			this.headerPanelImage = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_IMAGES + "fondgris.png"));
//		} 
//		catch (IOException e) 
//		{
//			System.err.println(e.getMessage());
//			e.printStackTrace();
//		}
		
		this.refreshTiles();
	}
	
	/**
	 * Met à jour la liste des tuiles
	 */
	public void refreshTiles()
	{
		this.tilePanel1.clear();
		this.tilePanel2.clear();
		for(Tile t : this.player.getTileInventory())
		{
			if(t.getColor() == this.player.getColors().get(0))
			{
				this.tilePanel1.addTile(t);
			}
			else if(t.getColor() == this.player.getColors().get(1))
			{
				this.tilePanel2.addTile(t);
			}
		}
	}

	/**
	 * Determine l'état du panel
	 */
	public void setEnabled(boolean state)
	{
		this.state = state;
	}
	
	/**
	 * Determine l'état des sous panels
	 */
	public void setEnabled(boolean statePanel1, boolean statePanel2)
	{
		this.tilePanel1.setEnabled(statePanel1);
		this.tilePanel2.setEnabled(statePanel2);
		if(statePanel1){
			this.headerColor = this.player.getColors().get(0);
		}else if(statePanel2){
			this.headerColor = this.player.getColors().get(1);
		}else{
			this.headerColor = CellColor.GREY;
		}
	}
	
	/**
	 * Fonction qui retourne le statut du panel du joueur
	 * @return le statut du panel joueur
	 */
	public boolean getState()
	{
		return this.state;
	}
	
	/**
	 * Fonction qui renvoie la pièce sélectionnée lors du clic grâce à la position relative v
	 * @param clickedPosition la position relative du clic dans le panel
	 * @return la pièce cliquée
	 */
	public BlokusTile getTile(Vector2 clickedPosition)
	{
		if(!this.getState())
			return null;
		
		BlokusTile res = tilePanel1.getTile(clickedPosition);
		if(res != null)
		{
			return res;
		}
		return tilePanel2.getTile(clickedPosition);
	}

	@Override
	public void update(float elapsedTime) {
		this.refreshTiles();
		tilePanel1.update(elapsedTime);
		tilePanel2.update(elapsedTime);
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
//		g2d.drawImage(this.headerPanelImage, this.position.getX(), this.position.getY(), null);
		switch(this.headerColor){
			case RED:
				g2d.setColor(CSSColors.DARKRED.color());
				break;
			case BLUE:
				g2d.setColor(CSSColors.DARKBLUE.color());
				break;
			case GREEN:
				g2d.setColor(CSSColors.DARKGREEN.color());
				break;
			case YELLOW:
				g2d.setColor(CSSColors.DARKGOLDENROD.color());
				break;
			default:
				g2d.setColor(new Color(128,128,128));
				break;

		}
		
		g2d.fillRect(this.position.getX(), this.position.getY(), HEADER_PANEL_WIDTH, HEADER_PANEL_HEIGHT);
		g2d.setColor(Color.WHITE);
		Font font = null;
		font = BufferedHelper.getDefaultFont(30f);
		g2d.setFont(font);

		g2d.drawString(this.player.getName(), this.position.getX() + OFFSET_NAME_X, this.position.getY() + OFFSET_NAME_Y);
		g2d.fillRect(this.position.getX(), this.position.getY() + HEADER_PANEL_HEIGHT, (int)this.getSize().getWidth(), (int)this.getSize().getHeight());
		this.tilePanel1.draw(g2d);
		this.tilePanel2.draw(g2d);
		
		g2d.dispose();
		
	}
	/**
	 * Obtient la position du panel
	 * @return La position courante du panel
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
		this.tilePanel1.setPosition(new Vector2(OFFSET_X_TILE_PANEL + this.position.getX(), OFFSET_Y_TILE_PANEL + this.position.getY()));
		this.tilePanel2.setPosition(new Vector2(OFFSET_X_TILE_PANEL + this.position.getX(), this.position.getY() + (int)this.tilePanel1.getSize().getHeight()));
	}

	/**
	 * Obtient la taille du panel
	 * @return La taille du panel
	 */
	public Dimension getSize() {
		return size;
	}
	
	/**
	 * Obtient le joueur associé
	 * @return Le joueur
	 */
	public Player getAssociatedPlayer() {
		return this.player;
	}

	/**
	 * Determine la taille du panel
	 * @param size La nouvelle taille du panel
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}
}
