
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


import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;



import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.SpringLayout;
import javax.swing.JButton;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.swing.JLabel;

import org.jcodec.codecs.h264.H264Decoder;

import utils.ByteArrayOutputReceiver;
import utils.Log;

public class View extends JFrame {
	private JPanel ScreenPanel;
	private JPanel keyPanel;
	private JButton btnBack;
	private JButton btnHome;
	private JButton btnMenu;
	private IDevice device;
	BufferedImage bufferedImage=null;
	private JLabel lblImage;
	String exStorDir=null;
	File tempFile=null;
	Class<View> thisClass = View.class;
	long startTime;
	Graphics2D graphics;
	int width, height;
	boolean runAtLeastOneTime=false;
	


	/**
	 * Create the frame.
	 * @param asl 
	 */
	public View(IDevice device) {
		
		String tmpdir = System.getProperty("java.io.tmpdir");
		Log.p("Default TempDir: "+ tmpdir, thisClass);
		
		this.device=device;
		Log.p("Connected To device with serial: " +device.getSerialNumber(), thisClass);
	
		
		setTitle("AndroController");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 550);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		ScreenPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, ScreenPanel, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, ScreenPanel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, ScreenPanel, 332, SpringLayout.WEST, getContentPane());
		getContentPane().add(ScreenPanel);
		
		keyPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, keyPanel, 485, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, keyPanel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, keyPanel, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, keyPanel, -10, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, ScreenPanel, -6, SpringLayout.NORTH, keyPanel);
		ScreenPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("fill:default:grow"),}));
		
		lblImage = new JLabel();
		lblImage.setIcon(new ImageIcon(View.class.getResource("/javax/swing/plaf/metal/icons/Error.gif")));
		ScreenPanel.add(lblImage, "1, 1, fill, fill");
		
		getContentPane().add(keyPanel);
		keyPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		btnBack = new JButton("Back");
		keyPanel.add(btnBack, "1, 1, fill, fill");
		
		btnMenu = new JButton("Menu");
		keyPanel.add(btnMenu, "3, 1, fill, fill");
		
		btnHome = new JButton("Home");
		keyPanel.add(btnHome, "5, 1, default, fill");
		exStorDir= device.getMountPoint(IDevice.MNT_EXTERNAL_STORAGE);
		graphics = (Graphics2D) lblImage.getGraphics();
		width = lblImage.getWidth();
		height = lblImage.getHeight();
	}
	void updateScreenShot() throws TimeoutException, AdbCommandRejectedException, IOException, ShellCommandUnresponsiveException, SyncException{
		
		Log.p("updateScreenshot at: " + System.currentTimeMillis(), thisClass);
		
		
		if(tempFile == null){
			tempFile = new File(System.getProperty("java.io.tmpdir") + File.separatorChar + "screenshot.png");
		}
		
		
		
		//String remoteTempFile = exStorDir + "/.screenshot.png";
		ByteArrayOutputReceiver receiver = new ByteArrayOutputReceiver();
		startTime = System.currentTimeMillis();
		//
		
		//
		//
		//DON'T USE FILES AS BUFFER !!!!
		//**done in remote side
		//**to be done for local side
		//TODO implement screenrecord command 
		//push, exec, continuously collectOutput and decode it to show
		//!!!!!!!!!!!!!!!!!
		//
		
		Log.p("Exec Command: [start]" +"screencap -p | gzip -9 -c ", thisClass);
		device.executeShellCommand("screencap -p | gzip -9 -c", receiver);
		Log.p("Exec Command: [end]"+"screencap -p | gzip -9 -c" + " takes: " + (System.currentTimeMillis()-startTime) + "ms", thisClass);
		Log.p("Exec Command: [Output lenght]" + receiver.getOutput().position(), thisClass);
		
		
		/*
		startTime = System.currentTimeMillis();
		Log.p("Exec Command: [start]" +"pull " + remoteTempFile + tempFile.getAbsolutePath(), thisClass);
		device.pullFile(remoteTempFile, tempFile.getAbsolutePath());
		Log.p("Exec Command: [end]"+ "pull " + remoteTempFile + tempFile.getAbsolutePath() +" takes: " + (System.currentTimeMillis()-startTime) + "ms", thisClass);
		*/
		/*
		byte[] data = receiver.getOutput().array();
		for ( int i = 0 ; i < 128 ; i+=8){
			Log.p(byteToHex(data[i]) + " " + byteToHex(data[i+1]) + " " + byteToHex(data[i+2]) + " " +byteToHex(data[i+3])
				+ byteToHex(data[i+4]) + " " + byteToHex(data[i+5]) + " " + byteToHex(data[i+6]) + " " +byteToHex(data[i+7]), thisClass);
		}
			*/
		
		ByteArrayInputStream bis = new ByteArrayInputStream(repair(receiver.getOutput().array()));
		GZIPInputStream gzip = new GZIPInputStream(bis);
		
		
		
		
		bufferedImage = ImageIO.read(gzip);
		bufferedImage = resizeBufferedImage(bufferedImage, bufferedImage.getType());
		
		
		
		Log.p("Taken Screenshot at: "+ System.currentTimeMillis(), thisClass);
		
		
	
		
		lblImage.setIcon(new ImageIcon(bufferedImage));
		
		tempFile.delete();
		if(!runAtLeastOneTime)
			runAtLeastOneTime=true;
		
	}
	/*
	private BufferedImage getBufferedImage(RawImage rawImage) throws IOException {
		if(bufferedImage == null) //initialize it
			bufferedImage = new BufferedImage(rawImage.width, rawImage.height, BufferedImage.TYPE_INT_RGB);
		int index = 0;
		int indexInc = rawImage.bpp >> 3;
		for (int y = 0; y < rawImage.height; y++) {
			for (int x = 0; x < rawImage.width; x++, index += indexInc) {
				int value = rawImage.getARGB(index);
				bufferedImage.setRGB(x, y, value);
			}
		}
		
		return bufferedImage;
		
	}
	*/
	private BufferedImage resizeBufferedImage(BufferedImage bufferedImage2, int type) {
		BufferedImage resizedImage = new BufferedImage(lblImage.getWidth(), lblImage.getHeight(), type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(bufferedImage2, 0, 0, lblImage.getWidth(), lblImage.getHeight(), null);
		g.dispose();
		return resizedImage;
	}
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String byteToHex(byte bytes) {
	    char[] hexChars = new char[2];
	        int v = bytes & 0xFF;
	        hexChars[0] = hexArray[v >>> 4];
	        hexChars[1] = hexArray[v & 0x0F];
	    
	    return new String(hexChars);
	}
	static byte[] repair(byte[] encoded) {
	    ByteArrayOutputStream decoded = new ByteArrayOutputStream();
	    for (int i=0; i<encoded.length; i++) {
	        if (encoded.length > i+1 && encoded[i] == 0x0d && encoded[i+1] == 0x0a) {
	            decoded.write(0x0a);
	            i++;
	        } else {
	            decoded.write(encoded[i]);
	        }
	    }
	    try {
	        decoded.close();
	    } catch (IOException ioe) {
	    	//ignore
	    }

	    return decoded.toByteArray();      
	}
}
