package edu.upenn.cit594.data;
/**
 * Property Value Data model
 * @author anmorr and ryanng
 *
 */
public class PropertyData extends Data {
	
	
	protected Double marketValue;
	protected Double totalLivableArea;
	protected String zipCode;
	
	public PropertyData(Double marketValue, Double totalLivableArea, String zipCode) {
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.zipCode = zipCode;
	}

	public Double getMarketValue() {
		return marketValue;
	}

	public Double getTotalLivableArea() {
		return totalLivableArea;
	}

	public String getZipCode() {
		return zipCode;
	}

}