package idh.java.jmines.ui;

/**
 * This interface defines the methods needed in a
 * MineSweeper user interface. The methods to register
 * all the UI callbacks is all there is. That's it.
 */
public interface JMinesUi {
	
	/**
	 * Registers callback for revealing a cell
	 * @param callback
	 */
	public void registerRevealCallback(UiCallback callback);
	
	/**
	 * Registers callback for marking a cell
	 * @param callback
	 */
	public void registerMarkCallback(UiCallback callback);
	
	/**
	 * Registers callback for starting a new game
	 * @param callback
	 */
	public void registerNewGameCallback(UiCallback callback);
	
	/**
	 * Initializes the UI
	 */
	public void init();

}
