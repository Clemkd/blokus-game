package utilities;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.Stack;

public class UndoRedoManager<T> implements Serializable {
	/**
	 * Le stack de "annuler"
	 */
	private Stack<T>	undoStack;
	
	/**
	 * Le stack de "refaire"
	 */
	private Stack<T>	redoStack;

	public UndoRedoManager() {
		this.undoStack = new Stack<T>();
		this.redoStack = new Stack<T>();
	}

	/**
	 * Obtient l'état de la commande "annuler"
	 * @return L'état de la commande
	 */
	public boolean canUndo() {
		return !this.undoStack.empty();
	}

	/**
	 * Obtient l'état de la commande "refaire"
	 * @return L'état de la commande
	 */
	public boolean canRedo() {
		return !this.redoStack.empty();
	}

	/**
	 * Effectue une commande "annuler" auprés du manager
	 * @param current L'élément courant à insérer dans l'historique
	 * @return L'objet retiré de l'historique de la commande "annuler"
	 * @throws EmptyStackException Exception jetée si la pile de l'historique est vide
	 */
	public T undo(T current) throws EmptyStackException {
		if (!this.canUndo())
			throw new EmptyStackException();

		T obj = this.undoStack.pop();
		this.redoStack.push(current);

		return obj;
	}

	/**
	 * Effectue une commande "refaire" auprés du manager
	 * @param current L'élément courant à insérer dans l'historique
	 * @return L'objet retiré de l'historique de la commande "refaire"
	 * @throws EmptyStackException Exception jetée si la pile de l'historique est vide
	 */
	public T redo(T current) throws EmptyStackException {
		if (!this.canRedo())
			throw new EmptyStackException();

		T obj = this.redoStack.pop();
		this.undoStack.push(current);

		return obj;
	}

	/**
	 * Ajoute un nouvel élément dans l'historique
	 * @param obj L'élément à ajouter
	 */
	public void add(T obj) {
		this.undoStack.push(obj);
		this.redoStack.clear();
	}

}
