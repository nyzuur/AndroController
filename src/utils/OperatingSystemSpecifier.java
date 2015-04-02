package utils;
import java.util.Locale;


public class OperatingSystemSpecifier {
	
	public enum OperatingSystems{
	 Windows,
	 Linux,
	 OSX
	}
	
	public static OperatingSystems getOperatingSystem(){
		
		String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		if(os.indexOf("win")>= 0) {
			return OperatingSystems.Windows;
		}else if (os.indexOf("mac")>=0 ||  os.indexOf("osx")>=0 || os.indexOf("os x") >= 0){
			return OperatingSystems.OSX;
		}else if (os.indexOf("linux")>= 0) {
			return OperatingSystems.Linux;
		}else {
			System.exit(-1);
			return null;
		}
	}
 
}
