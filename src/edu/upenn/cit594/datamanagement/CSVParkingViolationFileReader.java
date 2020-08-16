package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.logging.Logger;

public class CSVParkingViolationFileReader implements Reader<ParkingViolation> {

	String filename;
	Logger logInstance = Logger.getInstance();

	public CSVParkingViolationFileReader(String filename) {
		this.filename = filename;
	}

	@Override
	public List<ParkingViolation> read() {
		List<ParkingViolation> parkingViolationList = new ArrayList<ParkingViolation>();
		Scanner fileReader = null;
		int counter = 0;
		try {
			fileReader = new Scanner(new File(filename));
			logInstance.log("Opened: " + filename);
			while (fileReader.hasNext()) {
				String eachViolation = fileReader.nextLine();
				String[] violationComponents = eachViolation.split(",");
				String timeStamp = violationComponents[0];
				Double fineAssessed = Double.parseDouble(violationComponents[1]);
				String description = violationComponents[2];
				String vehicleID = violationComponents[3];
				String vehicleState = violationComponents[4];
				String violationID = violationComponents[5];
				String zipCode;
				if(violationComponents.length == 7) {
					zipCode = violationComponents[6];
				}else {
					zipCode = "";
				}
				parkingViolationList.add(new ParkingViolation(timeStamp, fineAssessed, description, vehicleID, vehicleState, violationID, zipCode));
				
				counter++;
			}
		} catch (Exception e) {
			System.out.println("DEBUG Last Line Read: " + counter);
			System.out.println("Invalid Parking Violations CSV Input File");
			System.exit(0);
			throw new IllegalStateException(e);

		} finally {
			fileReader.close();
		}
		return parkingViolationList;
	}

}
