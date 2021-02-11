package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

	private static String pathToCSV;

	
	public CSVReader(String pathToCSV) {
		this.setPathToCSV(pathToCSV);
	}

	
	public void readCSV(){
		File csvFile = new File(getPathToCSV());
		
		if(csvFile.isFile()) {
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
				
				try {
					SQLiteDB db = new SQLiteDB();
					
					List<List<String>> goodEntry = new ArrayList<List<String>>();
					List<List<String>> badEntry = new ArrayList<List<String>>();

					String row;
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

					try {
						db.insertBatch(goodEntry);
					} catch (SQLException e) {
						System.out.println("Error handing off batch.");
						e.printStackTrace();
					}
					
					
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


	public static String getPathToCSV() {
		return pathToCSV;
	}


	public void setPathToCSV(String pathToCSV) {
		this.pathToCSV = pathToCSV;
	}

}
