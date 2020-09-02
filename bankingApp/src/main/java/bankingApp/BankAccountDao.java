package bankingApp;

import java.sql.SQLException;
import java.util.List;

public interface BankAccountDao {
	public List<BankAccount> getAllBankAccounts() throws SQLException;
	
	
	public void insertAccount(String fname, String lname, String Uname, String Pw, int funds) throws SQLException;
	
}
