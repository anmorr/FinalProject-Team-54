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
	
	protected Reader parkingFileReader;
	protected List<Data> parkingViolations;
	protected Reader CSVPropertyValueReader;
	protected List<Data> propertyValues;
	protected Reader TSVPopulationReader;
	protected List<Data> populationData;
	
	public Processor(Reader parkingViolationReader, Reader propertyValueReader,
			Reader populationReader) {
		this.parkingFileReader = parkingViolationReader;
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
		
	}
	
	/**
	 * Displays the total fines per capita for each 
	 * ZIP code.
	 * @param none
	 */
	public void totalFinesPerCapita() {
		
	}
	
	public void averageMarketValue() {
		
	}
	
	public void averageTotalLiveableArea() {
		
	}
	
	public void totalResidentialMarketValuePerCapita() {
		
	}
	
	public void customFeature() {
		
	}
}
