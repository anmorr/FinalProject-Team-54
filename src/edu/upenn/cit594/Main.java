package edu.upenn.cit594;

import java.io.File;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.CSVParkingViolationFileReader;
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
	
	static Reader<ParkingViolation> parkingViolationReader;
	static Reader<Property> propertyValueReader;
	static Reader<Population> populationReader;
	
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
		
		// Initialize the logfile
		logfileName = args[4];
		Logger.initialize(logfileName);
		Logger instance = Logger.getInstance();
		
		String arguments = args[0] + " " + args[1] + " " + args[2]
				+ " " + args[3] + " " + args[4];
		
		parkingViolationFileType = args[0];
		if(parkingViolationFileType.contentEquals("json")) {
			parkingViolationFileName = args[1];

			parkingViolationReader = new JSONParkingViolationFileReader(parkingViolationFileName);
		} else if (parkingViolationFileType.contentEquals("csv")) {
			parkingViolationFileName = args[1];

			parkingViolationReader = new CSVParkingViolationFileReader(parkingViolationFileName);
		}else {
			usage(1);
		}
		
		propertyValueFileName = args[2];
		
		propertyValueReader = new CSVPropertyValueFileReader(propertyValueFileName);
		
		populationFileName = args[3];
		
		populationReader = new TSVPopulationFileReader(populationFileName);
		
		processor = new Processor(parkingViolationReader, propertyValueReader, populationReader);
		
		CommandLineUserInterface ui = new CommandLineUserInterface(processor);
		
		
		instance.log(arguments);
		
		ui.start();
	}

}
