package idh.java.jmines;

import idh.java.jmines.model.Cell;
import idh.java.jmines.model.GameState;
import idh.java.jmines.ui.JMinesUi;
import idh.java.jmines.ui.UiCallback;
import idh.java.jmines.ui.cli.JMinesCli;
import idh.java.jmines.ui.gui.JMinesGui;

public class JMines {
	
	private JMinesUi ui; //a reference to the user interface
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
		//initialize the user interface (GUI or CLI!)
		if (gui)
			ui = new JMinesGui(); //use GUI
		else
			ui = new JMinesCli(); //use CLI
		
		//register reveal callback
		ui.registerRevealCallback(new UiCallback() {
			@Override
			public GameState call(int x, int y) {
				return reveal(x, y); //actual method call here
			}
		});
		
		//register mark callback
		ui.registerMarkCallback(new UiCallback() {
			@Override
			public GameState call(int x, int y) {
				return mark(x, y); //actual method call here
			}
		});
		
		//register new game callback
		ui.registerNewGameCallback(new UiCallback() {
			@Override
			public GameState call(int x, int y) {
				return newGame(x, y); //actual method call here
			}
		});
		
		//initialize ui
		ui.init();
	}
	
	
	//reveal a cell!
	private GameState reveal(int x, int y) {
		Cell[][] board = state.getBoard();
		
		if (!board[x][y].isRevealed()) {
			board[x][y].setRevealed(true);
			if (board[x][y].isMine()) {
				state.setLost();
			} else if (board[x][y].getNumber() == 0) {
				//reveal all neighbors
				for (int y1 = Math.max(y-1, 0); y1 <= Math.min(y+1, board.length-1); y1++) {
					for (int x1 = Math.max(x-1, 0); x1 <= Math.min(x+1, board[y].length-1); x1++) {
						reveal(x1, y1); //reveal neighbor
					}
				}
			}
		}
		
		state.setWon(isWon());
		return state;
	}
	
	
	private boolean isWon() {
		//is won?
		for (int y1 = 0; y1 < state.getBoard().length; y1++) {
			for (int x1 = 0; x1 < state.getBoard()[y1].length; x1++) {
				if (!state.getBoard()[x1][y1].isRevealed() && !state.getBoard()[x1][y1].isMine()) {
					return false;
				} 
			}
		}
		return true;
	}
	
	
	//mark a cell!
	private GameState mark(int x, int y) {
		state.getBoard()[x][y].setMarked(!state.getBoard()[x][y].isMarked());
		return state;
	}
	
	//start a new game!
	private GameState newGame(int dimensions, int difficulty) {
		state = new GameState(dimensions, difficulty);
		return state;
	}

}
