package edu.upenn.cit594.processor;

import java.util.List;

import edu.upenn.cit594.data.Property;
/**
 * 
 * @author anmorr and ryanng
 * 
 * Average Market Value Strategy.
 *
 */
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
				} catch (NullPointerException e) {
					if (!skipTest) {
						skipped = (int) homeCount;
						skipTest = true;
						skippedTotal++;
					} else {
						skipped++;
						skippedTotal++;
					}
					continue;
				} catch (NumberFormatException e) {
					continue;
				}
				homeCount++;
			}
		}
		if (homeCount > 0) {
			averageMarketValue = (int) (homeValues / homeCount);
		}
//		System.out.println("Skipped Total: " + skippedTotal);
		return averageMarketValue;
	}
}
