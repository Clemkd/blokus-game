package entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.Timer;

import utilities.Move;
import utilities.Vector2;

public class PlayerRandom extends Player implements ActionListener {

	private Random rand;
	private Timer timer;
	private Game game;
	private CellColor color;

	public PlayerRandom(String name, List<CellColor> colors) {
		super(name, colors);
		this.rand = new Random();
		this.timer = null;
		this.game = null;
		this.color = null;
	}

	@Override
	public void play(Game game, CellColor c) {
		this.playing = true;
		this.game = game;
		this.color = c;
		this.timer = new Timer(800 + rand.nextInt(400), this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.timer.stop();
		// La liste des placements possibles avec les tiles correspondants
		ArrayList<Move> validMovesWithTiles = new ArrayList<Move>();

		// La liste des tiles représentant les rotations et flips de tile
		ArrayList<Tile> tileVariations = new ArrayList<Tile>();

		// Les placements actuels possibles
		ArrayList<Vector2> validMoves = game.getBoard().getFreePositions(this.color);

		// La liste des pièces possibles avec leur position possible
		for (Vector2 position : validMoves) {
			// Pour chaque pièce du joueur
			for (Tile tile : this.getTileInventory()) {
				if (tile.getCouleur() == this.color) {
					// La liste des rotations et flips de la pièce
					tileVariations = tile.getTilesListOfRotationsAndFlips();

					// Pour chaque rotation et flip possible de la pièce
					for (Tile t : tileVariations) {
						for (Vector2 tileOrigin : t.getExtremities()) {
							if (game.getBoard().isValidMove(t, tileOrigin, position)) {
								validMovesWithTiles.add(new Move(position, t, tileOrigin));
							}
						}
					}
				}
			}
		}

		if (validMovesWithTiles.size() > 0) {
			int index = this.rand.nextInt(validMovesWithTiles.size());
			Move selectedMove = validMovesWithTiles.get(index);
			this.chosenMove = new Move(selectedMove.getPosition(), selectedMove.getTile(), selectedMove.getTileOrigin());
		} else {
			this.chosenMove = new Move(0);
		}

		this.game = null;
		this.color = null;
		this.playing = false;
	}

}
