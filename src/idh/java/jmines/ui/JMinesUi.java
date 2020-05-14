package idh.java.jmines.ui;

/**
 * This interface defines the methods needed in a
 * MineSweeper user interface. The methods to register
 * all the UI callbacks is all there is. That's it.
 */
public interface JMinesUi {
	
	/**
	 * Registers UiCallback instance for UI-core-communication
	 * @param callback
	 */
	public void registerUiCallback(UiCallback callback);
	
	/**
	 * Initializes the UI
	 */
	public void init();

}
