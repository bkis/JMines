package idh.java.jmines;

import idh.java.jmines.model.Cell;
import idh.java.jmines.model.GameState;
import idh.java.jmines.ui.JMinesUi;
import idh.java.jmines.ui.UiCallback;
import idh.java.jmines.ui.cli.JMinesCli;
import idh.java.jmines.ui.gui.JMinesGui;

public class JMines {
	
	public static final Integer[] OPTIONS_DIMENSIONS = 
		{5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
	
	public static final Integer[] OPTIONS_DIFFICULTY = 
		{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	
	private GameState state; //a reference to the current game state
	
	
	/*
	 * main method starts the program
	 */
	public static void main(String[] args) {
		//create an instance of the game (constructor initializes the game)
		new JMines(args.length == 0 || !args[0].equalsIgnoreCase("cli"));
	}
	
	/**
	 * This constructor initializes the MineSweeper game
	 */
	public JMines(boolean gui) {
		JMinesUi ui; // a reference to the user interface
		             // (only needed for setting it up and
		             // registering the callbacks!)
		//initialize the user interface (GUI or CLI!)
		if (gui)
			ui = new JMinesGui(); //use GUI
		else
			ui = new JMinesCli(); //use CLI
		
		//register reveal callback
		ui.registerUiCallback(new UiCallback() {
			@Override
			public GameState callReveal(int x, int y) {
				return reveal(x, y); //actual method call here
			}
			
			@Override
			public GameState callMark(int x, int y) {
				return mark(x, y); //actual method call here
			}
			
			@Override
			public GameState callNewGame(int dimensions, int difficulty) {
				return newGame(dimensions, difficulty); //actual method call here
			}
		});
		
		//initialize ui
		ui.init();
	}
	
	
	//reveal a cell!
	private GameState reveal(int x, int y) {
		Cell[][] board = state.getBoard();
		
		// reveal
		reveal(x, y, board);
		
		// check if game is won
		if (!state.isLost()) {
			boolean won = true;
			for (int y1 = 0; y1 < board.length; y1++) {
				for (int x1 = 0; x1 < board[y1].length; x1++) {
					if (!board[x1][y1].isRevealed() && !board[x1][y1].isMine()) {
						won = false;
						break;
					} 
				}
			}
			if (won) revealAll(); // reveal all cells if game is won
			state.setWon(won); // game is won???
		}
		
		return state;
	}
	
	
	// the recursive part of the reveal logic
	private void reveal(int x, int y, Cell[][] board) {
		if (!board[x][y].isRevealed()) {
			board[x][y].setRevealed(true);
			if (board[x][y].isMine()) {
				revealAll();  // reveal all cells
				state.setLost(); // game is lost!
			} else if (board[x][y].getNumber() == 0) {
				//reveal all neighbors
				for (int y1 = Math.max(y-1, 0); y1 <= Math.min(y+1, board.length-1); y1++) {
					for (int x1 = Math.max(x-1, 0); x1 <= Math.min(x+1, board[y].length-1); x1++) {
						reveal(x1, y1); //reveal neighbor
					}
				}
			}
		}
	}
	
	//mark a cell!
	private GameState mark(int x, int y) {
		state.getBoard()[x][y].setMarked(!state.getBoard()[x][y].isMarked());
		return state;
	}
	
	//start a new game!
	private GameState newGame(int dimensions, int difficulty) {
		// initialize board object
		Cell[][] board = new Cell[dimensions][dimensions];
		// calculate number of mines to deploy (this is not ideal!)
		int mines = dimensions * difficulty / (10 / 3);
		// variable to count mines that are left to "deploy"
		int minesLeft = mines;
		// calculate number of cells on board
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
		
		// create new GameState and assign it to class attribute
		state = new GameState(board, difficulty, mines);
		// return new game state
		return state;
	}
	
	private void revealAll() {
		for (int i = 0; i < state.getBoard().length; i++) {
			for (int j = 0; j < state.getBoard()[i].length; j++) {
				state.getBoard()[i][j].setRevealed(true);
			}
		}
	}

}
