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
	
	public GameState(int dimensions, int difficulty) {
		super();
		this.dimensions = dimensions;
		this.difficulty = difficulty;
		initBoard();
	}
	
	
	/*
	 * Initialize the game board!
	 */
	private void initBoard() {
		board = new Cell[dimensions][dimensions]; //initialize board object
		mines = calcMineCount(dimensions, difficulty); //calculate number of mines to deploy
		int minesLeft = mines;
		int cells = (int) Math.pow(dimensions, 2);
		
		//create board and place mines!
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				board[x][y] = new Cell(x, y); //init cell
				//place mine?
				if (minesLeft > 0 && ((double)minesLeft / (double)cells) > Math.random()) {
					board[x][y].setMine(true);
					minesLeft--;
				}
				//board[x][y].setRevealed(true); //TEMP
				cells--;
			}
		}
		
		//setup number of neighboring mines for each cell!
		//iterate over cells
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				//if cell is not a mine, continue with next cell
				if (!board[x][y].isMine()) continue;
				
				//iterate over neighboring cells
				for (int y1 = Math.max(y-1, 0); y1 <= Math.min(y+1, board.length-1); y1++) {
					for (int x1 = Math.max(x-1, 0); x1 <= Math.min(x+1, board[y].length-1); x1++) {
						//if not a mine, increase number of cell by 1
						if (!board[x1][y1].isMine()) {
							board[x1][y1].setNumber(board[x1][y1].getNumber() + 1);
						}
					}
				}
				
			}
		}
	}
	
	private int calcMineCount(int dimensions, int difficulty) {
		return dimensions * difficulty / (10 / 3);
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
		if (won) revealAll();
	}
	
	
	private void revealAll() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j].setRevealed(true);
			}
		}
	}


	public boolean isLost() {
		return lost;
	}


	public void setLost() {
		this.lost = true;
		revealAll();
	}
	
	
	@Override
	public String toString() {
		return "JMine GameState [dim:" + dimensions + ", diff:" + difficulty + "]";
	}
	
	
}









