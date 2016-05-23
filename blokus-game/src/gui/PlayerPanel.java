package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.CellColor;
import entities.Player;
import entities.Tile;
import navigation.Page;
import utilities.Vector2;

/**
 * @author Groupe1
 *
 */
public class PlayerPanel implements DrawableInterface{
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
	
	/**
	 * Determine si il y a eu un clic
	 */
	private boolean isClicked;
	
	private static final int TILE_PANEL_WIDTH = 200;
	
	private static final int TILE_PANEL_HEIGHT = 400;
	
	private static final int POS_X = 0;
	
	private static final int POS_Y = 0;
	
	private Player player;
	/**
	 * Constructeur de PlayerPanel
	 * @param t1 le premier panel du joueur
	 * @param t2 le deuxième panel du joueur
	 */
	public PlayerPanel(Player p) {
		this.player = p;
		this.state = false;
		this.tilePanel1 = new TilePanel(CellColor.BLUE, TILE_PANEL_WIDTH, TILE_PANEL_HEIGHT, new Vector2(POS_X, POS_Y), p );
		this.tilePanel2 = new TilePanel(CellColor.RED, TILE_PANEL_WIDTH, TILE_PANEL_HEIGHT, new Vector2(POS_X, POS_Y+TILE_PANEL_HEIGHT), p );;
		this.isClicked = false;
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
	 * Fonction qui retourne le statut du panel du joueur
	 * @return le statut du panel joueur
	 */
	public boolean getState()
	{
		return this.state;
	}
	
	/**
	 * Fonction qui renvoie la pièce sélectionnée lors du clic grâce à la position relative v
	 * @param v la position relative du clic dans le panel
	 * @return la pièce cliquée
	 */
	public Tile getTile(Vector2 v)
	{
		int x = v.getX();
		int y = v.getY();
		Tile res = null;
		if(x> this.tilePanel1.getPos().getX() && x < this.tilePanel1.getPos().getX() + this.tilePanel1.getWidth() && y> this.tilePanel1.getPos().getY() && y<this.tilePanel1.getPos().getY()+this.tilePanel1.getHeight())
		{
			// Je donne la position relative au TilePanel pour essayer de récuperer la pièce
			v.setX(x-this.tilePanel1.getPos().getX());
			v.setY(y-this.tilePanel1.getPos().getY());
			res = this.tilePanel1.getTile(v);
		}
		else if(x> this.tilePanel2.getPos().getX() && x < this.tilePanel2.getPos().getX() + this.tilePanel2.getWidth() && y> this.tilePanel2.getPos().getY() && y<this.tilePanel2.getPos().getY()+this.tilePanel2.getHeight())
		{
			// Je donne la position relative au TilePanel pour essayer de récuperer la pièce
			v.setX(x-this.tilePanel2.getPos().getX());
			v.setY(y-this.tilePanel2.getPos().getY());
			res = this.tilePanel2.getTile(v);
		}
		return res;
	}

	@Override
	public void update(float elapsedTime) {
	
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d= (Graphics2D) g.create();
		g2d.setColor(Color.WHITE);
		BufferedImage  b = null;
		
		try {
			if(player.getColors().get(0) != CellColor.YELLOW)
			{
				b = ImageIO.read(new File(Page.PATH_RESOURCES_IMAGES+"grisJ1.png"));
			}
			else
			{
				b = ImageIO.read(new File(Page.PATH_RESOURCES_IMAGES+"grisJ2.png"));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(this.player.getColors().get(0) != CellColor.YELLOW)
		{
			g2d.drawImage(b, 32, 32, null);
			g2d.fillRect(32, 32+49, 266, 632);
			g2d.dispose();
		}
		else
		{
			g2d.drawImage(b, 980, 32, null);
			g2d.fillRect(980, 32+49, 266, 632);
			g2d.dispose();
		}
		
		tilePanel1.draw(g);
		tilePanel2.draw(g);
		
	}
}
