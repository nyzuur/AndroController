package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.ADB;

import com.android.ddmlib.IDevice;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class MainView  {

	private JFrame frame;

	/**
	 * Create the frame.
	 */
	public MainView(ADB adb) {
		frame = new SelectionView(adb);
		frame.setVisible(true);
		
	}

	
	

}
