package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * This class is responsible for all interactions with the SQLite DB.
 * 
 * It can initialize a db, create a table, or delete a table using the file
 * name.
 * 
 * All data entry is entered as a batch. (For future reference, batches can be
 * divided up if time constraint not met)
 * 
 * @author johng
 *
 */
public class SQLiteDB {

	private static String fileName; // File name from the csv file
	private static String DBPath; // Required DB path for JDBC connections

	/**
	 * Constructor takes filename and builds the required jdbc pathing.
	 * 
	 * @param fileName
	 */
	public SQLiteDB(String fileName) {
		SQLiteDB.fileName = fileName;
		SQLiteDB.DBPath = "jdbc:sqlite:./" + fileName + ".db";
	}

	/**
	 * Initializes a database and table using the given file name
	 * 
	 * Requirements did not specify alternative columns headers etc. Stuck with
	 * emailed data example headers.
	 * 
	 */
	protected static void createDB() {

		try {
			Connection conn = DriverManager.getConnection(DBPath);

			String sql = "Create table IF NOT EXISTS " + fileName + "(" + "entry_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "A varchar(50) NOT NULL," + "B varchar(50) NOT NULL," + "C varchar(50) NOT NULL,"
					+ "D varchar(10) NOT NULL," + "E varchar(128) NOT NULL," + "F varchar(50) NOT NULL,"
					+ "G varchar(50) NOT NULL," + "H boolean NOT NULL," + "I boolean NOT NULL,"
					+ "J varchar(50) NOT NULL)";

			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Successfully created table.");

		} catch (SQLException e) {
			System.out.println("Error initializing database and/or table: " + e.getMessage());
		}
	}

	/**
	 * Drops table if it exists (the table name should also match the file name if
	 * built from this class)
	 */
	protected void dropTable() {
		String sql = "DROP TABLE IF EXISTS '" + fileName + "';";

		try {
			Connection conn = DriverManager.getConnection(DBPath);
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Successfully dropped table.");

		} catch (SQLException e) {
			System.out.println("Error dropping table: " + e.getMessage());
		}
	}

	/**
	 * Inserts string lists as batch for time constraints. Currently one batch
	 * sufficed for given example. Groups of batches can be added later for larger
	 * data sets if needed.
	 * 
	 * @param goodEntry - String list of valid entries parsed from the CSV Handler
	 */
	protected void insertBatch(List<List<String>> goodEntry) {

		createDB();

		String sql = "INSERT INTO " + fileName + " (A, B, C, D, E, F, G, H, I, J)" + " VALUES(?,?,?,?,?,?,?,?,?,?);";

		try (Connection connection = DriverManager.getConnection(DBPath);
				PreparedStatement statement = connection.prepareStatement(sql);) {

			connection.setAutoCommit(false);

			double startTime = System.currentTimeMillis();
			System.out.println("Preparing " + goodEntry.size() + " rows for entry.");
			for (List<String> entry : goodEntry) {
				statement.setString(1, entry.get(0));
				statement.setString(2, entry.get(1));
				statement.setString(3, entry.get(2));
				statement.setString(4, entry.get(3));
				statement.setString(5, entry.get(4));
				statement.setString(6, entry.get(5));
				statement.setString(7, entry.get(6));
				statement.setBoolean(8, Boolean.getBoolean(entry.get(7)));
				statement.setBoolean(9, Boolean.getBoolean(entry.get(8)));
				statement.setString(10, entry.get(9));

				statement.addBatch();
			}

			statement.executeBatch();

			double endTime = System.currentTimeMillis();

			System.out.println("Estimated Completion Time: " + (endTime - startTime) + "ms");
			connection.commit();
			statement.close();

		} catch (SQLException e) {
			System.out.println("DB Connection Failed");
			e.printStackTrace();
		}

	}

}
