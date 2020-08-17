package edu.upenn.cit594.processor;

import java.util.List;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.logging.Logger;

public class AverageMarketValue implements DataAverage {

	protected List<Property> propertyList;

	public AverageMarketValue(List<Property> propertyList) {
		this.propertyList = propertyList;
	}

	@Override
	public int average(String zipCode) {

		double homeValues = 0;
		double homeCount = 0;
		int skipped = 0;
		int skippedTotal = 0;
		boolean skipTest = false;
		int averageMarketValue = 0;
		for (Property property : propertyList) {
			if ((property.getZipCode() == null) || (property.getZipCode().isEmpty())) {
				continue;
			}
			if (property.getZipCode().contentEquals(zipCode)) {

				try {
					Double currentMarketValue = Double.parseDouble(property.getMarketValue());
					homeValues += currentMarketValue;
					Logger.getInstance().log("Home Values: " + homeValues);
				} catch (NullPointerException e) {
					if (!skipTest) {
						skipped = (int) homeCount;
						skipTest = true;
						skippedTotal++;
					} else {
						skipped++;
						skippedTotal++;
					}

					System.out.println("Property: " + property.getMarketValue() + " Property Zip: "
							+ property.getZipCode() + " Skipped: " + skipped);
					continue;
				} catch (NumberFormatException e) {
					System.out.println(
							"Property: " + property.getMarketValue() + " Property Zip: " + property.getZipCode());
					continue;
				}
				homeCount++;
			}
		}
		if (homeCount > 0) {
			averageMarketValue = (int) (homeValues / homeCount);
		}
		System.out.println("Skipped Total: " + skippedTotal);
		return averageMarketValue;
	}
}
