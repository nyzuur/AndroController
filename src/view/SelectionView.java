
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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.android.ddmlib.IDevice;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import src.ADB;

import java.awt.Window.Type;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SelectionView extends JFrame implements MouseListener {

	private JPanel contentPane;
	private JLabel lblCihazSeiniz;
	private JComboBox<String> comboBox;
	private JButton btnConnect;
	private JButton btnRefresh;
	private IDevice[] devices;
	private ADB adb;
	private MainView mainView;
	/**
	 * Create the frame.
	 */
	public SelectionView(ADB adb, MainView v) {
		this.adb = adb;
		this.devices = adb.getDevices();
		this.mainView=v;
		setTitle("AndroController");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblCihazSeiniz = new JLabel("Cihaz Se\u00E7iniz:");
		contentPane.add(lblCihazSeiniz, "2, 2");
		
		comboBox = new JComboBox<String>();
		contentPane.add(comboBox, "2, 4, fill, default");
		for(IDevice device : devices){
			comboBox.addItem(device.getSerialNumber());
		}
		
		
		
		btnRefresh = new JButton("Yenile");
		contentPane.add(btnRefresh, "2, 6, left, default");
		btnRefresh.addMouseListener(this);
		
		btnConnect = new JButton("Ba\u011Flan");
		contentPane.add(btnConnect, "2, 8, center, default");
		btnConnect.addMouseListener(this);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == btnConnect){
			if(comboBox.getSelectedIndex() != -1){
				mainView.switchToMainView(devices[comboBox.getSelectedIndex()]);
			}
			
		}else if(e.getSource()==btnRefresh){
			devices = adb.getDevices();
			comboBox.removeAllItems();
			for(IDevice device : devices){
				comboBox.addItem(device.getSerialNumber());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
