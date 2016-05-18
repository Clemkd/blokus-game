package gui;

public class Navigation {
	
	private static Page page;
	public static Page homePage = new HomePage();
	
	public static void NavigateTo(Page p)
	{
		page = p;
	}
	
	public static Page getPage()
	{
		return page;
	}
}
