package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.SQLiteDB;

class SQLiteDBTest {

	@Test
	void testFill() {
		SQLiteDB db = new SQLiteDB();
		
		db.insertToDB("a", "b", "c", "d", "e", "f", "g", true, false, "j");
		
	}

}
