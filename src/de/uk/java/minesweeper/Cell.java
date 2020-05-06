package de.uk.java.minesweeper;

/**
 * This class represents a cell (or field)
 * of the MineSweeper game board.
 * It's just a simple data class / POJO
 */
public class Cell {

	private boolean mine; //is this a mine?
	private boolean marked; //is this cell marked?
	private boolean revealed; //is this cell revealed?
	private int number; //how many neighboring mines are there around this cell?
	private int x; //the x coordinate of this cell in the game board
	private int y; //the y coordinate of this cell in the game board
	
	
	//// CONSTRUCTOR ////
	
	public Cell (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	//// GETTERS & SETTERS ////

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public boolean isRevealed() {
		return revealed;
	}

	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		if (!revealed) {
			return marked ? "!" : "\u00B7";
		} else if (mine) {
			return "X";
		} else {
			return number == 0 ? " " : number + "";
		}
	}

}












