import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import entities.BoardManager;
import utilities.UndoRedoManager;

public abstract class Game {
	/**
	 * Plateau de jeu courant
	 */
	protected BoardManager board;
	/**
	 * Liste des joueurs de la partie
	 */
	protected ArrayList<Player> players;
	/**
	 * Tour actuel, aussi utilisé pour determiner le joueur courant
	 */
	protected int currentTurn;
	/**
	 * Prise en charge de la sauvegarde de l'état de la partie pour les annulations et répétitions de coups
	 */
	protected UndoRedoManager<BoardManager> undoRedoManager;
	/**
	 * Liste des ActionListeners de cette partie
	 */
	protected ArrayList<ActionListener> listeners;
	
	/**
	 * Renvoie l'état de la partie, la partie est finie quand aucun joueur ne peut jouer un coup supplémentaire.
	 * @return Etat de la partie, true si elle est terminée.
	 */
	public abstract boolean isTerminated();
	
	/**
	 * Transmet une copie du plateau courant
	 * @return Copie du plateau courant
	 */
	public BoardManager getBoard() {
		return this.board.copy();
	}
	
	/**
	 * Permet de récuperer le tour courant
	 * @return Tour courant
	 */
	public int getTurn() {
		return this.currentTurn;
	}
	
	//TODO: Implémenter le undoRedoManager différemment car besoin sauvegarder état des joueurs aussi(pieces restantes, etc)
	public void undoMove() {
		this.board = undoRedoManager.undo(this.board);
		this.currentTurn--;
	}
	public void redoMove() {
		this.board = undoRedoManager.redo(this.board);
		this.currentTurn++;
	}
	public boolean canUndo() {
		return this.undoRedoManager.canUndo();
	}
	public boolean canRedo() {
		return this.undoRedoManager.canRedo();
	}
	
	/**
	 * Récupère le joueur courant
	 * @return Joueur courant
	 */
	public Player getCurrentPlayer() {
		return this.players.get(this.currentTurn%this.players.size());
	}
	
	/**
	 * Algorithme correspondant au traitement d'un tour de jeu
	 */
	public abstract void doTurn();
	
	/**
	 * Sauvegarde la partie dans un fichier
	 * TODO: do it
	 */
	public abstract void save();
	
	/**
	 * Fonction appellée quand un clic doit être traité par Game
	 * @param x Colonne pointée
	 * @param y Ligne pointée
	 */
	public abstract void clickEvent(int x, int y);
	
	public void addListener(ActionListener al) {
		this.listeners.add(al);
	}
	
	public void removeListener(ActionListener al) {
		this.listeners.remove(al);
	}
	
	public void notifyListeners(ActionEvent e) {
		for(ActionListener al : this.listeners) {
			al.actionPerformed(e);
		}
	}
}
