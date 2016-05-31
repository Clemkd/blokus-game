package gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import navigation.Page;
import utilities.BlokusMessageBoxButtonState;
import utilities.BlokusMessageBoxResult;
import utilities.Vector2;

public class BlokusMessageBox implements DrawableInterface, ActionListener {
	
	private final static Dimension DEFAULT_SIZE = new Dimension(Window.WIDTH, 300);
	
	private final static Vector2 DEFAULT_POSITION = new Vector2(0, (int)((Window.HEIGHT / 2) - (DEFAULT_SIZE.getHeight() / 2)));
	
	private final static int DEFAULT_MARGIN_X = 10;

	private static final int DEFAULT_STROKE = 1;
	
	private static final Color DEFAULT_STROKE_COLOR = Color.GRAY;
	
	private BlokusMessageBoxButtonState buttonState;
	
	private String message;
	
	private List<BlokusButton> buttons;
	
	private Vector2 position;
	
	private Dimension size;
	
	private boolean visible;
	
	private Font font;
	
	private List<ActionListener> listeners;
	
	private int stroke;
	
	private Color strokeColor;
	
	public BlokusMessageBox(String message, Font font, BlokusMessageBoxButtonState state)
	{
		this.stroke = DEFAULT_STROKE;
		this.setStrokeColor(DEFAULT_STROKE_COLOR);
		this.position = DEFAULT_POSITION;
		this.size = DEFAULT_SIZE;
		this.message = message;
		this.font = font;
		this.buttonState = state;
		this.buttons = new ArrayList<BlokusButton>();
		this.visible = false;
		this.listeners = new ArrayList<ActionListener>();
		
		this.initialize();
	}
	
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
	
	public void addListener(ActionListener listener)
	{
		this.listeners.add(listener);
	}
	
	public void removeListener(ActionListener listener)
	{
		this.listeners.remove(listener);
	}
	
	private void raiseActionEvent(ActionEvent e)
	{
		for(ActionListener listener : this.listeners)
			listener.actionPerformed(e);
	}
	
	public void show(Page parent)
	{
		this.visible = true;
		this.addListener(parent);
		parent.setMessageBox(this);
		parent.setEnabled(false);
	}
	
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
			// TODO : CODE HERE
			for(BlokusButton button : this.buttons)
				button.update(elapsedTime);
		}
	}
	@Override
	public void draw(Graphics2D g) {
		if(this.visible)
		{
			Graphics2D g2d = (Graphics2D)g.create();
			
			g2d.setPaint(Color.ORANGE);
			g2d.fillRect(this.position.getX() - this.stroke, this.position.getY() - this.stroke, this.size.width + this.stroke * 2, this.size.height + this.stroke * 2);
			g2d.setPaint(Color.WHITE);
			g2d.fillRect(this.position.getX(), this.position.getY(), this.size.width, this.size.height);
			
			g2d.setFont(this.font);
			g2d.setPaint(Color.BLACK);
			
			int i = 0;
			for(String line : this.getMessage().split("\n"))
			{
				g2d.drawString(line, 
						(int)(DEFAULT_POSITION.getX() + (DEFAULT_SIZE.getWidth() / 2) - (this.font.getStringBounds(this.getMessage(), g2d.getFontRenderContext()).getWidth() / 2)),
						(int)(DEFAULT_POSITION.getY() + DEFAULT_SIZE.getHeight() / 2) + i++ * g2d.getFontMetrics(this.font).getHeight());
			}
			
			for(BlokusButton button : this.buttons)
				button.draw(g);
			
			g2d.dispose();
		}
	}
	
	public BlokusMessageBoxButtonState getButtonState()
	{
		return buttonState;
	}

	public void setButtonState(BlokusMessageBoxButtonState buttonState)
	{
		this.buttonState = buttonState;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Vector2 getPosition()
	{
		return position;
	}

	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	public Dimension getSize()
	{
		return size;
	}

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

	public boolean isShown() {
		return this.visible;
	}

	public int getStroke() {
		return stroke;
	}

	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}
}
