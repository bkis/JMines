package de.uk.java.minesweeper.ui;

import java.util.Scanner;

import de.uk.java.minesweeper.Cell;
import de.uk.java.minesweeper.GameState;

/**
 * This is an implementation of the MineSweeperUi interface.
 * It offer a command line interface to play the MineSweeper game.
 */
public class MineSweeperCli implements MineSweeperUi {
	
	//UI callbacks for interaction with game core
	private UiCallback reveal;
	private UiCallback mark;
	private UiCallback newGame;
	
	//the Scanner instance used to get user input
	private Scanner input;
	
	
	@Override
	public void init() {
		input = new Scanner(System.in); //init scanner
		MineSweeperIntro.play(); //play game intro
		
		String currInput; //to store user input
		int dim = -1; //to store chosen dimensions
		int diff = -1; //to store chosen difficulty
		
		//ask for dimensions
		do {
			System.out.print("Board size [5-10]: ");
			currInput = input.nextLine();
			if (currInput.matches("([5-9]|10)")) {
				dim = Integer.parseInt(currInput);
			}
		} while (dim == -1);
		
		//ask for difficulty
		do {
			System.out.print("Difficulty [1-10]: ");
			currInput = input.nextLine();
			if (currInput.matches("([1-9]|10)")) {
				diff = Integer.parseInt(currInput);
			}
		} while (diff == -1);
		
		//start new game, draw game state!
		draw(newGame.call(dim, diff));
		
		//ask for command
		do {
			System.out.print("\n[r]eveal or [m]ark: ");
			String cmd = input.nextLine().replaceAll("\\s", "").toLowerCase();
			if (!isValidCmd(cmd)) continue;
			char[] cmdsplit = cmd.toCharArray();
			char c = cmdsplit[0];
			int x = Integer.parseInt(cmdsplit[1] + "");
			int y = Integer.parseInt(cmdsplit[2] + "");
			if (c == 'r') {
				draw(reveal.call(x, y));
			} else {
				draw(mark.call(x, y));
			}
		} while (true);
	}
	
	
	private boolean isValidCmd(String cmd) {
		return cmd.matches("[rm]\\d{2}.*");
	}
	
	
	/**
	 * draw game state
	 * @param state
	 */
	public void draw(GameState state) {
		//clear screen
		cls();
		
		Cell[][] board = state.getBoard();
		
		//print board with koordinates
		System.out.print("   ");
		
		for (int i = 0; i < board.length; i++)
			System.out.print(" " + i);
		
		System.out.println();
		System.out.print("  +-");
		
		for (int i = 0; i < board.length; i++)
			System.out.print("--");

		System.out.print("+");
		System.out.println();
		
		for (int y = 0; y < board.length; y++) {
			System.out.print(y + " |");
			for (int x = 0; x < board[y].length; x++) {
				System.out.print(" " + board[x][y]);
			}
			System.out.print(" |");
			System.out.println();
		}
		
		System.out.print("  +-");
		
		for (int i = 0; i < board.length; i++)
			System.out.print("--");
		
		System.out.print("+");
		
		if (state.isLost()) {
			System.out.println("YOU LOST!");
		} else if (state.isWon()) {
			System.out.println("YOU WON!");
		}
	}
	
	private void cls() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}
	
	@Override
	public void registerRevealCallback(UiCallback callback) {
		reveal = callback; //reference to callback as a field
	}

	@Override
	public void registerMarkCallback(UiCallback callback) {
		mark = callback; //reference to callback as a field
	}

	@Override
	public void registerNewGameCallback(UiCallback callback) {
		newGame = callback; //reference to callback as a field
	}

}
