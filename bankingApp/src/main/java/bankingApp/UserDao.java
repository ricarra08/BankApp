package bankingApp;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
	
	public List<User> getAllUsers() throws SQLException;
	
	public void insertUser(String fname, String lname, String Uname, String Pw) throws SQLException;
 }
