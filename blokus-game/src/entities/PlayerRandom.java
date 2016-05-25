package entities;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import utilities.Move;
import utilities.Vector2;

public class PlayerRandom extends Player {

	private Random rand;
	
	public PlayerRandom(String name, List<CellColor> colors) 
	{
		super(name, colors);
		this.rand = new Random();
	}

	@Override
	public void play(Game game, CellColor c) 
	{
		this.playing = true;
		
		// La liste des placements possibles avec les tiles correspondants
		ArrayList<Entry<Tile, Vector2>> validMovesWithTiles = new ArrayList<Entry<Tile, Vector2>>();
		
		// La liste des tiles représentant les rotations et flips de tile
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		// Les placements actuels possibles
		ArrayList<Vector2> validMoves = game.board.getValidMoves(c);
		
		// La liste des pièces possibles avec leur position possible
		for(Vector2 validMove : validMoves)
		{
			// Pour chaque pièce du joueur
			for(Tile tile : this.getTileInventory())
			{
				// La liste des rotations et flips de la pièce
				tiles = tile.getTilesListOfRotationsAndFlips();
				
				// Pour chaque rotation et flip possible de la pièce
				for(Tile t : tiles)
				{
					if(game.board.isValidMove(t, validMove))
					{
						validMovesWithTiles.add(new AbstractMap.SimpleEntry<Tile, Vector2>(t, validMove));
					}
				}
			}
		}
		
		int index = this.rand.nextInt(validMovesWithTiles.size());
		Entry<Tile, Vector2> selectedMove = validMovesWithTiles.get(index);
		this.chosenMove = new Move(selectedMove.getValue(), selectedMove.getKey());
		
		this.playing = false;
	}

}
