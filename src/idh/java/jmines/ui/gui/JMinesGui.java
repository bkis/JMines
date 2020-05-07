package idh.java.jmines.ui.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import idh.java.jmines.model.GameState;
import idh.java.jmines.ui.JMinesUi;
import idh.java.jmines.ui.MouseClickListener;
import idh.java.jmines.ui.UiCallback;

public class JMinesGui extends JFrame implements JMinesUi, ActionListener, MouseClickListener {
	
	private static final long serialVersionUID = 1L;
	
	// Callbacks
	private UiCallback reveal;
	private UiCallback mark;
	private UiCallback newGame;
	
	private JPanel boardPanel;
	
	
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
		setPreferredSize(new Dimension(500, 500)); //die initiale Fenstergröße
		setTitle("MineSweeper"); //der Text für den Titel des Fensters
		
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
				CellButton cell = (CellButton) boardPanel.getComponent((10*y) + x);
				// Button-Zustand updaten!
				cell.update();
			}
		}
	}
	
	
	/*
	 * Initialisiert ein neues board panel zur Darstellung des Spielfelds
	 */
	private void initBoard(GameState state) {
		// neues Panel für Buttons mit GridLayout anlegen (Größe nach dimensions)
		boardPanel = new JPanel(new GridLayout(state.getDimensions(), state.getDimensions(), 2, 2));
		boardPanel.setBackground(new Color(200, 200, 200)); // panel background
		boardPanel.setBorder(new EmptyBorder(2, 2, 2, 2)); // panel padding
		
		for (int y = 0; y < state.getDimensions(); y++) {
			for (int x = 0; x < state.getDimensions(); x++) {
				// CellButton-Instanz erzeugen (Anzahl angrenzender Minen übergeben)
				CellButton button = new CellButton(state.getBoard()[x][y]);
				button.setName("cell-" + x + "-" + y); // "Name" des Buttons (den merkt er sich)
				button.addMouseListener(this); //MouseListener für Reaktion auf Klick
				//Button dem Panel hinzufügen
				boardPanel.add(button);
			}
		}
		
		getContentPane().add(boardPanel); //Panel dem Fenster hinzufügen
		pack(); //Fenstergröße anpassen
	}
	
	
	@Override
	public void registerRevealCallback(UiCallback callback) {
		reveal = callback;
	}

	@Override
	public void registerMarkCallback(UiCallback callback) {
		mark = callback;
	}

	@Override
	public void registerNewGameCallback(UiCallback callback) {
		newGame = callback;
	}


	/*
	 * Hier laufen alle Events ein, für die wir den ActionListener
	 * registriert haben (hier: das Fenstermenü).
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "new":
			draw(newGame.call(10, 5));
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
	public void mouseClicked(MouseEvent e) {
		//handelt es sich um einen Linksklick?
		boolean leftClick = e.getButton() == MouseEvent.BUTTON1;
		
		if (e.getComponent().getName().startsWith("cell-")) {
			String xy = e.getComponent().getName().replaceAll("\\D", "");
			int x = xy.charAt(0) - 48;
			int y = xy.charAt(1) - 48;
			
			if (leftClick) {
				draw(reveal.call(x, y)); // bei Linksklick aufdecken
			} else {
				draw(mark.call(x, y)); // in allen anderen Fällen markieren
			}
		}
	}
	

}











