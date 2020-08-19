package edu.upenn.cit594.processor;

import java.util.List;

import edu.upenn.cit594.data.Property;
/**
 * 
 * @author ryanng and anmorr
 * 
 * Average Total Livable Area Strategy
 *
 */
public class AverageTotalLivableArea implements DataAverage {
	
	protected List<Property> propertyList;
	
	public AverageTotalLivableArea(List<Property> propertyList) {
		this.propertyList = propertyList;
	}

	@Override
	public int average(String zipCode) {
		int homeCount = 0;
		int liveableArea = 0;
		int averageTotalLiveableArea = 0;
		for (Property property : propertyList) {
			if((property.getZipCode() == null) || (property.getZipCode().isEmpty())) {
				continue;
			}
			if(property.getZipCode().contentEquals(zipCode)){
				try {
					liveableArea += Double.parseDouble(property.getTotalLivableArea());
				}
				catch (NumberFormatException e) {
					continue;
				}
				catch (NullPointerException e) {
//					
					continue;
				}
				homeCount++;
			}
		}
		if(homeCount > 0) {
			averageTotalLiveableArea = liveableArea/homeCount;
		}
		return averageTotalLiveableArea;
	}

}
