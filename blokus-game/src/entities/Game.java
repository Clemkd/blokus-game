package entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utilities.Move;
import utilities.OutOfBoundsException;
import utilities.UndoRedoManager;
import utilities.Vector2;

public class Game
{
	/**
	 * Plateau de jeu courant
	 */
	private Board board;
	/**
	 * Liste des joueurs de la partie
	 */
	private ArrayList<Player> players;
	/**
	 * Tour actuel, aussi utilisé pour determiner le joueur courant
	 */
	private int currentTurn;
	/**
	 * Prise en charge de la sauvegarde de l'état de la partie pour les
	 * annulations et répétitions de coups
	 */
	private UndoRedoManager<Board> undoRedoManager;

	private ArrayList<CellColor> playingColors;
	
	private Gson gson;
	private boolean testedMove;

	public Game()
	{	
		this.gson = new Gson();
		this.currentTurn = 0;
		this.testedMove = false;
		this.board = new Board();

		this.players = new ArrayList<Player>();
		ArrayList<CellColor> colorsP1 = new ArrayList<CellColor>();
		colorsP1.add(CellColor.BLUE);
		colorsP1.add(CellColor.RED);
		this.players.add(new PlayerHuman("J1", colorsP1));
		ArrayList<CellColor> colorsP2 = new ArrayList<CellColor>();
		colorsP2.add(CellColor.YELLOW);
		colorsP2.add(CellColor.GREEN);
		this.players.add(new PlayerRandom("J2", colorsP2));

		this.undoRedoManager = new UndoRedoManager<Board>();

		this.playingColors = new ArrayList<CellColor>();
		this.playingColors.add(CellColor.BLUE);
		this.playingColors.add(CellColor.YELLOW);
		this.playingColors.add(CellColor.RED);
		this.playingColors.add(CellColor.GREEN);
	}
	
	public Game(Game g)
	{
		this.gson = new Gson();
		this.testedMove = false;

		this.players = new ArrayList<Player>();
		this.undoRedoManager = new UndoRedoManager<Board>();
		this.playingColors = new ArrayList<CellColor>();
		
		this.addPlayer(g.players.get(0).copy());
		this.addPlayer(g.players.get(1).copy());
		this.board = g.board.copy();
		for(CellColor c : g.getPlayingColors()) {
			this.playingColors.add(c);
		}
		this.currentTurn = g.currentTurn;
	}

	private List<CellColor> getPlayingColors() {
		return this.playingColors;
	}

	public Game(Player player1, Player player2) {
		this();
		this.players.clear();
		this.players.add(player1);
		this.players.add(player2);
	}

	/**
	 * Renvoie l'état de la partie, la partie est finie quand aucun joueur ne
	 * peut jouer un coup supplémentaire.
	 * 
	 * @return Etat de la partie, true si elle est terminée.
	 */
	public boolean isTerminated()
	{
		if (this.playingColors.isEmpty())
			return true;
		return false;
	}

	/**
	 * Transmet une copie du plateau courant
	 * 
	 * @return Copie du plateau courant
	 */
	public Board getBoard()
	{
		return this.board.copy();
	}

	/**
	 * Permet de récuperer le tour courant
	 * 
	 * @return Tour courant
	 */
	public int getTurn()
	{
		return this.currentTurn;
	}

	// TODO: Implémenter le undoRedoManager différemment car besoin sauvegarder
	// état des joueurs
	// aussi(pieces restantes, etc)
	public void undoMove()
	{
		this.board = undoRedoManager.undo(this.board);
		this.currentTurn--;
	}

	public void redoMove()
	{
		this.board = undoRedoManager.redo(this.board);
		this.currentTurn++;
	}

	public boolean canUndo()
	{
		return this.undoRedoManager.canUndo();
	}

	public boolean canRedo()
	{
		return this.undoRedoManager.canRedo();
	}

	/**
	 * Récupère le joueur courant
	 * 
	 * @return Joueur courant
	 */
	public Player getCurrentPlayer()
	{
		CellColor c = getCurrentColor();

		for (Player p : this.players)
		{
			if (p.colors.contains(c))
				return p;
		}

		return null;
	}

