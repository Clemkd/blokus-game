package gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import utilities.Vector2;

public class BlokusImageBox implements DrawableInterface {
	
	private Vector2 position;
	private Dimension size;
	private Color color;
	private BlokusButton buttonOk;
	private Image image;
	
	public BlokusImageBox(Image image, Color color) {
		this.color = color;
		this.position = new Vector2();
		this.size = new Dimension();
		this.image = image;
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
