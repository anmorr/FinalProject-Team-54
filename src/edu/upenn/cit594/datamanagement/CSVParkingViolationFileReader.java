package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import edu.upenn.cit594.data.Data;
import edu.upenn.cit594.data.ParkingViolation;

public class CSVParkingViolationFileReader implements Reader {

	String filename;

	public CSVParkingViolationFileReader(String filename) {
		this.filename = filename;
	}

	@Override
	public List<Data> read() {
		List<Data> parkingViolationList = new ArrayList<Data>();
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(new File(filename));
			while (fileReader.hasNextLine()) {
				String eachViolation = fileReader.nextLine();
				String[] violationComponents = eachViolation.split(",");
				String timeStamp = violationComponents[0];
				double fineAssessed = Double.parseDouble(violationComponents[1]);
				String description = violationComponents[2];
				String vehicleID = violationComponents[3];
				String vehicleState = violationComponents[4];
				String violationID = violationComponents[5];
				String zipCode = violationComponents[6];
				parkingViolationList.add(new ParkingViolation(timeStamp, fineAssessed, description, vehicleID, vehicleState, violationID, zipCode));

			}
		} catch (Exception e) {
			System.out.println("Invalid Parking Violtions CSV Input File");
			System.exit(0);
			throw new IllegalStateException(e);

		} finally {
			fileReader.close();
		}
		return parkingViolationList;
	}

}
