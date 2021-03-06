package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.*;

/**
 * 
 * @authors anmorr and ryanng
 *
 * This class handles all processing for the
 * program.
 * 
 */
public class Processor {
	
//	protected Reader<ParkingViolation> parkingViolationsFileReader;
	protected List<ParkingViolation> parkingViolations;
	protected List<Property> propertyData;
	protected List<Population> populationData;
	protected Map<String, Integer> populationToZip = new HashMap<String, Integer>();
	protected Map<String, Integer> averageMarketValueMap = new HashMap<String, Integer>();
	protected Map<String, Integer> totalResidentialMarketValueMap = new HashMap<String, Integer>();
	protected Map<String, Integer> averageTotalLivableAreaMap = new HashMap<String, Integer>();
	protected Map<String, Double> esitmatedFinesPerHouseholdMap = new HashMap<String, Double>();
	protected Map<String, Double> percentageOfFinesToTotalMarketValueMap = new HashMap<String, Double>();
	
	
	public Processor(Reader<ParkingViolation> parkingViolationReader, Reader<Property> propertyReader,
			Reader<Population> populationReader) {
		parkingViolations = parkingViolationReader.read();
		propertyData = propertyReader.read();
		populationData = populationReader.read();
		createPopulationToZipMap(); 
	}
	
	/**
	 * Helper method to implement the average strategy.
	 * @param String zipCode
	 * @param DataAverage avg
	 */
	private int processorAverage(String zipCode, DataAverage avg) {
		
		return avg.average(zipCode);
		
	}
	
	/**
	 * Helper method to calculate the aggregate home values 
	 * for a given ZIP code.
	 * @param String zipCode
	 */
	private Double[] getPropertyInfoByZipCode(String zipCode) {
		double homeValues = 0;
		double homeCount = 0;
		Double[] homeInfo = new Double[2];
		for (Property property : propertyData) {
			if((property.getZipCode() == null) || (property.getZipCode().isEmpty())) {
				continue;
			}
			if(property.getZipCode().contentEquals(zipCode)) {
				homeCount++;
				try {
					homeValues += Double.parseDouble(property.getMarketValue());
				}
				catch (NumberFormatException e){
					continue;
				}
			}
		}
		homeInfo[0] = homeValues;
		homeInfo[1] = homeCount;
		return homeInfo;
	}
	
	
	/**
	 * Helper method to create a global map of ZIP codes to population
	 * Used by:  totalPopulationForAllZipCodes() and totalFinesPerCapita()
	 */
	private void createPopulationToZipMap() {
		
		for (Population population : populationData) {
			if(populationToZip.containsKey(population.getZipCode())) {
				continue;
			}
			populationToZip.put(population.getZipCode(), population.getPopulation());
		}
	}
	
	/**
	 * Writes the total population for all of the
	 * ZIP codes in the population file.
	 * @params none
	 */
	public int totalPopulationForAllZipCodes() {
		int totalPopulation = 0;
		
		for(String zip : populationToZip.keySet()) {
			totalPopulation += populationToZip.get(zip);
		}
		
		return totalPopulation;
	}
	
