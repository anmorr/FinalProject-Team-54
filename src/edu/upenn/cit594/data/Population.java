package edu.upenn.cit594.data;
/**
 * Population Data model
 * @author anmorr and ryanng
 *
 */
public class Population extends Data {
	
	protected String zipCode;
	protected Integer population;
	
	public Population(String zipCode, Integer population2) {
		this.zipCode = zipCode;
		this.population = population2;
	}

	public String getZipCode() {
		return zipCode;
	}

	public Integer getPopulation() {
		return population;
	}

}
