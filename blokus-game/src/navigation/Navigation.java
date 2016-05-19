package navigation;

public class Navigation {
	
	/**
	 * L'état de jeu
	 */
	private static Page page;
	
	/**
	 * L'état de jeu : Page d'accueil
	 */
	public static Page homePage = new HomePage();
	
	public static Page iaPage = new IASelectionPage();
	
	public static Page gamePage = new GamePage();
	
	/**
	 * Change l'état de jeu et navigue vers cet état
	 * @param p Le nouvel état de jeu
	 */
	public static void NavigateTo(Page p)
	{
		page = p;
	}
	
	/**
	 * Obtient l'état courant du jeu
	 * @return L'état courant du jeu
	 */
	public static Page getPage()
	{
		return page;
	}
}
