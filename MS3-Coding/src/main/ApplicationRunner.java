package main;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ApplicationRunner {

	public static void main(String[] args) throws SQLException {

		Scanner sc = new Scanner(System.in);
		
		System.out.println("Select an option: ");
		System.out.println("1: Enter new or additional data using a CSV.");
		System.out.println("2: Drop existing table.");


		int choice = Integer.parseInt(sc.nextLine());
		
		if(choice == 1) {
			System.out.print("Please enter the path to your CSV: ");
			
			if(sc.hasNext()) {
				String pathToCSV = sc.nextLine();
				CSVHandler reader = new CSVHandler(pathToCSV);
				reader.readCSV();
				
			}
			
			sc.close();
		}
		else {
			System.out.println("Please enter the name of the Table to drop (usually name of csv file): ");
			if(sc.hasNext()) {
				String tableToDrop = sc.nextLine();
				SQLiteDB db = new SQLiteDB(tableToDrop);
				db.dropTable();
				sc.close();
			}
		}
		

		
		

		
		

	}

}
