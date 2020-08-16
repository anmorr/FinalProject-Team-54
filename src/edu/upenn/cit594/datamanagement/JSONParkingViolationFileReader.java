package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.logging.Logger;

public class JSONParkingViolationFileReader implements Reader<ParkingViolation> {

	String filename;
	Logger logInstance = Logger.getInstance();
	
	public JSONParkingViolationFileReader(String filename) {
		this.filename = filename;
	}
	
	
	@Override
	public List<ParkingViolation> read() {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		JSONArray parkingViolations;

		
		try {
			parkingViolations = (JSONArray)parser.parse(new FileReader(filename));
			logInstance.log("Opened: " + filename);
		} catch (Exception e) {
			System.out.println("Invalid Parking Violations Input File!");
			System.exit(0);
			throw new IllegalStateException(e);
		}
		
		// user an iterator to iterate over each element of the array
		@SuppressWarnings("rawtypes")
		Iterator iter = parkingViolations.iterator();
	
		
		List<ParkingViolation> violationList = new ArrayList<ParkingViolation>();
		
		while (iter.hasNext()) {
			JSONObject parkingViolation = (JSONObject) iter.next();
			System.out.println(parkingViolation.get("date"));
			String timeStamp = (String) parkingViolation.get("date");
			System.out.println(parkingViolation.get("fine"));
			Long fineAssessed = (Long) parkingViolation.get("fine");
			System.out.println(parkingViolation.get("violation"));
			String description = (String) parkingViolation.get("violation");
			System.out.println(parkingViolation.get("plate_id"));
			String vehicleID = (String) parkingViolation.get("plate_id");
			System.out.println(parkingViolation.get("state"));
			String vehicleState = (String) parkingViolation.get("state");
			System.out.println(parkingViolation.get("ticket_number"));
			Long violationID = (Long) parkingViolation.get("ticket_number");
			System.out.println(parkingViolation.get("zip_code"));
			String zipCode = (String) parkingViolation.get("zip_code");
			
			violationList.add(new ParkingViolation(timeStamp,Double.parseDouble(Long.toString(fineAssessed)), description, vehicleID, vehicleState, Long.toString(violationID), zipCode));
		}
		
		return violationList;
	}

	
}
