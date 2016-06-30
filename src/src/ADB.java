
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


package src;

import utils.Log;


import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

public class ADB {
	AndroidDebugBridge adb = null;
	IDevice device;
	
	
	public ADB(String adbLocation){
		
		AndroidDebugBridge.init(false);
		adb = AndroidDebugBridge.createBridge(adbLocation, true);
		waitUntilConnected(adb);
		
	}
	public IDevice[] getDevices(){
		IDevice[] devices = adb.getDevices();
		Log.p("ADB Socket Addr:"+ adb.getSocketAddress().toString(), ADB.class);
		Log.p("Devices: ", ADB.class);
		for(IDevice device : devices){
			Log.p(device.getName(), ADB.class);
		}
		return devices;
	}
	private void waitUntilConnected(AndroidDebugBridge adb){
		  int trials=10;
		  final int connectionWaitTime=50;
		  while (trials > 0) {
			  Log.p("ConnectionTrial: " + (11-trials) , ADB.class);
		    try {
		      Thread.sleep(connectionWaitTime);
		    }
		 catch (InterruptedException e) {
		      e.printStackTrace();
		    }
		    if (adb.isConnected()) {
		      break;
		    }
		    trials--;
		  }
		}
	
	
	
	
	

}
