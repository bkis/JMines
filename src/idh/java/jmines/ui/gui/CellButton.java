package idh.java.jmines.ui.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import idh.java.jmines.JMines;
import idh.java.jmines.model.Cell;

public class CellButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	// Pfad zu GUI assets (icons)
	private static final String ASSETS_PATH = "/idh/java/jmines/ui/gui/assets/";
	
	// ImageIcons für Icon-Grafiken
	private static final ImageIcon ICON_MARKED = getIcon(ASSETS_PATH + "icon-marked.png");
	private static final ImageIcon ICON_EXPLOSION = getIcon(ASSETS_PATH + "icon-explosion.png");
	
	// Schriftart für alle Zellen-Button
	private static final Font CELL_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	
	// Farben für Anzahl der angrenzenden Minen
	private static final Color[] COLORS_FG = {
		null,
		Color.BLUE,
		Color.GREEN.darker(),
		Color.RED,
		Color.BLUE.darker(),
		Color.RED.darker(),
		Color.CYAN.darker(),
		Color.BLACK,
		Color.GRAY.darker()
	};
	
	// Farben für Button-Hintergrund
	private static final Color[] COLORS_BG = {
		new Color(240, 240, 240), // unrevealed: light gray
		new Color(220, 220, 220)  // revealed: gray
	};
	
	private Cell cellModel;
	
	
	public CellButton(Cell cellModel) {
		this.cellModel = cellModel;
		setForeground(COLORS_FG[cellModel.getNumber()]);
		setBackground(COLORS_BG[0]);
		setFont(CELL_FONT);
		setPreferredSize(new Dimension(42,42));
		setOpaque(true);
		setBorderPainted(false);
		setRolloverEnabled(false);
	}
	
	public void update() {
		if (cellModel.isRevealed()) {
			setBackground(COLORS_BG[1]);
		}
		
		if (!cellModel.isRevealed() && cellModel.isMarked()) {
			setText("");
			setIcon(ICON_MARKED);
		} else if (!cellModel.isRevealed()) {
			setText("");
			setIcon(null);
		} else if (cellModel.isMine()) {
			setText("");
			setIcon(ICON_EXPLOSION);
		} else {
			if (cellModel.getNumber() == 0) {
				setText("");
			} else {
				setText(cellModel.getNumber() + "");
			}
			setIcon(null);
		}
	}
	
	private static ImageIcon getIcon(String resourcePath) {
		try {
			return new ImageIcon(ImageIO.read(JMines.class.getResource(resourcePath)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
