package utils;

public class Log {
	static boolean DEBUG = true;
	public static void p(String log, Class<?> c ){
		if(DEBUG){
			System.out.println("["+c.getSimpleName()+"] "+log);
		}
	}

}
