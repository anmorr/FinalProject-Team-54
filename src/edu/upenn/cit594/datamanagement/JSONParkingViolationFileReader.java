package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.upenn.cit594.data.Data;
import edu.upenn.cit594.data.ParkingViolation;

public class JSONParkingViolationFileReader implements Reader {

	String filename;
	public JSONParkingViolationFileReader(String filename) {
		this.filename = filename;
	}
	
	
	@Override
	public List<Data> read() {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		JSONArray parkingViolations;

		
		try {
			parkingViolations = (JSONArray)parser.parse(new FileReader(filename));
		} catch (Exception e) {
			System.out.println("Invalid Parking Violations Input File");
			System.exit(0);
			throw new IllegalStateException(e);
		}
		
		// user an iterator to iterate over each element of the array
		Iterator iter = parkingViolations.iterator();
	
		
		List<Data> violationList = new ArrayList<Data>();
		
		while (iter.hasNext()) {
			JSONObject parkingViolation = (JSONObject) iter.next();
			String timeStamp = (String) parkingViolation.get("date");
			double fineAssessed = (double) parkingViolation.get("fine");
			String description = (String) parkingViolation.get("violation");
			String vehicleID = (String) parkingViolation.get("plate_id");
			String vehicleState = (String) parkingViolation.get("state");
			String violationID = (String) parkingViolation.get("ticket_number");
			String zipCode = (String) parkingViolation.get("zip_code");
			violationList.add(new ParkingViolation(timeStamp, fineAssessed, description, vehicleID, vehicleState, violationID, zipCode));
		}
		
		
		
		return violationList;
	}

	
}
