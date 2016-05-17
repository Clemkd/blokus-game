
public class Game {
	/**
	 * Plateau de jeu courant
	 */
	private BoardManager board;
	/**
	 * Liste des joueurs de la partie
	 */
	private ArrayList<Player> players;
	/**
	 * Tour actuel, aussi utilisé pour determiner le joueur courant
	 */
	private int currentTurn;
	/**
	 * Prise en charge de la sauvegarde de l'état de la partie pour les annulations et répétitions de coups
	 */
	private UndoRedoManager<BoardManager> undoRedoManager;
	
	/**
	 * Renvoie l'état de la partie, la partie est finie quand aucun joueur ne peut jouer un coup supplémentaire.
	 * @return Etat de la partie, true si elle est terminée.
	 */
	public boolean isTerminated() {
		// TODO: renvoyer une valeur utile
		return false;
	}
	
	public BoardManager getBoard() {
		
	}
}
