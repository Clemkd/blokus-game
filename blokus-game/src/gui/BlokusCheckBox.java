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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import navigation.Page;
import program.Program;
import utilities.Vector2;

public class BlokusCheckBox implements DrawableInterface {
	
	private boolean isEnabled;
	
	private boolean isChecked;
	
	private BufferedImage checked;
	
	private BufferedImage noChecked;
	
	private String text;
	
	private Vector2 position;
	
	private Dimension size;
	
	private ArrayList<ActionListener> listeners;
	
	private Font font;
	
	private Rectangle2D textSize;
	
	private BlokusCheckBox(boolean enabled, boolean checked){
		this.isChecked = checked;
		this.isEnabled = enabled;
		this.text = "";
		this.font = null;
		this.position = new Vector2();
		this.size = new Dimension(20, 20);
		this.listeners = new ArrayList<ActionListener>();
	}
	
	public BlokusCheckBox(boolean enabled, boolean checked, String text, Font font) {

		this(enabled, checked);
		this.text = text;
		this.font = font;
		try {
			this.checked = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"boxchecked.png"));
			this.noChecked = ImageIO.read(getClass().getResource(Page.PATH_RESOURCES_BOUTONS+"box.png"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		this.textSize = this.font.getStringBounds(this.text, frc);
	}
	
	// TODO : Attention ! Font inexistante
	@Deprecated
	public BlokusCheckBox(boolean enabled, boolean checked, BufferedImage checkedImage, BufferedImage uncheckedImage) {
		this(enabled, checked);
		this.checked = checkedImage;
		this.noChecked = uncheckedImage;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public boolean isInBounds(Vector2 p)
	{
		return 	p.getX() >= this.position.getX() &&
				p.getX() < this.getSize().getWidth() + this.position.getX() + this.textSize.getWidth() &&
				p.getY() >= this.position.getY() &&
				p.getY() < this.getSize().getHeight() + this.position.getY();
	}
	
	@Override
	public void update(float elapsedTime) {
		
		if(this.isInBounds(Mouse.getPosition()) && this.isEnabled){
			GraphicsPanel.newCursor = Program.POINTING_HAND_CURSOR;
			if(Mouse.getLastMouseButton() == Mouse.LEFT && !Mouse.isReleased()){
				Mouse.consumeLastMouseButton();
				this.swapChecked();
			}
		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawImage(this.isChecked ?this.checked: this.noChecked, this.position.getX(), this.position.getY(), (int) this.size.getWidth(), (int) this.size.getHeight(), null);
		g2d.setColor(Color.WHITE);
		g2d.setFont(this.font);
		g2d.drawString(this.text, (int) (this.position.getX()+this.size.getWidth()+10), (int) (this.position.getY()+this.size.getHeight()-4));
		g2d.dispose();
		
	}
	
	public void swapChecked(){
		this.isChecked = !isChecked;
		this.raiseClickEvent(null);
	}
	
	public void addListener(ActionListener listener){
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
}