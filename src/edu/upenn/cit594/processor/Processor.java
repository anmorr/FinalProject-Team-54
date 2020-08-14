package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.data.Data;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.PropertyData;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.*;

/**
 * 
 * @author anmorr and ryanng
 *
 */
public class Processor {
	
	protected Reader parkingViolationsFileReader;
	protected List<Data> parkingViolations;
	protected Reader CSVPropertyValueReader;
	protected List<Data> propertyData;
	protected Reader TSVPopulationReader;
	protected List<Data> populationData;
	protected Map<String, Integer> populationToZip = new HashMap<String, Integer>();
	
	public Processor(Reader parkingViolationReader, Reader propertyValueReader,
			Reader populationReader) {
		this.parkingViolationsFileReader = parkingViolationReader;
		parkingViolations = parkingViolationReader.read();
		this.CSVPropertyValueReader = propertyValueReader;
		propertyData = CSVPropertyValueReader.read();
		this.TSVPopulationReader = populationReader;
		populationData = TSVPopulationReader.read();
		createPopulationToZipMap();
	}
	
	/**
	 * Helper method to create a map of Zip codes to population
	 */
	private void createPopulationToZipMap() {
		for (Data population : populationData) {
			Population currentPopulationObject = (Population) population;
			populationToZip.put(currentPopulationObject.getZipCode(), currentPopulationObject.getPopulation());
		}
	}
	
	/**
	 * Writes the total population for all of the
	 * ZIP codes in the population file.
	 * @params none
	 */
	public int totalPopulationForAllZipCodes() {
		int totalPopulation = 0;
		
		for (Data population : populationData) {
			Population currentPopulationObject = (Population) population;
			populationToZip.put(currentPopulationObject.getZipCode(), currentPopulationObject.getPopulation());
			totalPopulation += currentPopulationObject.getPopulation();
		}
		
		return totalPopulation;
	}
	
	/**
	 * Displays the total fines per capita for each 
	 * ZIP code.
	 * @param none
	 */
	public void totalFinesPerCapita() {
		Map<String, Double> zipToFines = new HashMap<String, Double>();
		Map<String, Double> zipToFinesPerCapita = new TreeMap<String, Double>();
		/**
		 * This for loop goes through the list of violations and 
		 * maps the zip codes to aggregate fines for that zip.
		 */
		for (Data violation : parkingViolations) {
			ParkingViolation currentViolation = (ParkingViolation) violation;
			if(!currentViolation.getVehicleState().contains("PA")) {
				continue;
			}
			if(currentViolation.getZipCode().isEmpty()) {
				continue;
			}
			String zipCodeKey = currentViolation.getZipCode();
			if(zipToFines.containsKey(zipCodeKey)) {
				Double totalFines = zipToFines.get(zipCodeKey);
				totalFines = totalFines + currentViolation.getFineAssessed();
				zipToFines.put(zipCodeKey, totalFines);
			}else {
				zipToFines.put(zipCodeKey, currentViolation.getFineAssessed());
			}
		}
		/**
		 * This section of code uses a helper method to map the populations
		 * to zip codes. It then iterates through the zipToFines map to use the 
		 * zip code as a key to extract the population from the populationToZip map.
		 * Lastly, the totalFinesPerCapita is calculated and placed into the zipToFinesPerCapita
		 * TreeMap.
		 */
		createPopulationToZipMap();
		for(String zipCode : zipToFines.keySet()) {
			int currentPopulation = 0;
			if(zipCode.isEmpty() || (zipCode == null)) {
				continue;
			}
			try {
				if(populationToZip.containsKey(zipCode)) {
					currentPopulation = populationToZip.get(zipCode);
				}else {
					continue;
				}
				
			}
			catch (Exception e) {
				System.out.println(zipCode);
				e.printStackTrace();
			}
			if(currentPopulation == 0) {
				continue;
			}
			Double totalFinesPerCapita = zipToFines.get(zipCode) / currentPopulation;
			zipToFinesPerCapita.put(zipCode, totalFinesPerCapita);
		}
		for(String zipCode : zipToFinesPerCapita.keySet()) {
			System.out.printf("%s %.04f\n", zipCode, zipToFinesPerCapita.get(zipCode));
		}
		System.out.println("\r");
	}
	
	/**
	 * Displays the average market value 
	 * ZIP code.
	 * @param none
	 * @return integer
	 */
	public int averageMarketValue(String zipCode) {
		int homeCount = 0;
		int homeValues = 0;
		int averageMarketValue = 0;
		for (Data property : propertyData) {
			if(((PropertyData)property).getZipCode().contentEquals(zipCode)){
				homeCount++;
				homeValues += ((PropertyData)property).getMarketValue();
			}
		}
		if(homeCount > 0) {
			averageMarketValue = homeValues/homeCount;
		}
		return averageMarketValue;
	}
	/**
	 * Displays the average total livable area.
	 * @param none
	 * @return integer
	 */
	public int averageTotalLiveableArea(String zipCode) {
		int homeCount = 0;
		int liveableArea = 0;
		int averageTotalLiveableArea = 0;
		for (Data property : propertyData) {
			if(((PropertyData)property).getZipCode().contentEquals(zipCode)){
				homeCount++;
				liveableArea += ((PropertyData)property).getTotalLivableArea();
			}
		}
		if(homeCount > 0) {
			averageTotalLiveableArea = liveableArea/homeCount;
		}
		return averageTotalLiveableArea;
	}
	
	/**
	 * Displays the total residential market value
	 * per capita.
	 * @param none
	 * @return integer
	 */
	public int totalResidentialMarketValuePerCapita(String zipCode) {
		int averageMarketVal = averageMarketValue(zipCode);
		int populationCount = 0;
		int totalMarketValuePerCapita = 0;
		for (Data population : populationData) {
			if(((Population)population).getZipCode().contentEquals(zipCode)){
				
				populationCount += ((Population)population).getPopulation();
			}
		}
		
		if(populationCount > 0) {
			totalMarketValuePerCapita = averageMarketVal/populationCount;
		}
		
		return totalMarketValuePerCapita;
	}
	
	/**
	 * Displays the custom feature.
	 * @param none
	 */
	public void customFeature() {
		System.out.println("customFeature");
	}
}
