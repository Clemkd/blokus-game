package utilities;

public enum BlokusMessageBoxResult
{
	YES("YES"),
	VALID("VALID"),
	NO("NO"),
	CANCEL("CANCEL");
	
	/**
	 * La commande
	 */
	private String actionCommand;
	
	/**
	 * Constructeur d'un BlokusMessageBoxResult
	 * 
	 * @param actionCommand la commande
	 */
	BlokusMessageBoxResult(String actionCommand)
	{
		this.actionCommand = actionCommand;
	}
	
	/**
	 * Getter de la commande
	 * 
	 * @return la commande
	 */
	public String getActionCommand()
	{
		return this.actionCommand;
	}
}
