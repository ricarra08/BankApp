package bankingApp;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
	public static ConnFactory cf= ConnFactory.getInstance();
	//retrieves all applications
	public List<User> getAllUsers() throws SQLException {
		List<User> userList=new ArrayList<User>();
		Connection conn= cf.getConnection();
		Statement stmt= conn.createStatement();
		ResultSet rs=stmt.executeQuery("select * from \"users\"");
		User a=null;
		while(rs.next()) {
			a= new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
					rs.getString(5));
			userList.add(a);
		}
		return userList;
	}

	//register applications to User_table
	public void insertUser(String fname, String lname, String Uname, String Pw) throws SQLException {
		
		Connection conn = cf.getConnection();
        String sql = "INSERT INTO \"users\" values(nextval('mySeq'),?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
        pstmt.setString(1, fname);
        pstmt.setString(2, lname);
        pstmt.setString(3, Uname);
        pstmt.setString(4, Pw);
        pstmt.executeUpdate();
        
		}
	//deny application/remove user from user table
	public void denyApplication(String pwd) throws SQLException{
		Connection conn = cf.getConnection();
        String sql = "delete from \"users\" where \"user_pw\" =?";
        PreparedStatement pstmt = conn.prepareStatement(sql); 
        pstmt.setString(1,pwd);
        pstmt.executeUpdate();
	}

}
