package gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;

import utilities.Vector2;

public class BlokusMessageBox implements DrawableInterface {
	
	ArrayList<BlokusButton> listButtons;
	
	private Vector2 position;
	
	private Dimension size;
	
	private Color color;
	
	public BlokusMessageBox(ArrayList<BlokusButton> listButtons, Color color) {
		this.listButtons = listButtons;
		this.color = color;
		this.position = new Vector2();
		this.size = new Dimension();
	}

	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	
}
