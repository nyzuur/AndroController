
/*
 * Copyright (C) 2015  Niyazi Uður
 * 
 *		This program is free software; you can redistribute it and/or
 *		modify it under the terms of the GNU General Public License
 *		as published by the Free Software Foundation; either version 2
 *		of the License, or (at your option) any later version.
 *		
 *		This program is distributed in the hope that it will be useful,
 *		but WITHOUT ANY WARRANTY; without even the implied warranty of
 *		MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *		GNU General Public License for more details.
 */


package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import src.ADB;
import utils.Log;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class MainView {

	private JFrame frame;
	boolean switchedToMainView=false;
	boolean frameNotExited=true;
	

	/**
	 * Create the frame.
	 * @param asl 
	 */
	public MainView(ADB adb) {
		
		frame = new SelectionView(adb,this);
		frame.setVisible(true);
	}
	public void switchToMainView(IDevice device) {
		
		frameNotExited = false;
		switchedToMainView=true;
		frame.setVisible(false);
		frame.dispose();
		frame = new View(device);
		frame.setVisible(true);
		try {
			loop();
		} catch (TimeoutException | AdbCommandRejectedException | IOException e) {
			Log.p(e.toString(), MainView.class);
		}
		
	}
	
	void loop() throws TimeoutException, AdbCommandRejectedException, IOException{
		if(switchedToMainView){
			Timer timer = new Timer(0, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					View v = (View) frame;
					try {
						v.updateScreenShot();
					} catch (TimeoutException | AdbCommandRejectedException | IOException | ShellCommandUnresponsiveException | SyncException e1) {
						Log.p("" + e1, MainView.class);
					}
					
					
				}
			});
			
			timer.setRepeats(true);
			timer.start();
			
		}
	}
	

}
