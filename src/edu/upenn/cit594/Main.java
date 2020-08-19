package edu.upenn.cit594;

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


	public static void main(String[] args) {
		
		if(args.length != 5) {
			System.out.println("Usage: Incorrect number of arguments submitted.");
			System.exit(0);
		}
		
		// Initialize the logfile
		String logfileName = args[4];
		Logger.initialize(logfileName);
		Logger instance = Logger.getInstance();
		
		String parkingViolationFileType = args[0];
		String parkingViolationFileName = args[1];
		String propertyValueFileName = args[2];
		String populationFileName = args[3];
		
		Reader<ParkingViolation> parkingViolationReader = null;
		Reader<Property> propertyValueReader;
		Reader<Population> populationReader;
	
		if(parkingViolationFileType.equals("json")) {
			parkingViolationReader = new JSONParkingViolationFileReader(parkingViolationFileName);
		} else if (parkingViolationFileType.equals("csv")) {
			parkingViolationReader = new CSVParkingViolationFileReader(parkingViolationFileName);
		} else {
			System.out.println("Invalid Input File");
			System.exit(0);
		}
		
		propertyValueReader = new CSVPropertyValueFileReader(propertyValueFileName);
		populationReader = new TSVPopulationFileReader(populationFileName);
		Processor processor = new Processor(parkingViolationReader, propertyValueReader, populationReader);
				
		CommandLineUserInterface ui = new CommandLineUserInterface(processor);

		String arguments = parkingViolationFileType + " " + parkingViolationFileName + " " + propertyValueFileName
				+ " " + populationFileName + " " + logfileName;
		instance.log(arguments);
		
		ui.start();
	}

}
