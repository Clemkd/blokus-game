package utilities;

import java.util.EmptyStackException;
import java.util.Stack;

public class UndoRedoManager<T> {
	Stack<T>	undoStack;
	Stack<T>	redoStack;

	public UndoRedoManager() {
		this.undoStack = new Stack<T>();
		this.redoStack = new Stack<T>();
	}

	public boolean canUndo() {
		return !this.undoStack.empty();
	}

	public boolean canRedo() {
		return !this.redoStack.empty();
	}

	public T undo(T current) throws EmptyStackException {
		if (!this.canUndo())
			throw new EmptyStackException();

		T obj = this.undoStack.pop();
		this.redoStack.push(current);

		return obj;
	}

	public T redo(T current) throws EmptyStackException {
		if (!this.canRedo())
			throw new EmptyStackException();

		T obj = this.redoStack.pop();
		this.undoStack.push(current);

		return obj;
	}

	public void add(T obj) {
		this.undoStack.push(obj);
		this.redoStack.clear();
	}

}
