package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import navigation.Page;
import utilities.Vector2;

public class BlokusNumericUpDown implements DrawableInterface, ActionListener {
	private String						text;
	private BlokusButton				plusButton;
	private BlokusButton				minusButton;
	private Vector2						position;
	private Vector2						positionValue;
	private Dimension					size;
	private ArrayList<ActionListener>	listeners;
	private Font						font;
	private Rectangle2D					textSize;
	private boolean						isEnabled;
	private float						value;
	private final float					minValue;
	private final float					maxValue;
	private float						step;
	private Vector2						positionText;
	private Rectangle2D					valueSize;

	public BlokusNumericUpDown(String text, float startingValue, float step, float minValue, float maxValue,
			Font font) {
		this.text = text;
		this.font = font;
		this.position = new Vector2();
		this.positionValue = new Vector2();
		this.positionText = new Vector2();
		this.size = new Dimension();
		this.plusButton = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "boxvolumeplus.png"));
		this.minusButton = new BlokusButton(getClass().getResource(Page.PATH_RESOURCES_BOUTONS + "boxvolumemoins.png"));
		this.plusButton.addListener(this);
		this.minusButton.addListener(this);

		this.value = startingValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.step = step;

		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		this.valueSize = this.font.getStringBounds("100", frc);
		this.textSize = this.font.getStringBounds(this.text, frc);

		this.listeners = new ArrayList<ActionListener>();
	}

	@Override
	public void update(float elapsedTime) {
		this.plusButton.update(elapsedTime);
		this.minusButton.update(elapsedTime);
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setFont(this.font);
		g2d.setColor(Color.WHITE);
		g2d.drawString(this.text, this.positionText.getX(), this.positionText.getY());
		this.minusButton.draw(g2d);
		g2d.drawString(String.valueOf(Math.round(this.value * 100)), this.positionValue.getX(),
				this.positionValue.getY());
		this.plusButton.draw(g2d);
		g2d.dispose();
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.positionText.setX(position.getX());
		this.positionText.setY(position.getY()+16);

		this.minusButton.setPosition(new Vector2((int)(this.positionText.getX() + this.textSize.getWidth() + 10), position.getY()));

		this.positionValue.setX((int) (this.minusButton.getSize().getWidth() + this.minusButton.getPosition().getX() + 5));
		this.positionValue.setY(position.getY()+16);

		this.plusButton.setPosition(new Vector2((int) (this.positionValue.getX() + this.valueSize.getWidth() + 5), position.getY()));
	}

	public Dimension getSize() {
		return this.size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void addListener(ActionListener listener) {
		this.listeners.add(listener);
	}

	public void raiseClickEvent(ActionEvent e) {
		for (ActionListener listener : this.listeners) {
			listener.actionPerformed(e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof BlokusButton) {
			if (e.getSource().equals(this.minusButton)) {
				this.value -= this.step;
				if (this.value < this.minValue) {
					this.value = this.minValue;
				}
			}
			else if (e.getSource().equals(this.plusButton)) {
				this.value += this.step;
				if (this.value > this.maxValue) {
					this.value = this.maxValue;
				}
			}
			this.raiseClickEvent(new ActionEvent(this, 0, null));
		}
	}

	public float getValue() {
		return this.value;
	}
}
