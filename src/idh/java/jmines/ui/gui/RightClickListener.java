package idh.java.jmines.ui.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
 * Dieses Interface dient lediglich dazu, die Schnittstelle zum
 * herkömmlichen MouseListener zu vereinfachen (wir brauchen nämlich
 * nur eine der fünf Methoden).
 */
public interface RightClickListener extends MouseListener {

	@Override
	default void mouseClicked(MouseEvent e) {}
	
	@Override
	default void mouseEntered(MouseEvent e) {}
	
	@Override
	default void mouseExited(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e);
	
	@Override
	default void mouseReleased(MouseEvent e) {}

}
