package src;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseActionListener implements MouseListener,MouseMotionListener,MouseWheelListener{
	boolean mouseClickDown = false;
	Point firstClickedLocation;
	
	Dimension dimensions;
	
	/**
	
	 * @param d is dimension for converting raw points to real device points
	 */
	public MouseActionListener(Dimension d) {
		this.dimensions = d;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseClickDown = true;
		firstClickedLocation = e.getPoint();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseClickDown = false;

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
	
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

}