	/**
	 * @return
	 */
	public CellColor getCurrentColor()
	{
		CellColor c;
		switch (this.currentTurn % 4)
		{
		case 0:
			c = CellColor.BLUE;
			break;
		case 1:
			c = CellColor.YELLOW;
			break;
		case 2:
			c = CellColor.RED;
			break;
		case 3:
			c = CellColor.GREEN;
			break;
		default:
			c = CellColor.BLUE;
			break;
		}

		return c;
	}

	/**
	 * Appelé à chaque itération de jeu
	 */
	public void update()
	{
		if (!this.isTerminated())
		{
			if(!this.testedMove) {
				this.testedMove = true;
				if (Move.generateRandomValidMove(this).getTile() == null)
				{
					this.playingColors.remove(this.getCurrentColor());
				}
			}
			if (this.playingColors.contains(this.getCurrentColor()))
			{
				Player p = this.getCurrentPlayer();
				if (!p.isPlaying())
				{
					Move m = p.getMove();
					if (m == null)
					{
						p.play(this, this.getCurrentColor());
					}
					else
					{
						if (m.getTile() != null)
						{
							this.doMove(m);
						}
						else
						{
							this.playingColors.remove(this.getCurrentColor());
						}
					}
				}
			}
			else
			{
				this.currentTurn++;
				this.testedMove = false;
			}
		}
		else
		{
			System.out.println("Partie terminée !");
		}
	}

	/**
	 * Sauvegarde la partie dans un fichier TODO: do it
	 */
	public void save(){
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		
		Player playerToSave = getCurrentPlayer();
		List<Tile> list = this.players.get(0).getTileInventory();
		for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i).toString());
		}
		CellColor colorToSave = getCurrentColor();
		//String json = gson.toJson(list);
		//System.out.println(json);
	}
	
	public void load(){
		System.out.println("load");
		
	}

	/**
	 * Fonction appellée quand un coup valide doit être traité/appliqué par Game
	 * 
	 * @param 
	 *            Coup joué
	 */
	public void doMove(Move m)
	{
		this.undoRedoManager.add(this.board.copy());

		try
		{
			this.board.addTile(m.getTile(), m.getTileOrigin(), m.getPosition());
			this.currentTurn++;
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Renvoi un objet Game représentant l'objet courant auquel on a appliqué un
	 * mouvement qui ne doit pas être pris en compte dans l'historique de la
	 * partie
	 * 
	 * @param m
	 *            Coup à jouer
	 * @return Nouvel état de partie
	 */
	public Game simulateMove(Move m)
	{
		// TODO
		return null;
	}

	/**
	 * Renvoi le score du joueur fourni en paramètre dans l'état actuel du jeu
	 * 
	 * @param player
	 *            Joueur
	 * @return Score du joueur
	 */
	public int getScore(Player player)
	{
		int score = 0;
		Vector2 v = new Vector2();
		
		for(int i=0; i<Board.HEIGHT; i++) {
			for(int j=0; j<Board.WIDTH; j++) {
				v.setX(i);
				v.setY(j);
				try {
					if(player.getColors().contains(this.board.getCell(v))) {
						score++;
					}
				}
				catch (OutOfBoundsException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
		if(player.getTileInventory().isEmpty()) {
			score += 15;
			if(player.lastTileWasSingleCell()) {
				score += 5;
			}
		}

		
		return score;
	}

	/**
	 * Obtient la liste des joueurs du jeu
	 * 
	 * @return La liste des joueurs du jeu
	 */
	public ArrayList<Player> getPlayers()
	{
		return this.players;
	}

	// TODO : A Verifier
	/**
	 * Annule le tour de jeu effectué
	 * @param m Les données du tour de jeu
	 */
	public void revertMove(Move m)
	{
		// Revert du move sur le plateau
		this.getBoard().revertMove(m);
		
		// Revert des tiles du joueur
		if(!this.getCurrentPlayer().getColors().contains(m.getTile().getColor()))
			throw new InternalError("Essai d'ajout d'un tile incompatible dans l'inventaire du joueur courant");
		
		this.getCurrentPlayer().getTileInventory().add(m.getTile());
		
		// Revert des joueurs en jeu (si besoin)
		if(!this.playingColors.contains(this.getCurrentColor()))
		{
			this.playingColors.add(this.getCurrentColor());
		}
	}
	
	public Game copy() {
		return new Game(this);
	}
}
