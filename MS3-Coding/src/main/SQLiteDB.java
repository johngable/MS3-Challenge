package main;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {

	private static String DBPath = "jdbc:sqlite:C:\\sqlite\\db\\ms3.db\\";
	
	private static void createDB() {
		
		try {
			Connection conn = DriverManager.getConnection(DBPath);
			
			String sql = "Create table ms3Interview("
					+ "entry_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "A varchar(50) NOT NULL,"
					+ "B varchar(50) NOT NULL,"
					+ "C varchar(50) NOT NULL,"
					+ "D varchar(10) NOT NULL,"
					+ "E varchar(50) NOT NULL,"
					+ "F varchar(50) NOT NULL,"
					+ "G varchar(50) NOT NULL,"
					+ "H boolean NOT NULL,"
					+ "I boolean NOT NULL,"
					+ "J varchar(50) NOT NULL)";
			
			
			Statement statement = conn.createStatement();
			statement.executeQuery(sql);
			System.out.println("Successfully created table.");
			

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	
	protected static void dropTables() {
		String sql = "DROP TABLE IF EXISTS 'ms3Interview';";
		
		try {
			Connection conn = DriverManager.getConnection(DBPath);
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Successfully dropped table.");
			

        } catch (SQLException e) {
            System.out.println("Error dropping table: " + e.getMessage());
        }
	}
	
	
	public static void insertToDB(String a, String b, String c, String d, String e, String f, String g, Boolean h, Boolean i, String j) {
		
		String sql = "INSERT INTO ms3Interview (A, B, C, D, E, F, G, H, I, J)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?);";
		
		try (Connection conn = DriverManager.getConnection(DBPath);
				PreparedStatement statement = conn.prepareStatement(sql);){
			
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
	
	
	public static void main(String[] args) {
		//createDB();
		//insertToDB("a", "b", "c", "d", "e", "f", "g", true, false, "j");
		//dropTables();
	}


	
	
	
}
