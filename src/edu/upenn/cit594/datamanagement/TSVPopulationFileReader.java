package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.upenn.cit594.data.Data;
import edu.upenn.cit594.data.Population;

public class TSVPopulationFileReader implements Reader {

	String filename;
	
	
	
	public TSVPopulationFileReader(String filename) {
		this.filename = filename;
	}

	@Override
	public List<Data> read() {
		List<Data> populations = new ArrayList<Data>();
		Scanner in = null;
		
		try {
			in = new Scanner(new File(filename));
			while(in.hasNext()) {
				String[] currentLine = in.nextLine().split(" ");
				String zipCode = currentLine[0];
				Integer population = Integer.parseInt(currentLine[1]);
				populations.add(new Population(zipCode, population));
			}
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
		finally {
			in.close();
		}
		return populations;
	}

}
