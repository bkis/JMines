package idh.java.jmines.model;


/**
 * This class represents the MineSweeper game state.
 * It's (mainly) just a simple data class / POJO.
 */
public class GameState {
	
	private Cell[][] board; //the game board
	private int dimensions; //the game board dimensions
	private int difficulty; //the game difficulty
	private int mines; //the number of mines on the game board
	private int marked; //the number of marked cells
	private boolean won; //is the game won?
	private boolean lost; //is the game lost?
	
	
	//// CONSTRUCTOR ////
	
	public GameState(Cell[][] board, int difficulty, int mines) {
		this.board = board;
		this.dimensions = board.length;
		this.difficulty = difficulty;
		this.mines = mines;
	}


	public Cell[][] getBoard() {
		return board;
	}


	public void setBoard(Cell[][] board) {
		this.board = board;
	}


	public int getDimensions() {
		return dimensions;
	}


	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}


	public int getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}


	public int getMines() {
		return mines;
	}


	public void setMines(int mines) {
		this.mines = mines;
	}


	public int getMarked() {
		return marked;
	}


	public void setMarked(int marked) {
		this.marked = marked;
	}


	public boolean isWon() {
		return won;
	}


	public void setWon(boolean won) {
		this.won = won;
	}


	public boolean isLost() {
		return lost;
	}


	public void setLost() {
		this.lost = true;
	}
	
	
	@Override
	public String toString() {
		return "JMine GameState [dim:" + dimensions + ", diff:" + difficulty + "]";
	}
	
	
}









