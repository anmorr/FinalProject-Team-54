package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.logging.Logger;
/**
 * 
 * @author anmorr and ryanng
 * 
 * Property Value CSV File Reader
 *
 */
public class CSVPropertyValueFileReader implements Reader<Property> {

	protected String filename;
	Logger logInstance = Logger.getInstance();
	
	
	
	public CSVPropertyValueFileReader(String filename) {
		this.filename = filename;
	}



	@Override
	public List<Property> read() {
		List<Property> propertyData = new ArrayList<Property>();
		Scanner in = null;
		
		boolean firstLine = true;
		String zipCode = null;
		int zipCodeIndex = 0;
		String totalLivableArea = null;
		int totalLivableAreaIndex = 0;
		int marketValueIndex = 0;
		String marketValue = null;
		int currentLineCount = 1;
		final String regex = ",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
		try {
			in = new Scanner(new File(filename));
			logInstance.log(filename);
			while(in.hasNext()) {
				if(firstLine) {
					firstLine = false;
					String firstLineValue = in.nextLine();
					String[] firstLineSplit = firstLineValue.split(regex);
					for(int i = 0; i < firstLineSplit.length; i++) {
						String currentWord = firstLineSplit[i];
//						System.out.println(currentWord);
						if(currentWord.contentEquals("zip_code")) {
							zipCodeIndex = i;
//							System.out.println("DEBUG: ZIP Code Index: " + zipCodeIndex + " Expected: 72");
						}
						if(currentWord.contentEquals("total_livable_area")) {
							totalLivableAreaIndex = i;
//							System.out.println("DEBUG: Total Livable Area Index: " + totalLivableAreaIndex +" Expected: 64" );
						}
						if(currentWord.contentEquals("market_value")) {
							marketValueIndex = i;
//							System.out.println("DEBUG: Market Value Index: " + marketValueIndex + " Expected: 34");
						} 							
					}
					continue;
				}
				
				zipCode = null;
				totalLivableArea = null;
				marketValue = null;
				currentLineCount++;
				
				String currentLine = in.nextLine();
				String[] currentLineSplit = currentLine.split(regex);
				
				zipCode = currentLineSplit[zipCodeIndex];
				if(!zipCode.isEmpty() && (zipCode.length() >= 5)) {
					zipCode = zipCode.substring(0,5);
				}
						
				totalLivableArea = currentLineSplit[totalLivableAreaIndex];
				if(totalLivableArea.isEmpty()) {
					totalLivableArea = null;
				}
				
				marketValue = currentLineSplit[marketValueIndex];
				if(marketValue.isEmpty()) {
					marketValue = null;
				}
				
//				System.out.println("DEBUG Current Line Count : " + currentLineCount);

				
				propertyData.add(new Property(marketValue, totalLivableArea, zipCode));
				
				if((currentLineCount % 10000) == 0) {
					System.out.println("Running...");
					currentLineCount = 0;
				}
//				if(currentLineCount == 2000) {
//					break;
//				}
			}
		}
		catch (Exception e) {
			System.out.println("Invalid Property File!");
			System.exit(0);
			
		}
		finally {
			in.close();
		}
		return propertyData;
	}

}
