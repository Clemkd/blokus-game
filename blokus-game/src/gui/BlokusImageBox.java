package gui;

import java.awt.Color;

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

public class BlokusImageBox implements DrawableInterface, ActionListener {
	
	private final static Dimension DEFAULT_SIZE = new Dimension(Window.WIDTH, 300);
	
	private final static Vector2 DEFAULT_POSITION = new Vector2(0, (int)((Window.HEIGHT / 2) - (DEFAULT_SIZE.getHeight() / 2)));
	
	private final static int DEFAULT_MARGIN_X = 10;

	private static final int DEFAULT_STROKE = 1;
	
	private static final Color DEFAULT_STROKE_COLOR = Color.GRAY;
	
	private BlokusMessageBoxButtonState buttonState;
	
	private List<BlokusButton> buttons;
	
	private Vector2 position;
	
	private Dimension size;
	
	private boolean visible;
	
	private List<ActionListener> listeners;
	
	private int stroke;
	
	private Color strokeColor;
	
	private BufferedImage image;

	
	public BlokusImageBox(BufferedImage image, BlokusMessageBoxButtonState state)
	{
		this.stroke = DEFAULT_STROKE;
		this.setStrokeColor(DEFAULT_STROKE_COLOR);
		this.position = DEFAULT_POSITION;
		this.size = DEFAULT_SIZE;
		this.buttonState = state;
		this.buttons = new ArrayList<BlokusButton>();
		this.visible = false;
		this.listeners = new ArrayList<ActionListener>();
		this.image = image;
		this.initialize();
	}
	
	private void initialize()
	{
		this.buttons.clear();
		
		if(this.buttonState == BlokusMessageBoxButtonState.VALID)
		{
			BlokusButton buttonValid = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "optionsig.png"));
			buttonValid.setActionCommand(BlokusMessageBoxResult.VALID.getActionCommand());
			buttonValid.setPosition(new Vector2(2 * Window.WIDTH / 3, (int) (DEFAULT_POSITION.getY() + DEFAULT_SIZE.getHeight() - buttonValid.getSize().getHeight() * 2)));
			buttonValid.addListener(this);
			this.buttons.add(buttonValid); // Bouton VALIDER
			
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
			
			g2d.setPaint(Color.BLACK);
			
			g2d.drawImage(image, null, 454, 295);
			
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
