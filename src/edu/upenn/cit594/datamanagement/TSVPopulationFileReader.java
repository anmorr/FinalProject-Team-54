package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.logging.Logger;

/**
 * 
 * @author anmorr and ryanng
 * 
 * Population File Reader
 *
 */
public class TSVPopulationFileReader implements Reader<Population> {

	String filename;
	Logger logInstance = Logger.getInstance();
	
	
	
	public TSVPopulationFileReader(String filename) {
		this.filename = filename;
	}

	@Override
	public List<Population> read() {
		List<Population> populations = new ArrayList<Population>();
		Scanner in = null;
		
		try {
			in = new Scanner(new File(filename));
			logInstance.log(filename);
			
			while(in.hasNext()) {
				String[] currentLine = in.nextLine().split(" ");
				String zipCode = currentLine[0];
				Integer population = Integer.parseInt(currentLine[1]);
				populations.add(new Population(zipCode, population));
			}
		}
		catch (Exception e) {
			System.out.println("Invalid Population File");
			System.exit(0);
		}
		finally {
			in.close();
		}
		return populations;
	}

}
