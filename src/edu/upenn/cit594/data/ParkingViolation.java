package edu.upenn.cit594.data;
/**
 * This is a class to model the Parking Violation Data
 * @author anmorr and ryanng
 *
 */
public class ParkingViolation extends Data {
	
	protected String timeStamp;
	protected Double fineAssessed;
	protected String description;
	protected String vehicleID;
	protected String vehicleState;
	protected String violationID;
	protected String zipCode;
	
	public ParkingViolation(String timeStamp, Double fineAssessed, String description, String vehicleID,
		String vehicleState, String violationID, String zipCode) {
		this.timeStamp = timeStamp;
		this.fineAssessed = fineAssessed;
		this.description = description;
		this.vehicleID = vehicleID;
		this.vehicleState = vehicleState;
		this.violationID = violationID;
		this.zipCode = zipCode;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public Double getFineAssessed() {
		return fineAssessed;
	}

	public String getDescription() {
		return description;
	}

	public String getVehicleID() {
		return vehicleID;
	}

	public String getVehicleState() {
		return vehicleState;
	}

	public String getViolationID() {
		return violationID;
	}

	public String getZipCode() {
		return zipCode;
	}
	
	

}

