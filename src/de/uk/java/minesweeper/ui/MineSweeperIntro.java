package de.uk.java.minesweeper.ui;


public class MineSweeperIntro {
	
	private static final String BANNER_STRING = 
			"           ███╗   ███╗██╗███╗   ██╗███████╗\n" + 
			"           ████╗ ████║██║████╗  ██║██╔════╝\n" + 
			"           ██╔████╔██║██║██╔██╗ ██║█████╗\n" + 
			"           ██║╚██╔╝██║██║██║╚██╗██║██╔══╝\n" + 
			"           ██║ ╚═╝ ██║██║██║ ╚████║███████╗\n" + 
			"           ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚══════╝\n" + 
			"███████╗██╗    ██╗███████╗███████╗██████╗ ███████╗██████╗\n" + 
			"██╔════╝██║    ██║██╔════╝██╔════╝██╔══██╗██╔════╝██╔══██╗\n" + 
			"███████╗██║ █╗ ██║█████╗  █████╗  ██████╔╝█████╗  ██████╔╝\n" + 
			"╚════██║██║███╗██║██╔══╝  ██╔══╝  ██╔═══╝ ██╔══╝  ██╔══██╗\n" + 
			"███████║╚███╔███╔╝███████╗███████╗██║     ███████╗██║  ██║\n" + 
			"╚══════╝ ╚══╝╚══╝ ╚══════╝╚══════╝╚═╝     ╚══════╝╚═╝  ╚═╝";

	
	public static void play() {
		//print some empty lines
		for (int i = 0; i < 100; i++) { System.out.println(); }
		//slowly print intro ascii banner
		for (String line : BANNER_STRING.split("\n")) {
			System.out.println(line);
			try { Thread.sleep(200); } catch (InterruptedException e) {}
		}
	}
	
}
