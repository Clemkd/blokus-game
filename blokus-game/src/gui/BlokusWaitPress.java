package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import utilities.CSSColors;
import utilities.Vector2;

public class BlokusWaitPress implements DrawableInterface {

	private static final int			KEYTEXT_OFFSETX	= 5;
	private static final int			KEYTEXT_OFFSETY	= 24;
	private static final int			TEXT_OFFSETX	= 202;
	private static final int			TEXT_OFFSETY	= 24;
	private boolean						isEnabled;
	private String						text;
	private Vector2						position;
	private Dimension					size;
	private ArrayList<ActionListener>	listeners;
	private Font						font;
	private boolean						waiting;
	private int							keyCode;

	public BlokusWaitPress(boolean enabled, int defaultKeyCode, String text, Font font) {
		this.isEnabled = enabled;
		this.position = new Vector2();
		this.size = new Dimension(192, 32);
		this.listeners = new ArrayList<ActionListener>();
		this.text = text;
		this.font = font;
		this.waiting = false;
		this.keyCode = defaultKeyCode;
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
		if (!this.isEnabled)
			this.waiting = false;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Dimension getSize() {
		return this.size;
	}

	public boolean getWaiting() {
		return this.waiting;
	}

	public int getKeyCode() {
		return this.keyCode;
	}

	public void setKeyCode(int k) {
		this.keyCode = k;
	}

	public boolean isInBounds(Vector2 p) {
		return p.getX() >= this.position.getX() && p.getX() < this.getSize().getWidth() + this.position.getX()
				&& p.getY() >= this.position.getY() && p.getY() < this.getSize().getHeight() + this.position.getY();
	}

	@Override
	public void update(float elapsedTime) {
		if (this.isEnabled) {
			if (this.waiting) {
				if (Mouse.getLastMouseButton() == Mouse.RIGHT && !Mouse.isReleased()) {
					this.waiting = false;
					Mouse.consumeLastMouseButton();
				}
				if (Keyboard.getLastKeyTyped() != Keyboard.NONE) {
					this.keyCode = Keyboard.getLastKeyTyped();
					this.waiting = false;
					Keyboard.consumeLastKeyTyped();
				}
			}
			else {
				if (this.isInBounds(Mouse.getPosition())) {
					if (Mouse.getLastMouseButton() == Mouse.LEFT && !Mouse.isReleased()) {
						this.waiting = true;
						Keyboard.consumeLastKeyTyped();
						Mouse.consumeLastMouseButton();
					}
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();

		if (this.waiting) {
			g2d.setColor(CSSColors.ANTIQUEWHITE.color());
		}
		else {
			g2d.setColor(Color.WHITE);
		}
		g2d.fillRect(this.position.getX(), this.position.getY(), (int) this.size.getWidth(),
				(int) this.size.getHeight());

		String keyText = KeyEvent.getKeyText(this.keyCode);
		if (keyText.length() > 18)
			keyText = keyText.substring(0, 18);
		if (this.waiting)
			keyText = ". . .";

		String hintText = "";
		if (this.waiting)
			hintText = "(clic droit pour annuler)";

		g2d.setFont(this.font);
		g2d.setColor(Color.BLACK);
		g2d.drawString(keyText, this.position.getX() + KEYTEXT_OFFSETX, this.position.getY() + KEYTEXT_OFFSETY);
		g2d.setColor(Color.WHITE);
		g2d.drawString(this.text + " " + hintText, this.position.getX() + TEXT_OFFSETX,
				this.position.getY() + TEXT_OFFSETY);

		g2d.dispose();
	}

	public void addListener(ActionListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Lance l'évènement de clique bouton auprès de tous ses listeners
	 * 
	 * @param e
	 *            Les information sur l'évènement
	 */
	public void raiseClickEvent(ActionEvent e) {
		for (ActionListener listener : this.listeners) {
			listener.actionPerformed(e);
		}
	}
}
