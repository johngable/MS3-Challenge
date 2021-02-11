package main;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDB {

	private static String DBPath = "jdbc:sqlite:C:\\sqlite\\db\\ms3.db\\";
	
	private static void createDB() {
		
		try {
			Connection conn = DriverManager.getConnection(DBPath);
			
			String sql = "Create table IF NOT EXISTS ms3Interview("
					+ "entry_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "A varchar(50) NOT NULL,"
					+ "B varchar(50) NOT NULL,"
					+ "C varchar(50) NOT NULL,"
					+ "D varchar(10) NOT NULL,"
					+ "E varchar(512) NOT NULL,"
					+ "F varchar(50) NOT NULL,"
					+ "G varchar(50) NOT NULL,"
					+ "H boolean NOT NULL,"
					+ "I boolean NOT NULL,"
					+ "J varchar(50) NOT NULL)";
			
			
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
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
	
	
//	public String makeStatement(List<String> entry) {
//		String sql = "INSERT INTO ms3Interview (A, B, C, D, E, F, G, H, I, J)"
//				+ " VALUES(?,?,?,?,?,?,?,?,?,?);";
//		
//		try (Connection conn = DriverManager.getConnection(DBPath);
//				PreparedStatement statement = conn.prepareStatement(sql);){
//							statement.setString(0, entry.get(0));
//							statement.setString(1, entry.get(1));
//							statement.setString(2, entry.get(2));
//							statement.setString(3, entry.get(3));
//							statement.setString(4, entry.get(4));
//							statement.setString(5, entry.get(5));
//							statement.setString(6, entry.get(6));
//							statement.setBoolean(7, Boolean.getBoolean(entry.get(8)));
//							statement.setBoolean(8, Boolean.getBoolean(entry.get(9)));
//							statement.setString(9, entry.get(10));
//							
//							statement.addBatch();
//							
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
//							
//							
//		
//	}
	
	public void insertBatch(List<List<String>> goodEntry) {
		String sql = "INSERT INTO ms3Interview (A, B, C, D, E, F, G, H, I, J)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?);";
		
		Connection conn;
		try {
			conn = DriverManager.getConnection(DBPath);
			PreparedStatement statement = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			System.out.println("Preparing "+goodEntry.size()+" entries.");
			for(List<String> entry: goodEntry) {
				statement.setString(1, entry.get(0));
				statement.setString(2, entry.get(1));
				statement.setString(3, entry.get(2));
				statement.setString(4, entry.get(3));
				statement.setString(5, entry.get(4));
				statement.setString(6, entry.get(5));
				statement.setString(7, entry.get(7));
				statement.setBoolean(8, Boolean.getBoolean(entry.get(8)));
				statement.setBoolean(9, Boolean.getBoolean(entry.get(9)));
				statement.setString(10, entry.get(10));
				
				statement.addBatch();
			}
			
			statement.executeBatch();
			
			conn.commit();
			//System.out.println("Added: " + statement.executeBatch() +" new entries.");
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		dropTables();
		createDB();		
	}


	
	
	
}
