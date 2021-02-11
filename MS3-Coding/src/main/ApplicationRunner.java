package main;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ApplicationRunner {

	public static void main(String[] args) throws SQLException {
		
		System.out.println("Please enter the path to your CSV: ");
		
		Scanner sc = new Scanner(System.in);
		String pathToCSV = sc.nextLine();
		
		CSVReader reader = new CSVReader(pathToCSV);
		reader.readCSV();

	}

}
