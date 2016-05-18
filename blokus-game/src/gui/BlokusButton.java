package gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import utilities.Vector2;

public class BlokusButton implements DrawableInterface
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Determine si le bouton a été cliqué
	 */
	private boolean wasClicked;
	/**
	 * Les listeners du bouton
	 */
	private ArrayList<ActionListener> listeners;
	
	/**
	 * L'image de fond du bouton
	 */
	private BufferedImage backgroundImage;
	
	/**
	 * La position absolue du bouton dans son conteneur
	 */
	private Vector2<Integer> position;
	
	/**
	 * La taille du bouton
	 */
	private Dimension size;
	
	public BlokusButton(String file)
	{
		this.wasClicked = false;
		this.position = new Vector2<Integer>(0, 0);
		this.size = new Dimension();
		this.listeners = new ArrayList<ActionListener>();
		try {
			this.backgroundImage = ImageIO.read(new File(file));
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
	public Vector2<Integer> getPosition() {
		return position;
	}

	/**
	 * Définit la position absolue du bouton dans son conteneur
	 * @param position La nouvelle position du bouton
	 */
	public void setPosition(Vector2<Integer> position) {
		this.position = position;
	}
	
	/**
	 * Determine si la position est située sur le bouton
	 * @param position La position à tester
	 * @return Vrai si la position donnée est disposée sur le bouton, Faux dans le cas contraire
	 */
	public boolean isInBounds(Vector2<Integer> position)
	{
		return position.getX() >= this.getPosition().getX() &&
				position.getX() < this.getSize().getWidth() + this.position.getX() &&
				position.getY() >= this.getPosition().getY() &&
				position.getY() < this.getSize().getHeight() + this.position.getY();
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
		if(Mouse.getLastMouseButton() == Mouse.LEFT && !Mouse.isReleased() && !this.wasClicked && this.isInBounds(Mouse.getPosition()))
		{
			this.raiseClickEvent(new ActionEvent(this, 0, null));
			this.wasClicked = true;
		}
		else if(Mouse.isReleased())
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
		}
	}
}
