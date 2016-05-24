package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.CellColor;
import entities.Player;
import navigation.Page;
import utilities.Vector2;

/**
 * @author Groupe1
 *
 */
public class PlayerPanel implements DrawableInterface{
	
	private final static int OFFSET_X_P1_BLUE = 48;
	private final static int OFFSET_Y_P1_BLUE = 91;
	
	private final static int OFFSET_X_P2_YELLOW = 995;
	private final static int OFFSET_Y_P2_YELLOW = 91;
	
	private final static int OFFSET_X_P1_RED = 48;
	private final static int OFFSET_Y_P1_RED = 400;
	
	private final static int OFFSET_X_P2_GREEN = 995;
	private final static int OFFSET_Y_P2_GREEN = 400;
	
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
	
	private BufferedImage b;

	private Player player;
	/**
	 * Constructeur de PlayerPanel
	 * @param t1 le premier panel du joueur
	 * @param t2 le deuxième panel du joueur
	 */
	public PlayerPanel(Player p) {
		this.player = p;
		this.state = false;
		if(player.getColors().get(0) != CellColor.YELLOW)
		{
			this.tilePanel1 = new TilePanel(CellColor.BLUE);
			this.tilePanel1.setPosition(new Vector2(OFFSET_X_P1_BLUE, OFFSET_Y_P1_BLUE));
			this.tilePanel1.setSize(new Dimension(267, 652));
			this.tilePanel2 = new TilePanel(CellColor.RED);
			this.tilePanel2.setPosition(new Vector2(OFFSET_X_P1_RED, OFFSET_Y_P1_RED));
			this.tilePanel1.setSize(new Dimension(267, 652));
		}
		else
		{
			this.tilePanel1 = new TilePanel(CellColor.YELLOW);
			this.tilePanel2.setPosition(new Vector2(OFFSET_X_P2_YELLOW, OFFSET_Y_P2_YELLOW));
			this.tilePanel1.setSize(new Dimension(267, 652));
			this.tilePanel2 = new TilePanel(CellColor.GREEN);
			this.tilePanel2.setPosition(new Vector2(OFFSET_X_P2_GREEN, OFFSET_Y_P2_GREEN));
			this.tilePanel1.setSize(new Dimension(267, 652));
		}
		b = null;
		
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
	}

	/**
	 * Determine l'état du panel
	 */
	public void setEnabled(boolean state)
	{
		this.state = state;
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
		BlokusTile res = tilePanel1.getTile(clickedPosition);
		if(res != null)
		{
			return res;
		}
		return tilePanel2.getTile(clickedPosition);
	}

	@Override
	public void update(float elapsedTime) {
		tilePanel1.update(elapsedTime);
		tilePanel2.update(elapsedTime);
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d= (Graphics2D) g.create();
		g2d.setColor(Color.BLACK);
		
		if(this.player.getColors().get(0) != CellColor.YELLOW)
		{
			g2d.drawImage(this.b, 32, 32, null);
			g2d.fillRect(32, 32+49, 266, 632);
			g2d.dispose();
		}
		else
		{
			g2d.drawImage(this.b, 980, 32, null);
			g2d.fillRect(980, 32+49, 266, 632);
			g2d.dispose();
		}
		
		tilePanel1.draw(g);
		tilePanel2.draw(g);
		
	}
}
