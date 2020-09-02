package bankingApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDaoImpl implements BankAccountDao {
	public static ConnFactory cf= ConnFactory.getInstance();
	//view all customer information
	public List<BankAccount> getAllBankAccounts() throws SQLException {
		List<BankAccount> accList=new ArrayList<BankAccount>();
		Connection conn= cf.getConnection();
		String sql = "select * from \"bank_accounts\"";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		BankAccount a=null;
		while(rs.next()) {
			a= new BankAccount(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
					rs.getString(5),rs.getInt(6));
			accList.add(a);
		}
		return accList;
	}
	
	
	public List<BankAccount> getAllBankAccountsFEU(String fname, String lname) throws SQLException{
		List<BankAccount> accList= new ArrayList<BankAccount>();
		Connection conn= cf.getConnection();
		String sql = "Select * from \"bank_accounts\" where \"user_first_name\" = ? and \"user_last_name\" =?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, fname);
		pstmt.setString(2, lname);
		ResultSet rs = pstmt.executeQuery();
		BankAccount a = null;
		while(rs.next()) {
			a= new BankAccount(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
					rs.getString(5),rs.getInt(6));
			accList.add(a);
		}
		return accList;
	}
	
	//create Bank Account
	public void insertAccount(String fname, String lname, String Uname, String Pw, int funds) throws SQLException {
		
		Connection conn = cf.getConnection();
        String sql = "INSERT INTO \"bank_accounts\" values(nextval('mySeq'),?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
        pstmt.setString(1, fname);
        pstmt.setString(2, lname);
        pstmt.setString(3, Uname);
        pstmt.setString(4, Pw);
        pstmt.setInt(5, funds);
        pstmt.executeUpdate();
	}
	//Approve Application
	public void approveApplications(String pwd) throws SQLException{
		
		Connection conn = cf.getConnection();
		String sql = "INSERT INTO \"bank_accounts\" select * from \"users\" where \"user_pw\" = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, pwd);
		pstmt.executeUpdate();
	}
	
	
	//method to update funds for transactions 
	public void updateFunds(int funds, String pwd) throws SQLException{
		Connection conn = cf.getConnection();
        String sql = "UPDATE \"bank_accounts\" set \"user_funds\" =? where \"user_pw\" = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql); 
        pstmt.setInt(1, funds);
		pstmt.setString(2, pwd);
        pstmt.executeUpdate();
	}
	//deleting Customers Bank Account
	public void deleteAccount(String pwd) throws SQLException{
		Connection conn = cf.getConnection();
        String sql = "delete from \"bank_accounts\" where \"user_pw\" =?";
        PreparedStatement pstmt = conn.prepareStatement(sql); 
        pstmt.setString(1,pwd);
        pstmt.executeUpdate();
	}
}
