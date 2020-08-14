package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.Data;
import edu.upenn.cit594.data.PropertyData;

public class CSVPropertyValueFileReader implements Reader {

	protected String filename;
	
	
	
	public CSVPropertyValueFileReader(String filename) {
		this.filename = filename;
	}



	@Override
	public List<Data> read() {
		// TODO Auto-generated method stub
		List<Data> propertyData = new ArrayList<Data>();
		Scanner in = null;
		
		boolean firstLine = true;
		String zipCode = null;
		int zipCodeIndex = 0;
		String totalLivableArea = null;
		int totalLivableAreaIndex = 0;
		int marketValueIndex = 0;
		String marketValue = null;
		int currentLineCount = 1;
		try {
			in = new Scanner(new File(filename));
//			in.useDelimiter(",");
			while(in.hasNext()) {
				if(firstLine) {
					firstLine = false;
					String firstLineValue = in.nextLine();
					String[] firstLineSplit = firstLineValue.split(",");
					for(int i = 0; i < firstLineSplit.length; i++) {
						String currentWord = firstLineSplit[i];
//						System.out.println(currentWord);
						if(currentWord.contentEquals("zip_code")) {
							zipCodeIndex = i;
							System.out.println("DEBUG: Zip Code Index: " + zipCodeIndex + " Expected: 72");
						}
						if(currentWord.contentEquals("total_livable_area")) {
							totalLivableAreaIndex = i;
							System.out.println("DEBUG: Total Livable Area Index: " + totalLivableAreaIndex +" Expected: 64" );
						}
						if(currentWord.contentEquals("market_value")) {
							marketValueIndex = i;
							System.out.println("DEBUG: Market Value Index: " + marketValueIndex + " Expected: 34");
						} 							
					}
					continue;
				}
				
				if(currentLineCount == 552 || currentLineCount == 861 || currentLineCount == 1292
						|| currentLineCount == 1746) {
					// Line 553 is broken 
					in.nextLine();
					currentLineCount++;
					continue;
				}
				zipCode = null;
				totalLivableArea = null;
				marketValue = null;
				currentLineCount++;
				
				String currentLine = in.nextLine();
				String[] currentLineSplit = currentLine.split(",");
				zipCode = currentLineSplit[zipCodeIndex].substring(0,5);
				
				
				totalLivableArea = currentLineSplit[totalLivableAreaIndex];
				marketValue = currentLineSplit[marketValueIndex];
				
				
				
				propertyData.add(new PropertyData(Double.parseDouble(marketValue), Double.parseDouble(totalLivableArea), zipCode));
				/*
				if(currentLineCount < 10) {
					System.out.println(zipCode);
					System.out.println(totalLivableArea);
				}
				*/
				if(currentLineCount == 9) {
					break;
				}
				
			}
		}
		catch (Exception e) {
			System.out.println("Zip Code: " + zipCode + " Total Livable Area: " + totalLivableArea +  " Market Value: " + marketValue + " Current Line Count: " + currentLineCount);
			throw new IllegalStateException(e);
			
		}
		finally {
			in.close();
		}
		return propertyData;
	}

}
