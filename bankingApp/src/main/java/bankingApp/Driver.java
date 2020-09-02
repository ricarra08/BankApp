package bankingApp;

import java.sql.SQLException;

public class Driver {
	public static ConnFactory cf= ConnFactory.getInstance();
	public static void main(String[] args) {

		atm bank = new atm();
		bank.battery();
		
	}
}