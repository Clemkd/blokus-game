package navigation;

import gui.DrawableInterface;

public abstract class Page implements DrawableInterface{
	
	public static final String PATH_RESOURCES_IMAGES = "./resources/images/";
	public static final String PATH_RESOURCES_BOUTONS = "./resources/boutons/";
	
	/**
	 * Méthode de chargement du contenu de l'état de jeu
	 */
	public abstract void LoadContents();
	
	/**
	 * Méthode de déchargement du contenu de l'état de jeu
	 */
	public abstract void UnloadContents();
}
