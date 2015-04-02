package utils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import src.ADB;
import utils.Log;
import utils.OperatingSystemSpecifier;
import view.MainView;


public class AdbExtractor {
		
		static JarFile jar=null;
		static File adbLocation = null;
		
		
	public static void main (String args[]) throws Exception{
		if(!Log.DEBUG){
			try {
				jar =  new JarFile(AdbExtractor.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			} catch (IOException e) {
				Log.p(e.toString(),AdbExtractor.class);
			}
			if(jar!=null){
				
				OperatingSystemSpecifier.OperatingSystems os = OperatingSystemSpecifier.getOperatingSystem();
				ZipEntry entry;
				String dest = System.getProperty("user.home") + File.separatorChar + "adb";
				
				switch (os) {
				case Linux:
					Log.p("Os specified as linux",AdbExtractor.class);
					entry = jar.getEntry("linux/adb");
					adbLocation = extractEntry(entry,dest);
					break;
				case Windows:
					Log.p("Os specified as Windows",AdbExtractor.class);
					entry = jar.getEntry("win32/adb.exe");
					ZipEntry entryDLL1 = jar.getEntry("win32/AdbWinApi.dll");
					ZipEntry entryDLL2 = jar.getEntry("win32/AdbWinUsbApi.dll");
					adbLocation = extractEntry(entry, dest);
					extractEntry(entryDLL1, dest);
					extractEntry(entryDLL2, dest);
					break;
				case OSX:
					Log.p("Os specified as OS X",AdbExtractor.class);
					entry = jar.getEntry("darwin/adb");
					adbLocation = extractEntry(entry, dest);
					break;
				default:
					Log.p("Extraction for this os type is not found",AdbExtractor.class);
					System.exit(-1);
					break;
				}
				/*
				 * 
				 * START GUI
				 * 
				 */
				ADB adb = new ADB(adbLocation.getPath());
				new MainView(adb);
			}
		}else {
			ADB adb = new ADB(new File(System.getProperty("user.home") + File.separatorChar + "adb" + File.separatorChar + "adb.exe").getPath());
			new MainView(adb);
		}
		
	}
	private static File extractEntry(ZipEntry entry, String dest) throws Exception {
		File destinationDirectory =  new File(dest);
		if(!destinationDirectory.exists()){
			destinationDirectory.mkdirs();
		}
		File file = new File(dest + File.separatorChar + toFileName(entry));
		
		
		BufferedOutputStream bos = new  BufferedOutputStream(new FileOutputStream(file));
		InputStream zIn = null;
		try {
			zIn = jar.getInputStream(entry);
		} catch (IOException e) {
			Log.p(e.toString(),AdbExtractor.class);
		}
		byte[] bytesIn = new byte[4096];
	       int read = 0;
	       while ((read = zIn.read(bytesIn)) != -1) {
	           bos.write(bytesIn, 0, read);
	       }
	       bos.close();
	     return file;
	}
	private static String toFileName(ZipEntry entry){
		String entryName = entry.getName();
		return entryName.substring(entryName.lastIndexOf("/")+1,entryName.length());
	}
}
