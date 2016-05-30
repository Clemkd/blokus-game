package gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import navigation.Page;
import utilities.BlokusMessageBoxButtonState;
import utilities.BlokusMessageBoxResult;
import utilities.Vector2;

public class BlokusMessageBox implements DrawableInterface, ActionListener {
	
	private final static Dimension DEFAULT_SIZE = new Dimension(Window.WIDTH, 300);
	
	private final static Vector2 DEFAULT_POSITION = new Vector2(0, (int)((Window.HEIGHT / 2) - (DEFAULT_SIZE.getHeight() / 2)));
	
	private BlokusMessageBoxButtonState buttonState;
	
	private String message;
	
	private List<BlokusButton> buttons;
	
	private Vector2 position;
	
	private Dimension size;
	
	private boolean visible;
	
	private BlokusMessageBoxResult result;
	
	public BlokusMessageBox(String message, BlokusMessageBoxButtonState state)
	{
		this.result = null;
		this.position = DEFAULT_POSITION;
		this.size = DEFAULT_SIZE;
		this.message = message;
		this.buttonState = state;
		this.buttons = new ArrayList<BlokusButton>();
		this.visible = false;
		
		this.initialize();
	}
	
	private void initialize()
	{
		this.buttons.clear();
		
		if(this.buttonState == BlokusMessageBoxButtonState.VALID)
		{
			BlokusButton buttonValid = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "optionsig.png"));
			buttonValid.setActionCommand(BlokusMessageBoxResult.VALID.getTitle());
			buttonValid.addListener(this);
			this.buttons.add(buttonValid); // Bouton VALIDER
			
		}
		else if(this.buttonState == BlokusMessageBoxButtonState.VALID_OR_CANCEL)
		{
			BlokusButton buttonValid = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "optionsig.png"));
			buttonValid.setActionCommand(BlokusMessageBoxResult.VALID.getTitle());
			buttonValid.addListener(this);
			this.buttons.add(buttonValid); // Bouton VALIDER
			
			BlokusButton buttonCancel = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "optionsig.png"));
			buttonCancel.setActionCommand(BlokusMessageBoxResult.CANCEL.getTitle());
			buttonCancel.addListener(this);
			this.buttons.add(buttonCancel); // Bouton ANNULER
		}
		else if(this.buttonState == BlokusMessageBoxButtonState.YES_OR_NO)
		{
			BlokusButton buttonYes = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "optionsig.png"));
			buttonYes.setActionCommand(BlokusMessageBoxResult.YES.getTitle());
			buttonYes.addListener(this);
			this.buttons.add(buttonYes); // Bouton OUI
			
			BlokusButton buttonNo = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "optionsig.png"));
			buttonNo.setActionCommand(BlokusMessageBoxResult.NO.getTitle());
			buttonNo.addListener(this);
			this.buttons.add(buttonNo); // Bouton ANNULER
		}
	}
	
	// TODO : Tester
	private Semaphore semaphore = new Semaphore(0);
	
	public BlokusMessageBoxResult show() throws InterruptedException
	{
		this.visible = true;
		
		// TODO : Tester
		semaphore.drainPermits();
	    semaphore.acquire();
	    
	    return this.result;
	}

	@Override
	public void update(float elapsedTime) {
		if(this.visible)
		{
			// TODO : CODE HERE
		}
	}

	@Override
	public void draw(Graphics2D g) {
		if(this.visible)
		{
			// TODO : CODE HERE
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
			if(e.getActionCommand() == BlokusMessageBoxResult.VALID.getTitle())
			{
				// TODO : Tester
				if (semaphore.availablePermits() == 0)
			        semaphore.release();
				// TODO : CODE HERE
			}
			else if(e.getActionCommand() == BlokusMessageBoxResult.CANCEL.getTitle())
			{
				// TODO : CODE HERE
			}
			else if(e.getActionCommand() == BlokusMessageBoxResult.YES.getTitle())
			{
				// TODO : CODE HERE
			}
			else if(e.getActionCommand() == BlokusMessageBoxResult.NO.getTitle())
			{
				// TODO : CODE HERE
			}
		}
	}
}
