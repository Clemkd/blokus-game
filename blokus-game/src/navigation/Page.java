package navigation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;

import gui.BlokusMessageBox;
import gui.DrawableInterface;
import gui.Window;

public abstract class Page implements DrawableInterface, ActionListener {
	
	/**
	 * Constante d'accès au dossier de ressource des images
	 */
	public static final String PATH_RESOURCES_IMAGES = "/images/";
	
	/**
	 * Constante d'accès au dossier de resources des boutons
	 */
	public static final String PATH_RESOURCES_BOUTONS = "/boutons/";
	
	/**
	 * Constante d'accès au dossier de resources des fonts
	 */
	public static final String PATH_RESOURCES_FONTS = "/fonts/";
	
	protected static BlokusMessageBox MESSAGEBOX = null;
	
	protected boolean enabled;
	
	public Page()
	{
		this.enabled = true;
	}
	
	/**
	 * Méthode de chargement du contenu de l'état de jeu
	 */
	public abstract void loadContents();
	
	/**
	 * Méthode de déchargement du contenu de l'état de jeu
	 */
	public abstract void unloadContents();
	
	public abstract void updatePage(float elapsedTime);
	
	public abstract void drawPage(Graphics2D g);
	
	public final void update(float elapsedTime)
	{
		if(this.enabled)
		{
			this.updatePage(elapsedTime);
		}
		else
		{
			if(MESSAGEBOX != null && MESSAGEBOX.isShown())
			{
				MESSAGEBOX.update(elapsedTime);
			}
		}
	}
	
	public final void draw(Graphics2D g)
	{
		this.drawPage(g);
		
		if(!this.enabled)
		{
			g.setPaint(new Color(0, 0, 0, 120));
			g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
			
			if(MESSAGEBOX != null && MESSAGEBOX.isShown())
			{
				MESSAGEBOX.draw(g);
			}
		}
	}
	
	public void setEnabled(boolean state)
	{
		this.enabled = state;
	}
	
	protected void setMessageBox(BlokusMessageBox msgbox)
	{
		MESSAGEBOX = msgbox;
		MESSAGEBOX.addListener(this);
	}
	
	protected BlokusMessageBox getMessageBox()
	{
		return MESSAGEBOX;
	}
}
