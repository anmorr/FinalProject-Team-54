package edu.upenn.cit594.processor;

import java.util.List;

import edu.upenn.cit594.data.Property;

public class AverageMarketValue implements DataAverage {
	
	
	protected List<Property> propertyList;
	
	public AverageMarketValue(List<Property> propertyList) {
		this.propertyList = propertyList;
	}

	@Override
	public int average(String zipCode) {
		
		int homeValues = 0;
		int homeCount = 0;
		int averageMarketValue = 0;
		for (Property property : propertyList) {
			if((property.getZipCode() == null) || (property.getZipCode().isEmpty())) {
				continue;
			}
			if(property.getZipCode().contentEquals(zipCode)) {
				
				try {
					homeValues += Double.parseDouble(property.getMarketValue());
				}
				catch (NumberFormatException e) {
					continue;
				}
				homeCount++;
			}
		}
		if(homeCount > 0) {
			averageMarketValue = homeValues/homeCount;
//			System.out.println(averageMarketValue);
		}
		return averageMarketValue;
	}
}
