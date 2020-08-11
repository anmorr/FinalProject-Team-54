package edu.upenn.cit594.processor;

import java.util.List;

import edu.upenn.cit594.data.Data;
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
	protected List<Data> propertyValues;
	protected Reader TSVPopulationReader;
	protected List<Data> populationData;
	
	public Processor(Reader parkingViolationReader, Reader propertyValueReader,
			Reader populationReader) {
		this.parkingViolationsFileReader = parkingViolationReader;
		parkingViolations = parkingViolationReader.read();
		this.CSVPropertyValueReader = propertyValueReader;
		propertyValues = CSVPropertyValueReader.read();
		this.TSVPopulationReader = populationReader;
		populationData = TSVPopulationReader.read();
	}
	
	/**
	 * Writes the total population for all of the
	 * ZIP codes in the population file.
	 * @params none
	 */
	public void totalPopulationForAllZipCodes() {
		System.out.println("totalPopulationForAllZipCodes");
	}
	
	/**
	 * Displays the total fines per capita for each 
	 * ZIP code.
	 * @param none
	 */
	public void totalFinesPerCapita() {
		System.out.println("totalFinesPerCapita");
	}
	
	/**
	 * Displays the average market value 
	 * ZIP code.
	 * @param none
	 */
	public void averageMarketValue() {
		System.out.println("averageMarketValue");
	}
	
	/**
	 * Displays the average total livable area.
	 * @param none
	 */
	public void averageTotalLiveableArea() {
		System.out.println("averageTotalLiveableArea");
	}
	
	/**
	 * Displays the total residential market value
	 * per capita.
	 * @param none
	 */
	public void totalResidentialMarketValuePerCapita() {
		System.out.println("totalResidentialMarketValuePerCapita");
	}
	
	/**
	 * Displays the custom feature.
	 * @param none
	 */
	public void customFeature() {
		System.out.println("customFeature");
	}
}
