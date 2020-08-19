package edu.upenn.cit594.logging;


import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
/**
 * 
 * @author anmorr and ryanng
 * 
 * This is a Singleton logging class to
 * provide logging services for this
 * program.
 *
 */
public class Logger {
	
	
	private PrintStream out;
	private static Logger instance;
	private static boolean fileNameSet = false;
	
	/**
	 * 1. Private Constructor
	 * @param filename
	 */
	private Logger(String filename) {
		try {
			out = new PrintStream(new FileOutputStream(filename, true));
		} 
		catch (Exception e) {
			System.out.println("Unable to open or create log file.");
		}
	}
	
	/**
	 * 2. Initialize Singleton instance with filename
	 * @param filename
	 */
	public static void initialize(String filename) {
		
		if(!fileNameSet) {
			Logger.instance = new Logger(filename);
			fileNameSet = true;
		}
	}
	
	/**
	 * 3. Singleton access method
	 * @return
	 */
	public static Logger getInstance() {
		return instance;
	}
	
	/**
	 * 4. Singleton log method
	 * @param msg1
	 * @param msg2
	 */
	public void log(String inputMsg) {
		Date msg1 = new java.util.Date(System.currentTimeMillis());
		out.printf("%-20s %s\n", msg1, inputMsg);
	}

}

