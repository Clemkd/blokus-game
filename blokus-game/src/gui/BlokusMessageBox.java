package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import navigation.Page;
import utilities.BlokusMessageBoxButtonState;
import utilities.BlokusMessageBoxResult;
import utilities.Vector2;

public class BlokusMessageBox implements DrawableInterface, ActionListener {
	
	/**
	 * Taille par défaut de la messageBox
	 */
	private final static Dimension DEFAULT_SIZE = new Dimension(Window.WIDTH, 300);
	
	/**
	 * Position par défaut de la messageBox
	 */
	private final static Vector2 DEFAULT_POSITION = new Vector2(0, (int)((Window.HEIGHT / 2) - (DEFAULT_SIZE.getHeight() / 2)));
	
	/**
	 * Constante qui défini la marge en X
	 */
	private final static int DEFAULT_MARGIN_X = 10;

	/**
	 * Constante de trait
	 */
	private static final int DEFAULT_STROKE = 1;
	
	/**
	 * Couleur du trait par défaut
	 */
	private static final Color DEFAULT_STROKE_COLOR = Color.GRAY;
	
	/**
	 * Couleur du fond par défaut
	 */
	private static final Color DEFAULT_BACK_COLOR = Color.WHITE;
	
	/**
	 * Enum qui défini le status du MessageBox
	 */
	private BlokusMessageBoxButtonState buttonState;
	
	/**
	 * La chaine qui va s'afficher
	 */
	private String message;
	
	/**
	 * La liste des boutons
	 */
	private List<BlokusButton> buttons;
	
	/**
	 * La position
	 */
	private Vector2 position;
	
	/**
	 * 
	 */
	private Dimension size;
	
	/**
	 * Visibilité
	 */
	private boolean visible;
	
	/**
	 * La police de caractère
	 */
	private Font font;
	
	/**
	 * La liste des listener
	 */
	private List<ActionListener> listeners;
	
	/**
	 * Le trait
	 */
	private int stroke;
	
	/**
	 * La couleur du trait
	 */
	private Color strokeColor;
	
	/**
	 * La coleur du fond
	 */
	private Color backColor;
	
	/**
	 * Image de fond
	 */
	private BufferedImage image;
	
	/**
	 * Constructeur du MessageBox
	 * 
	 * @param image image
	 * @param message texte a afficher
	 * @param font la police de caractère
	 * @param state état
	 */
	public BlokusMessageBox(BufferedImage image, String message, Font font, BlokusMessageBoxButtonState state)
	{
		this.stroke = DEFAULT_STROKE;
		this.setStrokeColor(DEFAULT_STROKE_COLOR);
		this.setBackColor(DEFAULT_BACK_COLOR);
		this.position = DEFAULT_POSITION;
		this.size = DEFAULT_SIZE;
		this.image = image;
		this.message = message;
		this.font = font;
		this.buttonState = state;
		this.buttons = new ArrayList<BlokusButton>();
		this.visible = false;
		this.listeners = new ArrayList<ActionListener>();
		
		this.initialize();
	}
	
