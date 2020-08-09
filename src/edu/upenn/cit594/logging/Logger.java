package edu.upenn.cit594.logging;


import java.io.PrintStream;

public class Logger {
	
	
	private PrintStream out;
	private static Logger instance;
	
	/**
	 * 1. Private Constructor
	 * @param filename
	 */
	private Logger(String filename) {
		try {
			out = new PrintStream(filename);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 2. Initialize Singleton instance with filename
	 * @param filename
	 */
	public static void initialize(String filename) {
		Logger.instance = new Logger(filename);
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
	public void log(String msg1, String msg2) {
		out.printf("%-20s %s\n", msg1, msg2);
	}

}

