package entities;

import java.util.ArrayList;
import java.util.Random;

import utilities.InvalidMoveException;
import utilities.Vector2;

public class testBoard {

	public static void main(String[] args) {
		Board b = new Board();
		
		ArrayList<Vector2> vBlue = b.getValidMoves(CellColor.BLUE);
		System.out.println("BLUE : " + vBlue);
		
		ArrayList<Vector2> vGreen = b.getValidMoves(CellColor.GREEN);
		System.out.println("GREEN : " + vGreen);
		
		ArrayList<Tile> tilesBlue = Tile.getListOfNeutralTile(CellColor.BLUE);
		
		Random rnd = new Random();
		
		
		ArrayList<Tile> vt = new ArrayList<Tile>();
		ArrayList<Vector2> vs = b.getValidMoves(CellColor.BLUE);
		
		while(vs.size() > 0)
		{
			try {
				
				
				Vector2 fp = vs.get(rnd.nextInt(vs.size()));
				
				vt.clear();
				for(int i = 0; i < tilesBlue.size(); i++)
				{
					if(b.isValidMove(tilesBlue.get(i), fp))
						vt.add(tilesBlue.get(i));
				}
	
				
				if(vt.size() == 0) break;
				Tile t = vt.get(rnd.nextInt(vt.size()));
				tilesBlue.remove(t);
				b.addTile(t, fp);
			} catch (InvalidMoveException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			
			System.out.println(b.toString());
			
			vs = b.getValidMoves(CellColor.BLUE);
			System.out.println(vs);
			System.out.println(vt);
		}
	}

}
