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
	
	/**
	 * L'état du  jeu: Page de sélection de l'IA
	 */
	public static Page iaPage = new IASelectionPage();
	
	/**
	 * L'état du jeu: Page de jeu
	 */
	public static Page gamePage = new GamePage();
	
	
	/**
	 * L'état du jeu: page des options
	 */
	
	/**
	 * L'état du jeu: page de tutorial
	 */
	public static Page tutorialPage = new TutorialPage();
	
	public static Page optionPage = new OptionPage();
	/**
	 * Change l'état de jeu et navigue vers cet état
	 * @param p Le nouvel état de jeu
	 */
	public static void NavigateTo(Page p)
	{
		Page oldpage = page;
		page = p;
		
		if(oldpage != null && p != oldpage)
		{
			oldpage.unloadContents();
		}
		
		page.loadContents();
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
