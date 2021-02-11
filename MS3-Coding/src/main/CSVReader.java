package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CSVReader {

	private String pathToCSV;
	
	private CSVReader(String pathToCSV) {
		this.setPathToCSV(pathToCSV);
	}
	
	public void readCSV() throws FileNotFoundException {
		File csvFile = new File(pathToCSV);
		
		if(csvFile.isFile()) {
			BufferedReader csvReader = new BufferedReader(new FileReader(pathToCSV));
		}
	}

	public String getPathToCSV() {
		return pathToCSV;
	}

	public void setPathToCSV(String pathToCSV) {
		this.pathToCSV = pathToCSV;
	}
}
