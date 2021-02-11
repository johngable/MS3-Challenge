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

/**
 * This class is meant to be used for accessing the user's CSV file, as well as
 * passing off the collected data for the SQLiteDB to input.
 * 
 * CSV reading and Writing is handled by buffered readers/writers. Parsing is
 * done using String split, storing the split into an array, and changing the
 * array to a List of Strings.
 * 
 * 
 * @author johng
 *
 */
public class CSVHandler {

	private String pathToCSV; // Instance variable of the full csv path from a user
	private String fileName; // name of the file from parsing the path above

	/**
	 * Constructor for the handler
	 * 
	 * @param pathToCSV - User parameter for the csv path
	 */
	public CSVHandler(String pathToCSV) {
		this.setPathToCSV(pathToCSV);
		this.fileName = parseFileName(pathToCSV);
	}

	/**
	 * Bulk of the program.
	 * 
	 * Opens the csv from the entered path, parses, and adds records to a List of a
	 * List of Strings, that are either good or bad records.
	 * 
	 * After parsing, good records are passed to the SQLiteDB class, a log of all
	 * data is made, and the bad records are dumped to a new csv.
	 * 
	 */
	public void readCSV() {
		File csvFile = new File(pathToCSV);
		if (csvFile.isFile()) {
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));

				try {
					SQLiteDB db = new SQLiteDB(fileName);

					List<List<String>> goodEntry = new ArrayList<List<String>>();
					List<List<String>> badEntry = new ArrayList<List<String>>();

					String row;
					while ((row = csvReader.readLine()) != null) {
						String[] dataEntry = row.split(",");

						// Ensure all 11 fields are present for a good record
						if (dataEntry.length == 11) {
							goodEntry.add(Arrays.asList(dataEntry));
						} else {
							badEntry.add(Arrays.asList(dataEntry));
						}
					}

					// Parsing completed
					db.insertBatch(goodEntry);
					writeLog(goodEntry, badEntry);
					dumpBadCSVEntries(badEntry);

					csvReader.close();

				} catch (IOException e) {
					System.out.println("Error reading CSV rows.");
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				System.out.println("Unable to read file into buffer reader.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Writes the results log to a static path with the filename used as the log
	 * name. Uses the string list's sizes for result records.
	 * 
	 * @param goodEntry - Lists of all the good records
	 * @param badEntry  - List of all the bad records
	 */
	private void writeLog(List<List<String>> goodEntry, List<List<String>> badEntry) {
		Logger fileLogger = Logger.getLogger(fileName);

		try {
			FileHandler fileHandler = new FileHandler("./../" + fileName + ".log");
			fileLogger.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);

			fileLogger.info("\n" + (goodEntry.size() + badEntry.size() - 1) + " Total Records Received\n"
					+ (goodEntry.size()) + " Good Records\n" + (badEntry.size() - 1) + " Bad Records\n");

		} catch (SecurityException | IOException e) {
			System.out.println("Error opening a file handler for file with the name: " + fileName);
			e.printStackTrace();
		}

	}

	/**
	 * Receives the fault records list, and dumps then to a csv in a static path
	 * using the fileName with an attached -bad.csv for distinction.
	 * 
	 * @param badEntry - String lists of every faulty entry
	 */
	private void dumpBadCSVEntries(List<List<String>> badEntry) {

		if (!badEntry.isEmpty()) {
			try {
				FileWriter csvWriter = new FileWriter("./../" + fileName + "-bad.csv");

				for (List<String> badRow : badEntry) {
					csvWriter.append(String.join(",", badRow));
					csvWriter.append("\n");
				}

				csvWriter.flush();
				csvWriter.close();

			} catch (IOException e) {
				System.out.println("Failed to write out list of bad entries.");
				e.printStackTrace();
			}
		} else {
			System.out.println("No bad entries out output.");
		}

	}

	/**
	 * Sets the instance variable for csv path.
	 * 
	 * @param pathToCSV - User entered CSV path
	 */
	public void setPathToCSV(String pathToCSV) {
		this.pathToCSV = pathToCSV;
	}

	/**
	 * Parses out the path to a csv and extracts the file name for future use.
	 * 
	 * @param pathToCVS - the path to a csv
	 */
	public String parseFileName(String pathToCSV) {
		String[] tempSplit = pathToCSV.split("/");
		String fileName = tempSplit[tempSplit.length - 1].replace(".csv", "");

		return fileName;
	}

}