	/**
	 * Displays the total fines per capita for each 
	 * ZIP code.
	 * @param none
	 */
	public Map<String, Double> totalFinesPerCapita() {
		Map<String, Double> zipToFines = new HashMap<String, Double>();
		Map<String, Double> zipToFinesPerCapita = new TreeMap<String, Double>();
		/**
		 * This for loop goes through the list of violations and 
		 * maps the zip codes to aggregate fines for that zip.
		 */
		for (ParkingViolation violation : parkingViolations) {
			ParkingViolation currentViolation = violation;
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
		 * It then iterates through the zipToFines map to use the 
		 * zip code as a key to extract the population from the populationToZip map.
		 * Lastly, the totalFinesPerCapita is calculated and placed into the zipToFinesPerCapita
		 * TreeMap.
		 */
		
		for(String zipCode : zipToFines.keySet()) {
			int currentPopulation = 0;
			Double totalFinesPerCapita;
			if(zipCode.isEmpty() || (zipCode == null)) {
				continue;
			}
			if(populationToZip.containsKey(zipCode)) {
				currentPopulation = populationToZip.get(zipCode);
				totalFinesPerCapita = zipToFines.get(zipCode) / currentPopulation;
				zipToFinesPerCapita.put(zipCode, totalFinesPerCapita);
			}else {
				continue;
			}
		}
		return zipToFinesPerCapita;
	}
	
	/**
	 * Displays the average market value 
	 * ZIP code. 
	 * @param none
	 * @return integer
	 */
	public int averageMarketValue(String zipCode) {
		if(averageMarketValueMap.containsKey(zipCode)) {
//			System.out.println("Found using memoization");
			return averageMarketValueMap.get(zipCode);
		}
		int averageMarketValue = 0;
		averageMarketValue = processorAverage(zipCode, new AverageMarketValue(propertyData));
		
		averageMarketValueMap.put(zipCode, averageMarketValue);
		return averageMarketValue;
	}
	/**
	 * Displays the average total livable area.
	 * @param none
	 * @return integer
	 */
	public int averageTotalLiveableArea(String zipCode) {
		
		if(averageTotalLivableAreaMap.containsKey(zipCode)) {
			return averageTotalLivableAreaMap.get(zipCode);
		}
		int averageTotalLivableArea = 0;

		averageTotalLivableArea = processorAverage(zipCode, new AverageTotalLivableArea(propertyData));
		
		
		averageTotalLivableAreaMap.put(zipCode, averageTotalLivableArea);
		return averageTotalLivableArea;
	}
	
	/**
	 * Displays the total residential market value
	 * per capita.
	 * @param none
	 * @return integer
	 */
	public int totalResidentialMarketValuePerCapita(String zipCode) {
		
		if(totalResidentialMarketValueMap.containsKey(zipCode)) {
			return totalResidentialMarketValueMap.get(zipCode);
		}
		
		Double[] homeInfo = getPropertyInfoByZipCode(zipCode);
		double homeValues = homeInfo[0];
		double populationCount = populationToZip.get(zipCode);
		int totalMarketValuePerCapita = 0;
		
		if(populationCount > 0) {
//			System.out.println("DEBUG Home Value: " + homeValues);
//			System.out.println("DEBUG Population: " + populationCount);
			
			totalMarketValuePerCapita =  (int) (homeValues/populationCount);
//			System.out.println(totalMarketValuePerCapita);
		}
		totalResidentialMarketValueMap.put(zipCode, totalMarketValuePerCapita);
		return totalMarketValuePerCapita;
	}
	
	/**
	 * Displays the custom feature.
	 * @param String zipCode
	 */
	public Double customFeature(String zipCode) {
		
		if(percentageOfFinesToTotalMarketValueMap.containsKey(zipCode)) {
			return percentageOfFinesToTotalMarketValueMap.get(zipCode);
		}
		
		double totalFines = 0.0;
		double percentageOfFinesToTotalMarketValue = 0.0;
		
		
		for (ParkingViolation violation : parkingViolations) {
			if(!violation.getVehicleState().contains("PA")) {
				continue;
			}
			if((violation.getZipCode().isEmpty()) || (violation.getZipCode() == null)) {
				continue;
			}
			
			if(violation.getZipCode().contentEquals(zipCode)) {
				totalFines += violation.getFineAssessed();
			}
		}
		
		
		percentageOfFinesToTotalMarketValue = (double)
				(totalFines / totalResidentialMarketValuePerCapita(zipCode)) * 100.00;
		
		
		percentageOfFinesToTotalMarketValueMap.put(zipCode, percentageOfFinesToTotalMarketValue);
		
		return percentageOfFinesToTotalMarketValue;
	}
}
