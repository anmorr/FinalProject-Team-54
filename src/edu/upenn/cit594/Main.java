package edu.upenn.cit594;

import java.io.File;

import edu.upenn.cit594.datamanagement.CSVPopulationFileReader;
import edu.upenn.cit594.datamanagement.CSVPropertyValueFileReader;
import edu.upenn.cit594.datamanagement.JSONParkingViolationFileReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.TSVPopulationFileReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

public class Main {
	
	static String parkingViolationFileType = null;
	static String parkingViolationFileName = null;
	static String propertyValueFileName = null;
	static String populationFileName = null;
	static String logfileName = null;
	
	static Reader parkingViolationReader;
	static Reader propertyValueReader;
	static Reader populationReader;
	
	static Processor processor;
	
	/**
	 * Helper file that prints message 
	 * on error with input files.
	 * @param msg
	 */
	private static void usage(int msg) {
		if(msg == 1) {
			System.out.println("Usage: java Main [file type (json/text)] [filename] "
					+ "[property value csv file] [population txt file] [logfile]");
			System.exit(0);
		}
		if(msg == 2){
			System.out.println("1 or more files is not readable!"); 
			System.out.println("Please verify that files exist and have correct permissions!");
			System.exit(0);
		}
	}
	
	
	/**
	 * Helper method that checks for file readability
	 * and permissions.
	 * @param filename
	 * @return
	 */
	private static boolean checkFile(String filename) {
		File f = new File(filename);
		if(f.canRead() && f.exists()) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {

		if(args.length < 5) {
			System.out.println("Usage: Incorrect number of arguments submitted.");
			System.exit(0);
		}
		
		parkingViolationFileType = args[0].toLowerCase();
		if(parkingViolationFileType.contentEquals("json")) {
			parkingViolationFileName = args[1];
			if(!checkFile(parkingViolationFileName)){
				usage(2);
			}
			parkingViolationReader = new JSONParkingViolationFileReader(parkingViolationFileName);
		} else if (parkingViolationFileType.contentEquals("csv")) {
			parkingViolationFileName = args[1];
			if(!checkFile(parkingViolationFileName)){
				usage(2);
			}
			parkingViolationReader = new CSVPopulationFileReader(parkingViolationFileName);
		}else {
			usage(1);
		}
		
		propertyValueFileName = args[2];
		if(!checkFile(propertyValueFileName)) {
			usage(2);
		}
		propertyValueReader = new CSVPropertyValueFileReader(propertyValueFileName);
		
		populationFileName = args[3];
		if(!checkFile(populationFileName)) {
			usage(2);
		}
		populationReader = new TSVPopulationFileReader(populationFileName);
		
		// Initialize the logfile
		logfileName = args[4];
		Logger.initialize(logfileName);
		
		processor = new Processor(parkingViolationReader, propertyValueReader, populationReader);
		
		CommandLineUserInterface ui = new CommandLineUserInterface(processor);
		
		Logger instance = Logger.getInstance();
		String arguments = parkingViolationFileType + " " + parkingViolationFileName + " " + propertyValueFileName
				+ " " + populationFileName + " " + logfileName;
		instance.log(arguments);
		
		ui.start();
	}

}
