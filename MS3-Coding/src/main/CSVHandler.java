package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CSVHandler {

	private static String pathToCSV;
	private static String fileName;

	public CSVHandler(String pathToCSV) {
		this.setPathToCSV(pathToCSV);
	}

	public void readCSV() {
		File csvFile = new File(getPathToCSV());

		if (csvFile.isFile()) {
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));

				try {
					SQLiteDB db = new SQLiteDB();

					List<List<String>> goodEntry = new ArrayList<List<String>>();
					List<List<String>> badEntry = new ArrayList<List<String>>();

					String row;
					while ((row = csvReader.readLine()) != null) {
						String[] dataEntry = row.split(",");
						if (dataEntry.length == 11) {
							goodEntry.add(Arrays.asList(dataEntry));
						} else {
							badEntry.add(Arrays.asList(dataEntry));
						}
					}

					try {
						db.insertBatch(goodEntry);
						writeLog(goodEntry, badEntry);
						dumpBadCSVEntries(badEntry);
					} catch (SQLException e) {
						System.out.println("Error handing off batch.");
						e.printStackTrace();
					}
					
					csvReader.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void writeLog(List<List<String>> goodEntry, List<List<String>> badEntry) {
		Logger fileLogger = Logger.getLogger(fileName);

		try {
			FileHandler fileHandler = new FileHandler("./../" + fileName + ".log");
			fileLogger.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);

			fileLogger.info("\n" + (goodEntry.size() + badEntry.size()-1) + " Total Records Received\n" + (goodEntry.size())
					+ " Good Records\n" + (badEntry.size()-1) + " Bad Records\n");

		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void dumpBadCSVEntries(List<List<String>> badEntry) {
		
		if(!badEntry.isEmpty()) {
			try {
				FileWriter csvWriter = new FileWriter("./../"+fileName+"-bad.csv");
				//csvWriter.append("A, B, C, D, E, F, G, H, I, J\n");
				
				for(List<String> badRow : badEntry) {
					csvWriter.append(String.join(",", badRow));
					csvWriter.append("\n");
				}
				
				csvWriter.flush();
				csvWriter.close();
			
			} catch (IOException e) {
				System.out.println("Failed to write out list of bad entries.");
				e.printStackTrace();
			}
		}else {
			System.out.println("No bad entries out output.");
		}
		
	}


	public static String getPathToCSV() {
		return pathToCSV;
	}

	public void setPathToCSV(String pathToCSV) {
		this.pathToCSV = pathToCSV;
		String[] tempSplit = pathToCSV.split("/");
		this.fileName = tempSplit[tempSplit.length - 1].replace(".csv", "");
		System.out.println(fileName);
	}

}
