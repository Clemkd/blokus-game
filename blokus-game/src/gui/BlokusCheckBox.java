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
	
	/**
	 * Etat d'activation de la case à cocher
	 */
	private boolean isEnabled;
	
	/**
	 * Etat de la case à cocher
	 */
	private boolean isChecked;
	
	/**
	 * Image de l'état coché
	 */
	private BufferedImage checked;
	
	/**
	 * Image de l'état décoché
	 */
	private BufferedImage noChecked;
	
	/**
	 * Le texte associé à la case à cocher
	 */
	private String text;
	
	/**
	 * La position de la case à cocher
	 */
	private Vector2 position;
	
	/**
	 * La taille de la case à cocher
	 */
	private Dimension size;
	
	/**
	 * La liste des listener
	 */
	private ArrayList<ActionListener> listeners;
	
	/**
	 * La police de caractère
	 */
	private Font font;
	
	/**
	 * La taille du texte
	 */
	private Rectangle2D textSize;
	
	/**
	 * Constructeur de la case à cocher
	 * 
	 * @param enabled si la case doit être activée
	 * @param checked si la case est coché
	 */
	private BlokusCheckBox(boolean enabled, boolean checked){
		this.isChecked = checked;
		this.isEnabled = enabled;
		this.text = "";
		this.font = null;
		this.position = new Vector2();
		this.size = new Dimension(20, 20);
		this.listeners = new ArrayList<ActionListener>();
	}
	
	/**
	 * Constructeur de la case à cocher
	 * 
	 * @param enabled si la case doit être activée
	 * @param checked si la case est coché
	 * @param text le texte associé
	 * @param font la police de caractère
	 */
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
	
	/**
	 * Getter de l'état de l'activation de la case à cocher
	 * 
	 * @return l'etat de la case à cocher
	 */
	public boolean isEnabled() {
		return isEnabled;
	}
	
	/**
	 * Setter de l'état de l'activation de la case à cocher
	 * 
	 * @param isEnabled l'etat de la case à cocher
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	/**
	 * Getter de l'état de la coche de la case à cocher
	 * 
	 * @return l'état de la coche de la case à cocher
	 */
	public boolean isChecked() {
		return isChecked;
	}
	
	/**
	 * Setter de l'état de la coche de la case à cocher
	 * 
	 * @param isChecked l'état de la coche de la case à cocher
	 */
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	/**
	 * Getter de la position
	 * 
	 * @return la position
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Setter de la position
	 * 
	 * @param position la position
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	/**
	 * Getter de la taille
	 * 
	 * @return la taille
	 */
	public Dimension getSize() {
		return size;
	}
	
	/**
	 * Setter de la taille
	 * 
	 * @param size la taille
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}
	
	/**
	 * Méthode qui permet de détecter si le clic est sur la case ou sur le texte
	 * 
	 * @param p la position de la souris
	 * @return true si oui, false sinon
	 */
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
	
	/**
	 * Change l'état de coche de la case à cocher
	 */
	public void swapChecked(){
		this.isChecked = !isChecked;
		this.raiseClickEvent(new ActionEvent(this, 0, null));
	}
	
	/**
	 * Ajoute un listener à la liste des listener
	 * 
	 * @param listener le listener
	 */
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