package edu.upenn.cit594.data;
/**
 * Property Data model
 * @author anmorr and ryanng
 *
 */
public class Property {
	
	
	protected String marketValue;
	protected String totalLivableArea;
	protected String zipCode;
	
	public Property(String marketValue, String totalLivableArea, String zipCode) {
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.zipCode = zipCode;
	}

	public String getMarketValue() {
		return marketValue;
	}

	public String getTotalLivableArea() {
		return totalLivableArea;
	}

	public String getZipCode() {
		return zipCode;
	}

}
