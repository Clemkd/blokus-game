package utilities;

public enum BlokusMessageBoxResult
{
	YES("YES"),
	VALID("VALID"),
	NO("NO"),
	CANCEL("CANCEL");
	
	private String actionCommand;
	
	BlokusMessageBoxResult(String actionCommand)
	{
		this.actionCommand = actionCommand;
	}
	
	public String getActionCommand()
	{
		return this.actionCommand;
	}
}
