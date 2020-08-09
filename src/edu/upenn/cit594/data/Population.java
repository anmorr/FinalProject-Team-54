package edu.upenn.cit594.data;
/**
 * Population Data model
 * @author anmorr and ryanng
 *
 */
public class Population extends Data {
	
	protected String zipCode;
	protected String population;
	
	public Population(String zipCode, String population) {
		this.zipCode = zipCode;
		this.population = population;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getPopulation() {
		return population;
	}

}
