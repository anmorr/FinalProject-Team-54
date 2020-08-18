package edu.upenn.cit594.ui;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

public class CommandLineUserInterface {
	
	protected Processor processor;
	protected Scanner in;
	protected Logger logInstance = Logger.getInstance();

	public CommandLineUserInterface(Processor processor) {
		
		this.processor = processor;
		in = new Scanner(System.in);
		
	}

	public void start() {
		while (true) {
			System.out.println("Enter 1 : Total population for all ZIP codes.");
			System.out.println("Enter 2 : Total parking fines per capita for each ZIP code.");
			System.out.println("Enter 3 : Average market value for residences in a specified ZIP code.");
			System.out.println("Enter 4 : Average total livable area for residences in a specified ZIP code.");
			System.out.println("Enter 5 : Total residential market value per capita for a specified ZIP code.");
			System.out.println("Enter 6 : Estimated fines per household.");
			System.out.println("Enter 0 : Exit");
			
			System.out.println("\nChoice: ");
			
			int choice = in.nextInt();
			
			Logger.getInstance().log("Choice: " + choice);
			
			if (choice == 1) {
				doTotalPopulationForAllZipCodes();
			}
			else if (choice == 2) {
				doTotalFinesPerCapita();
			}
			else if (choice == 3) {
				Scanner input1 = new Scanner(System.in);
				System.out.println("Please enter a ZIP code: ");
				String zipCode = input1.next().trim();
				logInstance.log("ZipCode: " + zipCode);
				System.out.println(doAverageMarketValue(zipCode));
			}
			else if (choice == 4) {
				Scanner input2 = new Scanner(System.in);
				System.out.println("Please enter a ZIP code: ");
				String zipCode = input2.next().trim();
				logInstance.log("ZipCode: " + zipCode);
				System.out.println(doAverageTotalLiveableArea(zipCode));
//				input2.close();
			}
			else if (choice == 5) {
				Scanner input3 = new Scanner(System.in);
				System.out.println("Please enter a ZIP code: ");
				String zipCode = input3.next().trim();
				logInstance.log("ZipCode: " + zipCode);
				System.out.println(doTotalResidentialMarketValuePerCapita(zipCode));
				
			}
			else if (choice == 6) {
				Scanner input4 = new Scanner(System.in);
				System.out.println("Please enter a ZIP code: ");
				String zipCode = input4.next().trim();
				logInstance.log("ZipCode: " + zipCode);
				System.out.println(doCustomFeature(zipCode));
			}
			else if (choice == 0) {
				System.out.println("Bye...");
				System.exit(0);
			}
			else {
				System.out.println("Usage Error: Please choose one of the specified options.");
				System.out.println("Bye...");
				System.exit(0);
			}
		}
		
	}


	private int doTotalResidentialMarketValuePerCapita(String zipCode) {
		return processor.totalResidentialMarketValuePerCapita(zipCode);
		
	}

	private int doAverageTotalLiveableArea(String zipCode) {
		return processor.averageTotalLiveableArea(zipCode);
		
	}

	private int doAverageMarketValue(String zipCode) {
		return processor.averageMarketValue(zipCode);
		
	}

	private void doTotalFinesPerCapita() {
		final DecimalFormat df = new DecimalFormat("0.0000");
		df.setRoundingMode((RoundingMode.FLOOR));
		Map<String, Double> zipToFinesPerCapita = processor.totalFinesPerCapita();
		for(String zipCode : zipToFinesPerCapita.keySet()) {
			System.out.println(zipCode + " " + df.format(zipToFinesPerCapita.get(zipCode)));
		}
		System.out.println();
	}

	private void doTotalPopulationForAllZipCodes() {
		System.out.println(processor.totalPopulationForAllZipCodes());
		
	}
	
	private Double doCustomFeature(String zipCode) {
		return processor.customFeature(zipCode);
		
	}
	
	

}
