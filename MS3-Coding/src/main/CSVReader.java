package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

	public static void readCSV(String pathToCSV){
		File csvFile = new File(pathToCSV);
		
		if(csvFile.isFile()) {
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
				
				try {
					String row;
					
					List<List<String>> goodEntry = new ArrayList<List<String>>();
					List<List<String>> badEntry = new ArrayList<List<String>>();

					SQLiteDB db = new SQLiteDB();
					
					while((row = csvReader.readLine()) != null) {
						String[] dataEntry = row.split(",");
						if(dataEntry.length==11) {
							goodEntry.add(Arrays.asList(dataEntry));
							//db.addBatch(dataEntry[0], dataEntry[1], dataEntry[2], dataEntry[3], dataEntry[4], dataEntry[5], dataEntry[6], Boolean.valueOf(dataEntry[7]), Boolean.valueOf(dataEntry[8]), dataEntry[9]);
							//System.out.println("Entry "+goodEntry.size()+" added successfully.");
						}else {
							badEntry.add(Arrays.asList(dataEntry));
							//System.out.println("Bad Entry");
						}
					}

					System.out.println(goodEntry.size()+" Good Entries");
					System.out.println(badEntry.size()+" Bad Entries");
					System.out.println((goodEntry.size()+badEntry.size())+" Total Entries");

					db.insertBatch(goodEntry);
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		readCSV("./../ms3Interview.csv");
	}
}
