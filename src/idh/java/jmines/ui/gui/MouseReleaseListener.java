package idh.java.jmines.ui.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
 * Dieses Interface dient lediglich dazu, die Schnittstelle zum
 * herkömmlichen MouseListener zu vereinfachen (wir brauchen nämlich
 * nur eine der fünf Methoden).
 */
public interface MouseReleaseListener extends MouseListener {

	@Override
	default void mouseClicked(MouseEvent e) {
		// leer
	}
	
	@Override
	default void mouseEntered(MouseEvent e) {
		// leer
	}
	
	@Override
	default void mouseExited(MouseEvent e) {
		// leer
	}
	
	@Override
	default void mousePressed(MouseEvent e) {
		// leer
	}
	
	@Override
	public void mouseReleased(MouseEvent e);

}
