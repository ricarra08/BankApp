package bankingApp;

import java.io.Serializable;

public class JointApp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String fl1;
	String fl2;
	String uname;
	String pw;
	int funds;
	
	//String uname2;
	//String pw2;
	
	public JointApp(String fl1, String fl2, String uname, String pw, int funds) {
		this.fl1 = fl1;
		this.fl2 = fl2;
		this.uname = uname;
		this.pw = pw;
		this.funds = funds;
		
		//this.uname2 = uname2;
		//this.pw2 = pw2;
	}
	
	public String toString () {
		return "First Account Holder: " + fl1 + " -- Second Account Holder: " + fl2;
		
	}

	public String getfl1() {
		return fl1;
	}

	public String getfl2() {
		return fl2;
	}

	public String getUname() {
		return uname;
	}

	public String getPW() {
		return pw;
	}

	public int getFunds() {
		return funds;
	}

	/*public String getUname2() {
		return uname2;
	}
	
	public String getPw2() {
		return pw2;
	}*/
}
