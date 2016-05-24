package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import utilities.BufferedHelper;
import utilities.Vector2;

public class BlokusButton implements DrawableInterface
{
	/**
	 * Determine si le bouton est activé
	 */
	private boolean isEnabled;
	/**
	 * Determine si le bouton a été cliqué
	 */
	private boolean wasClicked;
	
	/**
	 * Determine si le curseur est disposé sur le bouton
	 */
	private boolean mouseHover;
	/**
	 * Les listeners du bouton
	 */
	private ArrayList<ActionListener> listeners;
	
	/**
	 * L'image de fond du bouton
	 */
	private BufferedImage backgroundImage;
	
	/**
	 * Le mask du fond du bouton lorsque le curseur est dispositionné dessus
	 */
	private BufferedImage backgroundImageHover;
	
	/**
	 * Le mask du fond du bouton lorsque celui est désactivé
	 */
	private BufferedImage backgroundImageDisable;
	/**
	 * La position absolue du bouton dans son conteneur
	 */
	private Vector2 position;
	
	/**
	 * La taille du bouton
	 */
	private Dimension size;
	
	public BlokusButton(String file)
	{
		this.mouseHover = false;
		this.wasClicked = false;
		this.position = new Vector2();
		this.size = new Dimension();
		this.isEnabled = true;
		this.listeners = new ArrayList<ActionListener>();
		try {
			this.backgroundImage = ImageIO.read(new File(file));
			this.backgroundImageHover = BufferedHelper.generateMask(this.backgroundImage, Color.BLACK, 0.5f);
			this.backgroundImageDisable = BufferedHelper.generateMask(this.backgroundImage, Color.GRAY, 0.8f);
			this.size = new Dimension(this.backgroundImage.getWidth(), this.backgroundImage.getHeight());
		} catch (IOException e) {
			System.err.println("Impossible de charger l'image " + file + " pour le bouton\nMessage : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Obtient la taille du bouton
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * Définit la taille du bouton
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}

	/**
	 * Obtient la position absolue du bouton dans son conteneur
	 * @return La position du bouton
	 */
	public Vector2 getPosition() {
		return this.position;
	}

	/**
	 * Définit la position absolue du bouton dans son conteneur
	 * @param position La nouvelle position du bouton
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	/**
	 * Obtient l'état courant du bouton
	 * @return L'état courant du bouton (Activé / désactivé)
	 */
	public boolean isEnabled() {
		return this.isEnabled;
	}

	/**
	 * Définit l'activation du bouton
	 * @param state Le nouvel état du bouton (Activé / désactivé)
	 */
	public void setEnabled(boolean state) {
		this.isEnabled = state;
	}
	
	/**
	 * Determine si la position est située sur le bouton
	 * @param position La position à tester
	 * @return Vrai si la position donnée est disposée sur le bouton, Faux dans le cas contraire
	 */
	public boolean isInBounds(Vector2 p)
	{
		return p.getX() >= this.position.getX() &&
				p.getX() < this.getSize().getWidth() + this.position.getX() &&
				p.getY() >= this.position.getY() &&
				p.getY() < this.getSize().getHeight() + this.position.getY();
	}
	
	/**
	 * Ajoute un listener pour l'évènement de clique sur le bouton
	 * @param listener Le listener d'évènement
	 */
	public void addListener(ActionListener listener)
	{
		this.listeners.add(listener);
	}
	
	/**
	 * Lance l'évènement de clique bouton auprès de tous ses listeners
	 * @param e Les information sur l'évènement
	 */
	public void raiseClickEvent(ActionEvent e)
	{
		for(ActionListener listener : this.listeners)
		{
			listener.actionPerformed(e);
		}
	}

	@Override
	public void update(float elapsedTime) 
	{
		if(this.isInBounds(Mouse.getPosition()) && this.isEnabled)
		{
			this.mouseHover = true;
			if(Mouse.getLastMouseButton() == Mouse.LEFT)
			{
				if(this.wasClicked) {
					if(Mouse.isReleased()) {
						this.raiseClickEvent(new ActionEvent(this, 0, null));
						this.wasClicked = false;
					}
				}
				else {
					this.wasClicked = true;
				}
			}
		}
		else
		{
			this.mouseHover = false;
			this.wasClicked = false;
		}
		
		if(this.wasClicked && Mouse.isReleased())
		{
			this.wasClicked = false;
		}
	}

	@Override
	public void draw(Graphics2D g) 
	{
		
		if(this.backgroundImage != null)
		{
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawImage(this.backgroundImage,
					this.position.getX(),
					this.position.getY(), 
					(int)this.size.getWidth(),
					(int)this.size.getHeight(),
					null);
			
			if(this.mouseHover)
			{
				g2d.drawImage(this.backgroundImageHover,
						this.position.getX(),
						this.position.getY(), 
						(int)this.size.getWidth(),
					 	(int)this.size.getHeight(),
						null);
			}
			else if(!this.isEnabled)
			{
				g2d.drawImage(this.backgroundImageDisable,
						this.position.getX(),
						this.position.getY(), 
						(int)this.size.getWidth(),
						(int)this.size.getHeight(),
						null);
			}
			
			g2d.dispose();
		}
	}
}
