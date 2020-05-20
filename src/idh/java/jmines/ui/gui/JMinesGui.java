package idh.java.jmines.ui.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import idh.java.jmines.JMines;
import idh.java.jmines.model.GameState;
import idh.java.jmines.ui.JMinesUi;
import idh.java.jmines.ui.UiCallback;

public class JMinesGui extends JFrame implements JMinesUi, ActionListener, MouseReleaseListener {
	
	private static final long serialVersionUID = 1L;
	
	//UI callbacks for interaction with game core
	private UiCallback gameCore;
	
	private JPanel boardPanel;
	private JLabel welcomeScreen;
	
	
	@Override
	public void init() {
		
		// Look&Feel der Anwendung auf das "System-Look&Feel" des
		// Betriebssystems festlegen...
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Warning: Cannot load system Look&Feel");
		}
		
		//ein paar Einstellungen zum Fenster
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //was passiert beim Schließen des Fensters?
		setTitle("JMines"); //der Text für den Titel des Fensters
		
		//// Fenster-Menü initialisieren ////
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Game");
		
		// Menu-Item für "new game" erstellen und Aktion festlegen
		JMenuItem menuItemNewGame = new JMenuItem("New Game");
		menuItemNewGame.setActionCommand("new");
		menuItemNewGame.addActionListener(this);
		
		// Menu-Item für "quit game" erstellen und Aktion festlegen
		JMenuItem menuItemQuitGame = new JMenuItem("Quit Game");
		menuItemQuitGame.setActionCommand("quit");
		menuItemQuitGame.addActionListener(this);
		
		//Menü zusammensetzen
		menuBar.add(menu);
		menu.add(menuItemNewGame);
		menu.add(menuItemQuitGame);
		setJMenuBar(menuBar);
		
		//Titel-Grafik
		welcomeScreen = new JLabel();
		welcomeScreen.setIcon(AssetsHelper.getIcon("title-graphic.png"));
		welcomeScreen.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeScreen.setVerticalAlignment(SwingConstants.CENTER);
		showWelcomeScreen();
		
		pack(); //Fenstergröße anpassen
		setLocationRelativeTo(null); //Fenster auf Bildschrim zentrieren
		
		//Fenster sichtbar machen
		setVisible(true);
	}
	
	
	/*
	 * Diese Methode enthält die Funktionalität zur
	 * Darstellung des aktuellen Spielzustands in der GUI
	 */
	private void draw(GameState state) {
		//ist das board panel noch nicht initialisiert?
		if (boardPanel == null) initBoard(state);
		
		for (int y = 0; y < state.getDimensions(); y++) {
			for (int x = 0; x < state.getDimensions(); x++) {
				// den richtigen Button finden
				CellButton cell = (CellButton) boardPanel.getComponent((state.getDimensions()*y) + x);
				// Button-Zustand updaten!
				cell.updateState(state.isWon());
			}
		}
	}
	
	
	/*
	 * Initialisiert ein neues board panel zur Darstellung des Spielfelds
	 */
	private void initBoard(GameState state) {
		System.out.println("[JMines] initializing: " + state);
		// neues Panel für Buttons mit GridLayout anlegen (Größe nach dimensions)
		boardPanel = new JPanel(new GridLayout(state.getDimensions(), state.getDimensions(), 2, 2));
		boardPanel.setBackground(new Color(200, 200, 200)); // panel background
		boardPanel.setBorder(new EmptyBorder(2, 2, 2, 2)); // panel padding
		
		for (int y = 0; y < state.getDimensions(); y++) {
			for (int x = 0; x < state.getDimensions(); x++) {
				// CellButton-Instanz erzeugen (Anzahl angrenzender Minen übergeben)
				CellButton button = new CellButton(state.getBoard()[x][y]);
				button.setName("cell-" + x + ":" + y); // "Name" des Buttons (den merkt er sich)
				button.addMouseListener(this); //MouseListener für Reaktion auf Klick
				//Button dem Panel hinzufügen
				boardPanel.add(button);
			}
		}
		
		setPreferredSize(null); //unset preferred window size
		getContentPane().remove(welcomeScreen); //Welcome-Screen entfernen
		getContentPane().add(boardPanel); //Panel dem Fenster hinzufügen
		pack(); //Fenstergröße anpassen
		setLocationRelativeTo(null); //Fenster auf Bildschrim zentrieren
	}
	
	
	@Override
	public void registerUiCallback(UiCallback callback) {
		this.gameCore = callback;
	}


	/*
	 * Hier laufen alle Events ein, für die wir den ActionListener
	 * registriert haben (hier: das Fenstermenü).
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "new":
			// reset board panel and show welcome screen
			showWelcomeScreen();
			// ask for board dimensions
		    Integer dimensions = (Integer) JOptionPane.showInputDialog(null, "Board dimensions (width = height):",
		        "Setup New Game", JOptionPane.QUESTION_MESSAGE, null,
		        JMines.OPTIONS_DIMENSIONS, // Array of choices
		        JMines.OPTIONS_DIMENSIONS[5]); // Default choice
		    if (dimensions == null) return;
		    // ask for difficulty
		    Integer difficulty = (Integer) JOptionPane.showInputDialog(null, "Game difficulty:",
		        "Setup New Game", JOptionPane.QUESTION_MESSAGE, null,
		        JMines.OPTIONS_DIFFICULTY, // Array of choices
		        JMines.OPTIONS_DIFFICULTY[4]); // Default choice
		    if (difficulty == null) return;
		    // create and draw new game
			draw(gameCore.callNewGame(dimensions, difficulty));
			break;
		case "quit":
			System.exit(0);
			break;
		default:
			break;
		}
	}


	/*
	 * Hier laufen alle Events für Mausklicks ein, für die wir den MouseClickListener
	 * registriert haben (hier: die Cell-Buttons des Spielfelds).
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		//handelt es sich um einen Linksklick?
		boolean leftClick = e.getButton() == MouseEvent.BUTTON1;
		
		if (e.getComponent().getName().startsWith("cell-")) {
			String[] xy = e.getComponent().getName().replaceAll("^cell-", "").split("\\:");
			int x = Integer.parseInt(xy[0]);
			int y = Integer.parseInt(xy[1]);
			
			if (leftClick) {
				draw(gameCore.callReveal(x, y)); // bei Linksklick aufdecken
			} else {
				draw(gameCore.callMark(x, y)); // in allen anderen Fällen markieren
			}
		}
	}
	
	
	private void showWelcomeScreen() {
		if (boardPanel != null) {
			getContentPane().remove(boardPanel);
			boardPanel = null;
		}
		setPreferredSize(new Dimension(400, 400));
		getContentPane().add(welcomeScreen);
		pack();
		setLocationRelativeTo(null); //Fenster auf Bildschrim zentrieren
	}
	

}











