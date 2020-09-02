package bankingApp;

public class BankAccount {
	private int bank_id;
	private String user_First_Name;
	private String user_Last_Name;
	private String user_U_Name;
	private String user_PW;
	private int funds;
	
	public BankAccount() {
		super();
	}
	

	public BankAccount(int bank_id, String user_First_Name, String user_Last_Name, String user_U_Name,
			String user_PW, int funds) {
		super();
		this.bank_id = bank_id;
		this.user_First_Name= user_First_Name;
		this.user_Last_Name = user_Last_Name;
		this.user_U_Name = user_U_Name;
		this.user_PW = user_PW;
		this.funds = funds;
	}


	public int getbank_id() {
		return bank_id;
	}


	public void setUser_id(int bank_id) {
		this.bank_id = bank_id;
	}


	public String getUser_First_Name() {
		return user_First_Name;
	}


	public void setUser_First_Name(String user_First_Name) {
		this.user_First_Name = user_First_Name;
	}


	public String getUser_Last_Name() {
		return user_Last_Name;
	}


	public void setUser_Last_Name(String user_Last_Name) {
		this.user_Last_Name = user_Last_Name;
	}


	public String getUser_U_Name() {
		return user_U_Name;
	}


	public void setUser_U_Name(String user_U_Name) {
		this.user_U_Name = user_U_Name;
	}

	
	public String getUser_PW() {
		return user_PW;
	}


	public void setUser_PW(String user_PW) {
		this.user_PW = user_PW;
	}
	
	
	public int getFunds() {
		return funds;
	}


	public void setFunds(int funds) {
		this.funds = funds;
	}


	@Override
	public String toString() {
		return "[Bank Id=" + bank_id + ", First Name=" + user_First_Name + 
				", Last Name=" + user_Last_Name + ", Username=" + user_U_Name +
				", Password=" + user_PW + ", Account Balance="+ funds +"]\n";
	}
	
	
}

