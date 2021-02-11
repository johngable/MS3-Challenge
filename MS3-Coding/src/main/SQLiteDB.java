package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteDB {

	private static String fileName;
	private static String DBPath;

	public SQLiteDB(String fileName) {
		this.fileName = fileName;
		this.DBPath = "jdbc:sqlite:./" + fileName + ".db";
	}

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
			System.out.println(e.getMessage());
		}
	}

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

	protected void insertBatch(List<List<String>> goodEntry) throws SQLException {

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

	protected void insertToDB(String a, String b, String c, String d, String e, String f, String g, Boolean h, Boolean i,
			String j) {

		String sql = "INSERT INTO ms3Interview (A, B, C, D, E, F, G, H, I, J)" + " VALUES(?,?,?,?,?,?,?,?,?,?);";

		try (Connection conn = DriverManager.getConnection(DBPath);
				PreparedStatement statement = conn.prepareStatement(sql);) {

			statement.setString(1, a);
			statement.setString(2, b);
			statement.setString(3, c);
			statement.setString(4, d);
			statement.setString(5, e);
			statement.setString(6, f);
			statement.setString(7, g);
			statement.setBoolean(8, h);
			statement.setBoolean(9, i);
			statement.setString(10, j);

			statement.executeUpdate();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
	}

}
