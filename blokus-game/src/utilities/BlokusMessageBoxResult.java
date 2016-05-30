package utilities;

public enum BlokusMessageBoxResult
{
	YES("YES"),
	VALID("VALID"),
	NO("NO"),
	CANCEL("CANCEL");
	
	private String title;
	
	BlokusMessageBoxResult(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return this.title;
	}
}
