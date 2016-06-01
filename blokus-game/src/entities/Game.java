package entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import utilities.Move;
import utilities.OutOfBoundsException;
import utilities.UndoRedoManager;
import utilities.Vector2;

public class Game implements Serializable
{
	/**
	 * Default Serialisated ID
	 */
	private static final long serialVersionUID = 1L;
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

	/**
	 * Prise en charge de la sauvegarde de l'état des joueurs pour les annulations et répétitions
	 */
	private ArrayList<UndoRedoManager<Player>> undoRedoManagerPlayer;

	/**
	 * La liste des couleurs jouée
	 */
	private ArrayList<CellColor> playingColors;
	
	/**
	 * Flag d'un mouvement jouée
	 */
	private boolean testedMove;

	/**
	 * Prise en charge de la sauvegarde de la liste pour les annulations et répétitions
	 */
	private UndoRedoManager<ArrayList<CellColor>> undoRedoManagerColors;

	public Game()
	{	
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
		UndoRedoManager<Player> p1 = new UndoRedoManager<Player>();
		UndoRedoManager<Player> p2 = new UndoRedoManager<Player>();
		this.undoRedoManagerPlayer = new ArrayList<UndoRedoManager<Player>>();
		this.undoRedoManagerPlayer.add(p1);
		this.undoRedoManagerPlayer.add(p2);

		this.undoRedoManagerColors = new UndoRedoManager<ArrayList<CellColor>>();

		this.playingColors = new ArrayList<CellColor>();
		this.playingColors.add(CellColor.BLUE);
		this.playingColors.add(CellColor.YELLOW);
		this.playingColors.add(CellColor.RED);
		this.playingColors.add(CellColor.GREEN);
	}

	public Game(Game g)
	{
		this.testedMove = false;

		this.players = new ArrayList<Player>();
		this.undoRedoManager = new UndoRedoManager<Board>();
		UndoRedoManager<Player> p1 = new UndoRedoManager<Player>();
		UndoRedoManager<Player> p2 = new UndoRedoManager<Player>();
		this.undoRedoManagerPlayer = new ArrayList<UndoRedoManager<Player>>();
		this.undoRedoManagerPlayer.add(p1);
		this.undoRedoManagerPlayer.add(p2);
		this.playingColors = new ArrayList<CellColor>();

		this.players.add(g.players.get(0).copy());
		this.players.add(g.players.get(1).copy());
		this.board = g.board.copy();

		this.undoRedoManagerColors = new UndoRedoManager<ArrayList<CellColor>>();
		ArrayList<CellColor> colorsCopy = new ArrayList<CellColor>();
		colorsCopy.addAll(g.playingColors);
		this.playingColors = colorsCopy;
		for(CellColor c : g.getPlayingColors()) {
			this.playingColors.add(c);
		}
		this.currentTurn = g.currentTurn;
	}

	/**
	 * Getter des couleurs
	 * 
	 * @return une liste des couleurs du jeu
	 */
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

	/**
	 * Annule un mouvement
	 */
	public void undoMove()
	{
		this.board = undoRedoManager.undo(this.board);
		this.playingColors = undoRedoManagerColors.undo(this.playingColors);
		if(currentTurn%2==1)
		{
			if(!(this.players.get(0) instanceof PlayerHuman))
			{
				this.players.set(1, this.undoRedoManagerPlayer.get(1).undo(this.players.get(1)));
				this.board = undoRedoManager.undo(this.board);
				currentTurn--;
			}
			this.players.set(0, this.undoRedoManagerPlayer.get(0).undo(this.players.get(0)));
		}
		else
		{
			if(!(this.players.get(1) instanceof PlayerHuman))
			{
				this.players.set(0, this.undoRedoManagerPlayer.get(0).undo(this.players.get(0)));
				this.board = undoRedoManager.undo(this.board);
				currentTurn--;
			}
			this.players.set(1, this.undoRedoManagerPlayer.get(1).undo(this.players.get(1)));
		}


		this.currentTurn--;
	}

	/**
	 * Annule un seul mouvement (ne pas utiliser dans une partie normale)
	 */
	public void undoSingleMove()
	{
		this.board = undoRedoManager.undo(this.board);
		this.playingColors = undoRedoManagerColors.undo(this.playingColors);
		if(currentTurn%2==1)
		{
			this.players.set(0, this.undoRedoManagerPlayer.get(0).undo(this.players.get(0)));
		}
		else
		{
			this.players.set(1, this.undoRedoManagerPlayer.get(1).undo(this.players.get(1)));
		}

		this.currentTurn--;
	}

