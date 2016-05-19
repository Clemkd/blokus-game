package navigation;

import gui.DrawableInterface;

public abstract class Page implements DrawableInterface{
	
	/**
	 * Constante d'accès au dossier de ressource des images
	 */
	public static final String PATH_RESOURCES_IMAGES = "./resources/images/";
	
	/**
	 * Constante d'accès au dossier de resources des boutons
	 */
	public static final String PATH_RESOURCES_BOUTONS = "./resources/boutons/";
	
	/**
	 * Méthode de chargement du contenu de l'état de jeu
	 */
	public abstract void loadContents();
	
	/**
	 * Méthode de déchargement du contenu de l'état de jeu
	 */
	public abstract void unloadContents();
}
