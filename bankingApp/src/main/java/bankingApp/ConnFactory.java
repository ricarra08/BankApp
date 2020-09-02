package bankingApp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnFactory {
	//singleton Factory
	//private static instance of itself
	private static ConnFactory cf= new ConnFactory();
	
	//private no args constructor
	private ConnFactory() {
		super();
	}
	
	//public static synchronized "getter" method
	public static synchronized ConnFactory getInstance() { //Only one thread can utilize this at a time
		if (cf==null) { //if we don't have an instance of our ConnFatctory class we make a new one
			cf= new ConnFactory();
		}
		return cf;
	}
	
	//Methods that do stuff
	public Connection getConnection() {
		Connection conn= null;
		Properties prop= new Properties(); //allows us to not have to hardcode url and password
		
		try {
			prop.load(new FileReader("database.properties"));
			conn=DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("user"),prop.getProperty("password"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}