package idh.java.jmines.ui;

import idh.java.jmines.model.GameState;

/**
 * This interface represents a (generic) UI callback
 * for interation with the MineSweeper game core
 */
public interface UiCallback {
	
	/**
	 * This is a callback method from the UI to the game core.
	 * It triggers revealing a mine field / cell.
	 * @param x x-coordinate of the field
	 * @param y y-coordinate of the field
	 * @return The current game state as GameState object
	 */
	public GameState callReveal(int x, int y);
	
	/**
	 * This is a callback method from the UI to the game core.
	 * It triggers marking a mine field / cell.
	 * @param x x-coordinate of the field
	 * @param y y-coordinate of the field
	 * @return The current game state as GameState object
	 */
	public GameState callMark(int x, int y);
	
	/**
	 * This is a callback method from the UI to the game core.
	 * It triggers revealing a mine field / cell.
	 * @param x x-coordinate of the field
	 * @param y y-coordinate of the field
	 * @return The current game state as GameState object
	 */
	public GameState callNewGame(int dimensions, int difficulty);

}