	/**
	 * Refait un mouvement annulé
	 */
	public void redoMove()
	{
		this.board = undoRedoManager.redo(this.board);
		this.playingColors = undoRedoManagerColors.redo(this.playingColors);
		if(currentTurn%2==0)
		{
			if(!(this.players.get(0) instanceof PlayerHuman))
			{
				this.players.set(1, this.undoRedoManagerPlayer.get(1).redo(this.players.get(1)));
				this.board = undoRedoManager.redo(this.board);
				currentTurn++;
			}
			this.players.set(0, this.undoRedoManagerPlayer.get(0).redo(this.players.get(0)));
		}
		else
		{
			if(!(this.players.get(1) instanceof PlayerHuman))
			{
				this.players.set(0, this.undoRedoManagerPlayer.get(0).redo(this.players.get(0)));
				this.board = undoRedoManager.redo(this.board);
				currentTurn++;
			}
			this.players.set(1, this.undoRedoManagerPlayer.get(1).redo(this.players.get(1)));
		}
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
						this.doMove(m);
					}
				}
			}
			else
			{
				this.undoRedoManager.add(this.board.copy());
				ArrayList<CellColor> colorsCopy = new ArrayList<CellColor>();
				colorsCopy.addAll(this.playingColors);
				this.undoRedoManagerColors.add(colorsCopy);
				if(currentTurn%2==0)
				{
					Player p1 = this.getCurrentPlayer().copy();
					this.undoRedoManagerPlayer.get(0).add(p1);
				}
				else
				{
					Player p2 = this.getCurrentPlayer().copy();
					this.undoRedoManagerPlayer.get(1).add(p2);
				}

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
	 * Sauvegarde la partie dans un fichier
	 */
	public void save(){
		JFileChooser jFileChooser = new JFileChooser();
		int choice = jFileChooser.showSaveDialog(jFileChooser);
		if(choice == JFileChooser.APPROVE_OPTION){
			String path = jFileChooser.getSelectedFile().getAbsolutePath();
			try {
				FileOutputStream fileSave = new FileOutputStream(path);
				ObjectOutputStream out = new ObjectOutputStream(fileSave);
				out.writeObject(this);
				out.close();
				fileSave.close();
				System.out.println("sérialisation de game okay");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Méthode de chargement d'une partie
	 * 
	 * @return la partie chargée
	 */
	public static Game load(){
		Game gameToLoad = null;
		JFileChooser jfChooser = new JFileChooser();
		int choice = jfChooser.showOpenDialog(jfChooser);
		if(choice == JFileChooser.APPROVE_OPTION){
			String path = jfChooser.getSelectedFile().getAbsolutePath();
			try {
				FileInputStream fileToLoad = new FileInputStream(path);
				ObjectInputStream in = new ObjectInputStream(fileToLoad);
				gameToLoad = (Game) in.readObject();
				in.close();
				fileToLoad.close();
			} catch (IOException e) {
				e.printStackTrace();
			}catch(ClassNotFoundException c){
				c.printStackTrace();
			}
		}
		return gameToLoad;

	}

	/**
	 * Fonction appellée quand un coup valide doit être traité/appliqué par Game
	 * 
	 * @param 
	 *            Coup joué
	 */
	public void doMove(Move m)
	{
		if (m.getTile() != null)
		{
			this.undoRedoManager.add(this.board.copy());
			ArrayList<CellColor> colorsCopy = new ArrayList<CellColor>();
			colorsCopy.addAll(this.playingColors);
			this.undoRedoManagerColors.add(colorsCopy);
			if(currentTurn%2==0)
			{
				Player p1 = this.getCurrentPlayer().copy();
				p1.addTileToInventory(m.getTile());
				this.undoRedoManagerPlayer.get(0).add(p1);
			}
			else
			{
				Player p2 = this.getCurrentPlayer().copy();
				p2.addTileToInventory(m.getTile());
				this.undoRedoManagerPlayer.get(1).add(p2);
			}

			try
			{
				this.board.addTile(m.getTile(), m.getTileOrigin(), m.getPosition());
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
				System.exit(0);
			}
		}
		else
		{
			this.playingColors.remove(this.getCurrentColor());

			this.undoRedoManager.add(this.board.copy());
			ArrayList<CellColor> colorsCopy = new ArrayList<CellColor>();
			colorsCopy.addAll(this.playingColors);
			this.undoRedoManagerColors.add(colorsCopy);
			if(currentTurn%2==0)
			{
				Player p1 = this.getCurrentPlayer().copy();
				this.undoRedoManagerPlayer.get(0).add(p1);
			}
			else
			{
				Player p2 = this.getCurrentPlayer().copy();
				this.undoRedoManagerPlayer.get(1).add(p2);
			}
		}
		this.currentTurn++;
		this.testedMove = false;
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
	 * Obtient le joueur gagnant
	 * @return Le joueur gagnant
	 */
	public Player getWinner()
	{
		Player res = null;

		if(this.isTerminated())
		{
			int score1 = this.getScore(this.getPlayers().get(0));
			int score2 = this.getScore(this.getPlayers().get(1));

			res =  score1 > score2 ?  this.getPlayers().get(0) : this.getPlayers().get(1);
		}
		System.out.println(res);
		return res;
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

	// TODO : SUPPRIMER
	/**
	 * Annule le tour de jeu effectué
	 * @param m Les données du tour de jeu
	 */
	/*public void revertMove(Move m)
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
	}*/

	/**
	 * Méthode de copie de Game
	 * 
	 * @return une copie de Game
	 */
	public Game copy() {
		return new Game(this);
	}
}
