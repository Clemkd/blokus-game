package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entities.CellColor;
import entities.CellType;
import entities.Tile;
import utilities.BufferedHelper;
import utilities.Vector2;

public class BlokusTile implements DrawableInterface {
	/**
	 * L'objet correspondant aux données à représenter
	 */
	private Tile			tile;

	/**
	 * La position de l'objet sur la zone de dessin parente
	 */
	private Vector2			position;

	/**
	 * L'état du tile
	 */
	private boolean			enabled;

	/**
	 * Le mask du tile
	 */
	private BufferedImage	caseMaskImage;

	public BlokusTile(Tile tile, Vector2 position) {
		this.position = position;
		this.tile = tile;
		this.enabled = true;
		this.caseMaskImage = BufferedHelper.generateMask(this.tile.getColor().getImage(), new Color(128, 128, 128),
				0.75f);
	}

	public BlokusTile(Tile tile) {
		this(tile, new Vector2());
	}

	@Override
	public void update(float elapsedTime) {

	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g.create();

		Vector2 fc = this.tile.getFirstCase();

		for (int offsetX = 0; offsetX < Tile.WIDTH; offsetX++) {
			for (int offsetY = 0; offsetY < Tile.HEIGHT; offsetY++) {
				if (tile.getCellType(offsetX, offsetY) != CellType.BLANK) {
					Vector2 currentPosition = new Vector2(
							this.position.getX() + (-fc.getY() + offsetY) * CellColor.CELL_WIDTH,
							this.position.getY() + (-fc.getX() + offsetX) * CellColor.CELL_HEIGHT);

					g2d.drawImage(this.tile.getColor().getImage(), currentPosition.getX(), currentPosition.getY(),
							CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, null);

					if (!this.isEnabled()) {
						g2d.drawImage(this.caseMaskImage, currentPosition.getX(), currentPosition.getY(),
								CellColor.CELL_WIDTH, CellColor.CELL_HEIGHT, null);
					}
				}
			}
		}

		g2d.dispose();
	}

	/**
	 * Obtient la position actuelle de l'objet sur la zone de dessin parente
	 * 
	 * @return La position actuelle de l'objet
	 */
	public Vector2 getPosition() {
		return this.position;
	}

	/**
	 * Modifie la position de l'objet sur la zone de dessin parente
	 * 
	 * @param position
	 *            La nouvelle position de l'objet
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * Obtient l'objet de données de la représentation
	 * 
	 * @return L'objet de données
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * Méthode qui dit si le clic est sur la pièce
	 * 
	 * @param v
	 *            la position du clic
	 * @return vrai si la position est dans la pièce, faux sinon
	 */
	public boolean isInBounds(Vector2 v) {
		// TODO : ?
		if (!this.isEnabled())
			return false;

		Vector2 fc = this.tile.getFirstCase();

		for (int i = 0; i < Tile.WIDTH; i++) {
			for (int j = 0; j < Tile.HEIGHT; j++) {
				if (this.tile.getCellType(i, j) != CellType.BLANK) {
					Vector2 currentPosition = new Vector2(
							this.position.getX() + (-fc.getY() + j) * CellColor.CELL_WIDTH,
							this.position.getY() + (-fc.getX() + i) * CellColor.CELL_HEIGHT);
					if (v.getX() >= currentPosition.getX() && v.getY() >= currentPosition.getY()
							&& v.getX() <= currentPosition.getX() + CellColor.CELL_WIDTH
							&& v.getY() <= currentPosition.getY() + CellColor.CELL_HEIGHT) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Méthode qui renvoi la position de l'éventuelle cellule visée dans la matrice interne de la
	 * pièce, null si la souris n'est pas au dessus de la pièce
	 * 
	 * @param v
	 *            la position du curseur
	 * @return vecteur de la position de la cellule si possible, null sinon
	 */
	public Vector2 getCellOffset(Vector2 v) {
		if (!this.isEnabled())
			return null;

		Vector2 fc = this.tile.getFirstCase();

		for (int i = 0; i < Tile.WIDTH; i++) {
			for (int j = 0; j < Tile.HEIGHT; j++) {
				if (this.tile.getCellType(i, j) != CellType.BLANK) {
					Vector2 currentPosition = new Vector2(
							this.position.getX() + (-fc.getY() + j) * CellColor.CELL_WIDTH,
							this.position.getY() + (-fc.getX() + i) * CellColor.CELL_HEIGHT);

					if (v.getX() >= currentPosition.getX() && v.getY() >= currentPosition.getY()
							&& v.getX() <= currentPosition.getX() + CellColor.CELL_WIDTH
							&& v.getY() <= currentPosition.getY() + CellColor.CELL_HEIGHT) {
						return new Vector2(-fc.getX() + i, -fc.getY() + j);
					}
				}
			}
		}

		return null;
	}

	/**
	 * Determine l'état du tile
	 * 
	 * @param enabled
	 *            Le nouvel état du tile
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Obtient l'état courant du tile
	 * 
	 * @return L'état courant du tile
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setTile(Tile newTile) {
		this.tile = newTile;
	}
}