	/**
	 * Méthode d'initialisation
	 */
	private void initialize()
	{
		this.buttons.clear();
		
		if(this.buttonState == BlokusMessageBoxButtonState.VALID)
		{
			BlokusButton buttonValid = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "validbox.png"));
			buttonValid.setActionCommand(BlokusMessageBoxResult.VALID.getActionCommand());
			buttonValid.setPosition(new Vector2(2 * Window.WIDTH / 3, (int) (DEFAULT_POSITION.getY() + DEFAULT_SIZE.getHeight() - buttonValid.getSize().getHeight() * 2)));
			buttonValid.addListener(this);
			this.buttons.add(buttonValid); // Bouton VALIDER
			
		}
		else if(this.buttonState == BlokusMessageBoxButtonState.VALID_OR_CANCEL)
		{
			BlokusButton buttonValid = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "validbox.png"));
			buttonValid.setActionCommand(BlokusMessageBoxResult.VALID.getActionCommand());
			buttonValid.setPosition(new Vector2((int) ((2 * Window.WIDTH / 3) - buttonValid.getSize().getWidth() - DEFAULT_MARGIN_X),
					(int) (DEFAULT_POSITION.getY() + DEFAULT_SIZE.getHeight() - buttonValid.getSize().getHeight() * 2)));
			buttonValid.addListener(this);
			this.buttons.add(buttonValid); // Bouton VALIDER
			
			BlokusButton buttonCancel = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "cancelbox.png"));
			buttonCancel.setActionCommand(BlokusMessageBoxResult.CANCEL.getActionCommand());
			buttonCancel.setPosition(new Vector2(2 * Window.WIDTH / 3,
					(int) (DEFAULT_POSITION.getY() + DEFAULT_SIZE.getHeight() - buttonCancel.getSize().getHeight() * 2)));
			buttonCancel.addListener(this);
			this.buttons.add(buttonCancel); // Bouton ANNULER
		}
		else if(this.buttonState == BlokusMessageBoxButtonState.YES_OR_NO)
		{
			BlokusButton buttonYes = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "yesbox.png"));
			buttonYes.setActionCommand(BlokusMessageBoxResult.YES.getActionCommand());
			buttonYes.setPosition(new Vector2((int) ((2 * Window.WIDTH / 3) - buttonYes.getSize().getWidth() - DEFAULT_MARGIN_X),
					(int) (DEFAULT_POSITION.getY() + DEFAULT_SIZE.getHeight() - buttonYes.getSize().getHeight() * 2)));
			buttonYes.addListener(this);
			this.buttons.add(buttonYes); // Bouton OUI
			
			BlokusButton buttonNo = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "nobox.png"));
			buttonNo.setActionCommand(BlokusMessageBoxResult.NO.getActionCommand());
			buttonNo.setPosition(new Vector2(2 * Window.WIDTH / 3,
					(int) (DEFAULT_POSITION.getY() + DEFAULT_SIZE.getHeight() - buttonNo.getSize().getHeight() * 2)));
			buttonNo.addListener(this);
			this.buttons.add(buttonNo); // Bouton NON
		}
	}
	
	/**
	 * Méthode d'ajout dans un listener
	 * 
	 * @param listener le listener
	 */
	public void addListener(ActionListener listener)
	{
		this.listeners.add(listener);
	}
	
	/**
	 * Méthode qui enleve un listener
	 * 
	 * @param listener le listener à retirer
	 */
	public void removeListener(ActionListener listener)
	{
		this.listeners.remove(listener);
	}
	
	/**
	 * Permet de lever un évènement
	 * 
	 * @param e l'action event
	 */
	private void raiseActionEvent(ActionEvent e)
	{
		for(ActionListener listener : this.listeners)
			listener.actionPerformed(e);
	}
	
	/**
	 * Permet d'afficher le message box
	 * 
	 * @param parent la page sur laquelle doit s'afficher le message box
	 */
	public void show(Page parent)
	{
		this.visible = true;
		this.addListener(parent);
		parent.setMessageBox(this);
		parent.setEnabled(false);
	}
	
	/**
	 * Permet de fermer le message box
	 * 
	 * @param parent la page sur laquelle doit s'afficher le message box
	 */
	public void close(Page parent)
	{
		this.visible = false;
		this.removeListener(parent);
		parent.setEnabled(true);
	}

	@Override
	public void update(float elapsedTime) {
		if(this.visible)
		{
			GraphicsPanel.newCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
			for(BlokusButton button : this.buttons)
				button.update(elapsedTime);
		}
	}
	@Override
	public void draw(Graphics2D g) {
		if(this.visible)
		{
			Graphics2D g2d = (Graphics2D)g.create();
			
			g2d.setPaint(this.strokeColor);
			g2d.fillRect(this.position.getX() - this.stroke, this.position.getY() - this.stroke, this.size.width + this.stroke * 2, this.size.height + this.stroke * 2);
			g2d.setPaint(this.backColor);
			g2d.fillRect(this.position.getX(), this.position.getY(), this.size.width, this.size.height);
			
			g2d.setFont(this.font);
			g2d.setPaint(Color.BLACK);
			
			if(this.getMessage() != null){
				int i = 0;
				int j = 0;
				for(String line : this.getMessage().split("\n"))
				{
					j++;
				}
				for(String line : this.getMessage().split("\n"))
				{
					g2d.drawString(line, 
							(int)(DEFAULT_POSITION.getX() + (DEFAULT_SIZE.getWidth() / 2) - (this.font.getStringBounds(line, g2d.getFontRenderContext()).getWidth() / 2)),
							(int)(DEFAULT_POSITION.getY() + DEFAULT_SIZE.getHeight() / 2) + i * g2d.getFontMetrics(this.font).getHeight() - j/2 * g2d.getFontMetrics(this.font).getHeight());
					i++;
				}
				
			}
			
			if(this.getImage() != null){
				g2d.drawImage(this.getImage(), 389, 264, 400, 250, null);
			}
			
			for(BlokusButton button : this.buttons)
				button.draw(g);
			
			g2d.dispose();
		}
	}
	
	/**
	 * Retourne l'état des boutons
	 * 
	 * @return l'état
	 */
	public BlokusMessageBoxButtonState getButtonState()
	{
		return buttonState;
	}

	/**
	 * Setter des boutons
	 * 
	 * @param buttonState
	 */
	public void setButtonState(BlokusMessageBoxButtonState buttonState)
	{
		this.buttonState = buttonState;
	}

	/**
	 * Getter du message
	 * 
	 * @return le message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Setter du message
	 * 
	 * @param message le message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * Getter de l'image
	 * 
	 * @return l'image
	 */
	public BufferedImage getImage()
	{
		return image;
	}

	/**
	 * Setter de l'image
	 * 
	 * @param image l'image
	 */
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}
	
	/**
	 * Getter de la position
	 * 
	 * @return la position
	 */
	public Vector2 getPosition()
	{
		return position;
	}

	/**
	 * Setter de la position
	 * 
	 * @param position la position
	 */
	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	/**
	 * Getter de la taille
	 * 
	 * @return la taille
	 */
	public Dimension getSize()
	{
		return size;
	}

	/**
	 * Sette de la taille 
	 * 
	 * @param size la taille
	 */
	public void setSize(Dimension size)
	{
		this.size = size;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() != null)
		{
			this.raiseActionEvent(new ActionEvent(this, 0, e.getActionCommand()));
		}
	}

	/**
	 * Getter de la visibilité
	 * 
	 * @return la visibilité
	 */
	public boolean isShown() {
		return this.visible;
	}

	/**
	 * Getter de la taille du trait
	 * 
	 * @return la taille du trait
	 */
	public int getStroke() {
		return stroke;
	}

	/**
	 * Setter de la taille du trait
	 * 
	 * @param stroke la taille du trait
	 */
	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	/**
	 * Getter de la couleur du trait
	 * 
	 * @return la couleur du trait
	 */
	public Color getStrokeColor() {
		return strokeColor;
	}

	/**
	 * Setter de la couleur du trait
	 * 
	 * @param strokeColor la couleur du tratit
	 */
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	/**
	 * Getter de la couleur du fond
	 * 
	 * @return la couleur du fond
	 */
	public Color getBackColor() {
		return backColor;
	}

	/**
	 * Setter de la couleur du trait 
	 * 
	 * @param backColor la couleur du trait
	 */
	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}
}
