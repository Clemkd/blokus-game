package navigation;

import gui.DrawableInterface;

public abstract class Page implements DrawableInterface{
	
	/**
	 * Constante d'accès au dossier de ressource des images
	 */
	public static final String PATH_RESOURCES_IMAGES = "/images/";
	
	/**
	 * Constante d'accès au dossier de resources des boutons
	 */
	public static final String PATH_RESOURCES_BOUTONS = "/boutons/";
	
	/**
	 * Constante d'accès au dossier de resources des fonts
	 */
	public static final String PATH_RESOURCES_FONTS = "/fonts/";
	
	
	/**
	 * Méthode de chargement du contenu de l'état de jeu
	 */
	public abstract void loadContents();
	
	/**
	 * Méthode de déchargement du contenu de l'état de jeu
	 */
	public abstract void unloadContents();
	
	protected boolean enabled;
	
	public void setEnabled(boolean state)
	{
		this.enabled = state;
	}
}
