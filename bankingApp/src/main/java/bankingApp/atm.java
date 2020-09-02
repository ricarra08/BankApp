package bankingApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class atm {
	
	//scanner class instantiated
	Scanner s = new Scanner(System.in);
	
	//ArrayLists used for object
	//ArrayList<Customer> custys = new ArrayList<Customer>();
	//ArrayList<Application> apply = new ArrayList<Application>();
	//ArrayList<JointApp> Japps = new ArrayList<JointApp>();
	String uname;
	String pw;
	String U2;
	String P2;
	String lname;
	String fname;
	int funds;
	
	//SerializationMethods o = new SerializationMethods();
	
	String BAun = "Big Daddy"; //hardcode BankAdmin Username
	String BApw = "BigPapa"; //hardcode BankAdmin password
	
	String Eu = "Big Mama"; //hardcode Employee Username
	String Ep = "BigMa"; //hardcode Employee password
	
	//ArrayLists used to deserialize data to and then iterate over
	ArrayList<Customer> custList = new ArrayList<Customer>();  
	ArrayList<Application> appList = new ArrayList<Application>();
	ArrayList<JointApp> JappList = new ArrayList<JointApp>();
	
	//method used to run our Bank Interface
	public void battery() {
		sys();
	}
	
	// The heart of our project is built using this if-else if-else branch
	public void sys() {
		UserDaoImpl app = new UserDaoImpl();
		BankAccountDaoImpl badi = new BankAccountDaoImpl();
		/**try {
			System.out.println(udi.getAllUsers());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}**/
		
		readfile(); //used to deserialize customer data
		readApps(); //used to deserialize normal application data
		readJA(); ////used to deserialize joint application data
		
		//Our "Seed" that begins our decision tree
		System.out.println("Login as:" + "\n" + "a) Customer" + "\n"
					+ "b) Employee"+"\n" + "c) Bank Admin" + "\n" 
					);
		// input users decision into string variable and then use conditionals to branch out to either 3 Users
		String p = s.nextLine();
		
		//Branch 1: Customer
		if (p.equals("a"))  {
			//Apply for an account or Login if account has been approved
			System.out.println("What would you like to do?"+"\n"
					+"a)Register"+ "\n"
					+ "b)Login to account" );
			//reads user input
			String c1 = s.nextLine();
			if( c1.equals("a")) {//branch 1a
				//asks user for what type of account they would like
				System.out.println("Would you like to apply for an : " + "\n" + " a) Account" );
				String o1 = s.nextLine();
				if (o1.equals("a")) {
					//asks for first name, last name, username, and password info
					System.out.println("What is your first name?");
					String fname = s.nextLine();
					System.out.println("What is your last name?");
					String lname = s.nextLine();
					System.out.println("Create New Username:");
					String uname = s.nextLine();
					System.out.println("Create Password:");
					//funds is set automatically to $10 because our policy required an amount greater than 0 to open an account
					int funds = 10;
					String pw = s.nextLine();
					//create new application object and then add to apply list
					appList.add(new Application (fname,lname,uname,pw,funds)); 
					try {
						app.insertUser(fname, lname, uname, pw);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//saves application to appplication.txt file
					writeApps(appList);
					//Let the user know their application is waiting for approval
					System.out.println("Your Account Application has been submitted!");
					sys(); //recall the system
				} else if (o1.equals("b")) { 
					// asks for Joint Account user info
					System.out.println("What is the first account holder's full name?");
					String fl1 = s.nextLine();
					System.out.println("What is the second account holder's full name?");
					String fl2 = s.nextLine();
					System.out.println("Create Username:");
					String uname1 = s.nextLine();
					System.out.println("Create Password:");
					int funds = 10;
					String pw1 = s.nextLine();
					//instantiates new JointApp Object and adds to Japps
					JappList.add(new JointApp (fl1, fl2, uname1, pw1, funds)); 
					writeJA(JappList);
					System.out.println("Your Joint Account Application has been submitted!");
					sys();
				}
			} else if(c1.equals("b")) {//branch 1b
				System.out.println("Input Username: ");
				//stores username to string variable
				String checkUname = s.nextLine();
				System.out.println("Input Password: ");
				//stores password to string variable
				String checkPwd = s.nextLine();
				for(int a=0; a<custList.size(); a++) {
					String pwCheck = custList.get(a).getPW();
					String userCheck = custList.get(a).getUN();
					if(pwCheck.equals(checkPwd) && userCheck.equals(checkUname)) {
						boolean loggedin = true;
						while(loggedin) {
							System.out.println("Your Account Information");
							System.out.println(custList.get(a));
							for (int i = 0 ; i < custList.size(); i++) {
								//if true, then transactions branch is open
								//if (custList.get(i).getUN().equals(checkUname) && custList.get(i).getPW().equals(checkPwd)) {
								//System.out.println(custList.get(i).getPW());
								if(findCusty(checkPwd).equals(custList.get(i).getPW())) {
									//decide transaction type
									System.out.println("Would you like to:" + "\n" + "a) Withdraw" + "\n" + "b) Deposit" 
														+"\n"+ "c) Transfer" + "\n" + "d) Create New Account using existing User Information"+ "\n"
														+ "e) View All Bank Acounts tied to this User"  + "\n" +"f) Log Out");
									String c2b = s.nextLine(); 
									if (c2b.contentEquals("a")) { 
										//Withdrawal branch
										int $ = custList.get(i).get$(); 
										System.out.println("How much would you like to withdraw?"); 
										int less$ = Integer.parseInt(s.nextLine()); 
										//overdraft validation
										if(less$ <= $) {
											int newF = $ - less$;
											//replaces old funds with new funds
											custList.get(i).setFunds(newF);
											//Show remaining balance
											System.out.println("New Balance: $"+custList.get(i).get$());
											//saves new data to customerlist
											writefile(custList);
											try {
												badi.updateFunds(newF, checkPwd);
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										} else {
											//let them know they can't do overdrafts
											System.out.println("You can't cheat money!"); 
											//sys();
										}
									} else if (c2b.equals("b")) { 
										//Deposit branch
										int $ = custList.get(i).get$(); //change funds to double
										System.out.println("How much would you like to Deposit?"); 
										int plus$ = Integer.parseInt(s.nextLine()); 
										int newF = $ + plus$;
										custList.get(i).setFunds(newF);
										System.out.println("New Balance: $"+custList.get(i).get$());
										writefile(custList);
										try {
											badi.updateFunds(newF, checkPwd);
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									} else if (c2b.equals("c")){
										//Transfers branch
										System.out.println("Enter Account Username that you will be transferring money to: ");
										String h = s.nextLine();
										System.out.println("Enter Account Password that you will be transferring money to: ");
										String h1 = s.nextLine();
										for (int t = 0; t < custList.size(); t++ ) {
											if ((custList.get(t).getUN().equals(h) && custList.get(t).getPW().equals(h1)) && t!=i) {
												System.out.println("Transfer has begun\n" + "How much money would you like to Transfer?");
												int m$ = Integer.parseInt(s.nextLine());
												if(m$ <= custList.get(i).get$()) {
													int newF = custList.get(i).get$() - m$;
													custList.get(i).setFunds(newF);
													int nf = custList.get(t).get$() + m$;
													custList.get(t).setFunds(nf);
													try {
														badi.updateFunds(newF, checkPwd);
													} catch (SQLException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													
													try {
														badi.updateFunds(nf, h1);
													} catch (SQLException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													System.out.println("New Balance for 1st Account: $"+custList.get(i).get$() + "\n"
																	+	"New Balance for 2nd Account: $"+custList.get(t).get$());
												} else {
													System.out.println("You're not slick!!!"); //Let them know they can't mess around
													sys();
												}
											}
										}
										writefile(custList);
									} else if (c2b.equals("d")) {
										System.out.println("Create New Password for new Bank Account:");
										String newP = s.nextLine();
										custList.add(new Customer (custList.get(i).fname,custList.get(i).lname,checkUname,newP, 10)); 
										//saves application to appplication.txt file
										writefile(custList);
										try {
											badi.insertAccount(custList.get(i).fname,custList.get(i).lname,checkUname,newP, 10);
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										//Let the user know their application is waiting for approval
										System.out.println("A New Bank Account has been created under this user!");
									}else if(c2b.contentEquals("e")) {
										System.out.println("DataBase Data");
										try {
											System.out.println(badi.getAllBankAccountsFEU(custList.get(i).fname, custList.get(i).lname));
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										
									}else if (c2b.equals("f")) {
										loggedin = false;

									
																		}
									//sys();
								} //sys();
							}
						} sys();
					}
				}
				
				//System.out.println(checkPwd);
				//iterate over customer arraylist to check if username and password are correct
		
			} else {
				System.out.println("Invalid input, try again.");
				sys();
			}
		}
		
// Employee branch 2		
		
		else if(p.equals("b")) {
			//ask for employee info
	 		System.out.println("Login in to Employee Account" + "\n" + "What is your Username?");
	 		String eu = s.nextLine();
	 		System.out.println("What is your Password?");
	 		String ep = s.nextLine(); 
	 		//boolean shorthand operator used to validate user information
	 		if (eu.equals(Eu) && ep.equals(Ep)) {
	 			System.out.println("Would you like to:" +"\n"+ "a) View Customer Information" + "\n"
									+ "b) Approve or deny normal applications" + "\n" + "c) Approve or deny joint applications");
	 			String e1 = s.nextLine();
	 			if(e1.equals("a")) { //View Customer Information branch
	 					for (int i=0; i< custList.size(); i++){
	 						//iterates over custList and prints information
	 						System.out.println(custList.get(i) + "\n");
	 					} sys();
	 					
	 			} else if(e1.equals("b")) { //Approve or deny normal applications branch
	 				System.out.println("List of Normal Accounts to be Approved:");
	 				//loops through and prints out list of all accounts to be approved with identifying number
	 				for(int i=0; i<appList.size(); i++) {
						System.out.println(i+1 + ": " + appList.get(i));
					}
					System.out.print('\n');
	 				System.out.println("Would you like to:"+ "\n" + "a) Approve Normal Accounts" + "\n" +
	 						"b) Deny Normal Accounts");
	 				String d2b = s.nextLine();
	 				if (d2b.equals("a")) { // approve normal accounts branch
	 					//use identifying numbers from prior console print out to identify which application to accept
	 					System.out.println("Enter the number of the account you want to approve from the list above. Enter 0 if you don't want to approve any accounts.");
	 					int n = Integer.parseInt(s.nextLine());
	 					//create employee object
	 					Employee e = new Employee();
	 					ArrayList<Application> newApproved = new ArrayList<Application>();
	 					//employee object calls approve application method, assigns newly approved application to array list
	 					newApproved = e.approveApplication(appList, n);
	 					//serializes new list of pending applications
	 					writeApps(appList);
	 					System.out.println("Insert password of User Application to confirm approval:");
	 					String pwd = s.nextLine();
	 					try {
							badi.approveApplications(pwd);
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
	 					
	 					try {
							app.denyApplication(pwd);
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
	 					//ensures there is an newly approved account to retrieve info from
	 					if(!newApproved.isEmpty()) {
	 						//use getter methods to take application data and create a new customer
	 						Customer newUser = new Customer(newApproved.get(0).getF1(), newApproved.get(0).getL1(), newApproved.get(0).getU1(), newApproved.get(0).getP1(), newApproved.get(0).getFunds());
		 					//adds new customer to comprehensive customer list
	 						custList.add(newUser);
	 						//serializes updated customer list
		 					writefile(custList);
	 					}
	 					
	 				} else if(d2b.equals("b")){ //deny normal accounts branch
	 					//use identifying numbers from prior console print out to identify which application to deny
	 					System.out.println("Enter the number of the account you want to deny from the list above. Enter 0 if you don't want to deny any accounts.");
	 					int n = Integer.parseInt(s.nextLine());
	 					//create employee object
	 					Employee e = new Employee();
	 					//ArrayList<Application> newDenied = new ArrayList<Application>();
	 					//remove denied application from pending applications
	 					e.denyApplication(appList, n);
	 					//serialize updated app list
	 					writeApps(appList);
	 					System.out.println("Insert password of User Application to confirm denial:");
	 					String pwd = s.nextLine();
	 					try {
							app.denyApplication(pwd);
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
	 				}
	 			
	 			
	 			} else if (e1.equals("c")) { // joint account approval/denial branch, methods work similarly to normal accounts, except uses Japplist instead of applist since different application types have different implementations
	 				System.out.println("List of Joint Accounts to be Approved:");
					for(int i=0; i<JappList.size(); i++) {
						System.out.println(i+1 + ": " + JappList.get(i));
					}
					System.out.print('\n');
	 				System.out.println("Would you like to:"+ "\n" + "a) Approve Joint Accounts" + "\n" +
	 						"b) Deny Joint Accounts");
	 				String d2b = s.nextLine();
	 				if (d2b.equals("a")) {
	 					//call approve account
	 					System.out.println("Enter the number of the account you want to approve from the list above. Enter 0 if you don't want to approve any accounts.");
	 					int n = Integer.parseInt(s.nextLine());
	 					Employee e = new Employee();
	 					ArrayList<JointApp> newApproved = new ArrayList<JointApp>();
	 					newApproved = e.approveJointApplication(JappList, n);
	 					writeJA(JappList);
	 					Customer newUser = new Customer(newApproved.get(0).getfl1(), newApproved.get(0).getfl2(), newApproved.get(0).getUname(), newApproved.get(0).getPW(), newApproved.get(0).getFunds());
	 					custList.add(newUser);
	 					writefile(custList);
	 				} else if(d2b.equals("b")){
	 					//call deny method
	 					System.out.println("Enter the number of the account you want to deny from the list above. Enter 0 if you don't want to deny any accounts.");
	 					int n = Integer.parseInt(s.nextLine());
	 					Employee e = new Employee();
	 					//ArrayList<JointApp> newDenied = new ArrayList<JointApp>();
	 					e.denyJointApplication(JappList, n);
	 					writeJA(JappList);
	 				} 
	 			} else { //if input from scanner is not valid
	 				System.out.println("Invalid input, try again.");
	 			}
	 			sys();
	 		} else { // protects employee account from those without knowledge of password
	 			System.out.println("You are not Big Mama!!!");
	 		}
	 		sys();
		}
		
 // Bank Admin branch 3	
		
		else if(p.equals("c")) {
			//login info for Bank Admin
			System.out.println("Login in to account" + "\n" + "What is your Username?");
			String bu = s.nextLine();
			System.out.println("What is your Password?");
			String bp = s.nextLine(); 
			// validates login input, bank admin actions can only be done inside this if block
			if (bu.equals(BAun) && bp.equals(BApw)) {
			
				System.out.println("a) View Accounts" + "\n" + "b) Edits Accounts" + "\n");
				String d1 = s.nextLine();
				if(d1.equals("a")) { //View Accounts branch
					System.out.println("Serialized Data");
					for (int x=0; x < custList.size(); x++){
						System.out.println(custList.get(x) + "\n");
						
					}
					
					System.out.println("DataBase Data");
					try {
						System.out.println(badi.getAllBankAccounts());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					/*for(int i=0; i<custList.size(); i++) {
						System.out.println(i+1 + ": " + custList.get(i));
					}*/
					sys();
				} else if(d1.contentEquals("b")){ // Edit Accounts branch
					System.out.println("Would you like to:"+ "\n" + "a) Approve/Deny Normal Accounts" + "\n" +
									"b) Transaction" + "\n" +"c) Delete Accounts" + "\n" + "d) Create Accounts");
					String d2 = s.nextLine();
					if(d2.equals("a")) { //Approve/Deny Normal Accounts branch, same implementation as employee
						System.out.println("List of Normal Accounts to be Approved:");
		 				for(int i=0; i<appList.size(); i++) {
							System.out.println(i+1 + ": " + appList.get(i));
						}
						System.out.print('\n');
						System.out.println("Would you like to:"+ "\n" + "a) Approve Account" + "\n" +
							"b) Deny Accounts");
						String d2a = s.nextLine();
						if (d2a.equals("a")) { //approve normal accounts branch
							System.out.println("Enter the number of the account you want to approve from the list above. Enter 0 if you don't want to approve any accounts.");
		 					int n = Integer.parseInt(s.nextLine());
		 					Employee e = new Employee();
		 					ArrayList<Application> newApproved = new ArrayList<Application>();
		 					newApproved = e.approveApplication(appList, n);
		 					writeApps(appList);
		 					System.out.println("Insert password of User Application to confirm approval:");
		 					String pwd = s.nextLine();
		 					try {
								badi.approveApplications(pwd);
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
		 					
		 					try {
								app.denyApplication(pwd);
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} 
		 					if(!newApproved.isEmpty()) {
		 						Customer newUser = new Customer(newApproved.get(0).getF1(), newApproved.get(0).getL1(), newApproved.get(0).getU1(), newApproved.get(0).getP1(), newApproved.get(0).getFunds());
			 					custList.add(newUser);
			 					writefile(custList);
		 					}
						} else if(d2a.equals("b")){ //deny normal accounts branch
							System.out.println("Enter the number of the account you want to deny from the list above. Enter 0 if you don't want to deny any accounts.");
		 					int n = Integer.parseInt(s.nextLine());
		 					Bankadmin ba = new Bankadmin();
		 					//ArrayList<Application> newDenied = new ArrayList<Application>();
		 					ba.denyApplication(appList, n);
		 					writeApps(appList);
		 					System.out.println("Insert password of User Application to confirm denial:");
		 					String pwd = s.nextLine();
		 					try {
								app.denyApplication(pwd);
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
						sys();
					
					} else if (d2.equals("b")) { // Transactions branch
						//prints out all customers
						for (int x=0; x < custList.size(); x++){
							System.out.println(custList.get(x) + "\n");
						} 
						//bank admin inputs name of account to edit
						System.out.println("Input Password of account you would like to make a transaction for......");
						String pwd = s.nextLine();
						for (int n = 0; n < custList.size(); n++) {
							//ensures username can be found in customer list
							if (custList.get(n).getPW().equals(pwd)) {
								System.out.println("Would you like to:" + "\n" + "a) Withdraw" + "\n" + "b) Deposit" 
										+"\n"+ "c) Transfer");
								String d3a = s.nextLine(); 
								if(d3a.contentEquals("a")) { //Withdraw branch
									int $ = custList.get(n).get$(); 
									System.out.println("How much would you like to withdraw?"); 
									int less$ = Integer.parseInt(s.nextLine()); 
									int newF = $ - less$;
									custList.get(n).setFunds(newF);
									System.out.println("Remaining Balance: $"+custList.get(n).get$());
									writefile(custList);
									try {
										badi.updateFunds(newF, pwd);
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									sys();
								} else if (d3a.equals("b")) { //Deposit branch
									int $ = custList.get(n).get$();
									System.out.println("How much would you like to Deposit?"); 
									int plus$ = Integer.parseInt(s.nextLine()); 
									int newF = $ + plus$;
									custList.get(n).setFunds(newF);
									System.out.println("New Balance: $"+custList.get(n).get$());
									writefile(custList);
									try {
										badi.updateFunds(newF, pwd);
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									sys();
									sys();
								} else if (d3a.equals("c")){ //Transfer branch
									System.out.println("Enter Account Password that you will be transferring money to: ");
									String h = s.nextLine();
									for (int t = 0; t < custList.size(); t++ ) {
										if (custList.get(t).getPW().equals(h) && n!= t ) {
											System.out.println("Transfer has begun\n" + "How much money would you like to Transfer?");
											int m$ = Integer.parseInt(s.nextLine());
											int newF = custList.get(n).get$() - m$;
											custList.get(n).setFunds(newF);
											int nf = custList.get(t).get$() + m$;
											custList.get(t).setFunds(nf);
											try {
												badi.updateFunds(newF, pwd);
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
											try {
												badi.updateFunds(nf, h);
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											System.out.println("New Balance for 1st Account: $"+custList.get(n).get$() + "\n"
															+	"New Balance for 2nd Account: $"+custList.get(t).get$());
												
										}
									}
									writefile(custList);
									sys();
								}
							}
						}
						
					} else if(d2.equals("c")) { //Cancel Accounts branch
						System.out.println("List of Customer Accounts:");
						//prints out all customer accounts
						for(int i=0; i<custList.size(); i++) {
							System.out.println(i+1 + ": " + custList.get(i));
						}
						System.out.println("Which account would you like to delete? (Enter the number of the account you want to delete from the list above)");
						int n = Integer.parseInt(s.nextLine());
						//uses bank admin integer input to delete account with index n-1
	 					Bankadmin ba = new Bankadmin();
	 					//ArrayList<Customer> newDeleted = new ArrayList<Customer>();
	 					//account deleted
	 					ba.cancelAccount(custList, n);
	 					//rewrite customer list
	 					writefile(custList);
	 					System.out.println("Insert Password of Bank Account to confirm deletion.....");
	 					String pwd = s.nextLine();
	 					try {
							badi.deleteAccount(pwd);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					} else if(d2.equals("e")) { // Approve/Deny joint accounts branch
						System.out.println("List of Joint Accounts to be Approved:");
						for(int i=0; i<JappList.size(); i++) {
							System.out.println(i+1 + ": " + JappList.get(i));
						}
						System.out.print('\n');
		 				System.out.println("Would you like to:"+ "\n" + "a) Approve Joint Accounts" + "\n" +
		 						"b) Deny Joint Accounts");
		 				String d2b = s.nextLine();
		 				if (d2b.equals("a")) { // approve account branch
		 					System.out.println("Enter the number of the account you want to approve from the list above. Enter 0 if you don't want to approve any accounts.");
		 					int n = Integer.parseInt(s.nextLine());
		 					Bankadmin ba = new Bankadmin();
		 					ArrayList<JointApp> newApproved = new ArrayList<JointApp>();
		 					newApproved = ba.approveJointApplication(JappList, n);
		 					writeJA(JappList);
		 					Customer newUser = new Customer(newApproved.get(0).getfl1(), newApproved.get(0).getfl2(), newApproved.get(0).getUname(), newApproved.get(0).getPW(), newApproved.get(0).getFunds());
		 					custList.add(newUser);
		 					writefile(custList);
		 					
		 				} else if(d2b.equals("b")){ //deny account branch
		 					System.out.println("Enter the number of the account you want to deny from the list above. Enter 0 if you don't want to deny any accounts.");
		 					int n = Integer.parseInt(s.nextLine());
		 					Bankadmin ba = new Bankadmin();
		 					//ArrayList<JointApp> newDenied = new ArrayList<JointApp>();
		 					ba.denyJointApplication(JappList, n);
		 					writeJA(JappList);
		 				} 
					} else if(d2.equals("d")) {
						//asks for first name, last name, username, and password info
						System.out.println("What is the customer's first name?");
						String fname = s.nextLine();
						System.out.println("What is the customer's last name?");
						String lname = s.nextLine();
						System.out.println("Create New Customer Username:");
						String uname = s.nextLine();
						System.out.println("Create Customer Password:");
						//funds is set automatically to $10 because our policy required an amount greater than 0 to open an account
						int funds = 10;
						String pw = s.nextLine();
						//create new application object and then add to apply list
						custList.add(new Customer (fname,lname,uname,pw,funds)); 
						//saves application to appplication.txt file
						writefile(custList);
						try {
							badi.insertAccount(fname, lname, uname, pw, funds);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//Let the user know their application is waiting for approval
						System.out.println("A new customer has been registered!");
						sys(); //recall the system
					} 
					sys();
				}	
			} else { //protects bank admin account from users without knowledge of password
				System.out.println("You are not Big Daddy!");
			}
			sys();
		}
		s.close();
	}

	//password validator
	public String findCusty(String checkPwd) {
		//for(Customer customer : custList) {
		for(int i=0; i<custList.size(); i++) {
			Customer customer = custList.get(i);
			if (customer.getPW().equals(checkPwd)){
				return customer.getPW();
			}
		}
		return null;
	}
	
//Serialization Methods	
	
	//serialize customer list
	public void writefile(ArrayList<Customer> custList) {
		try {
			ArrayList<Customer> custys = new ArrayList<Customer>();
			custys = custList;
			FileOutputStream fos = new FileOutputStream("CustomerData.txt");
        	ObjectOutputStream oos = new ObjectOutputStream(fos);
        	oos.writeObject(custys);
        	System.out.println();
        	oos.close();
        	fos.close();
    	} 
    	catch (IOException ioe) {
        	ioe.printStackTrace();
    	}
	}
	
	//serialize normal application list
	public void writeApps(ArrayList<Application> appList) {
		try {
			ArrayList<Application> apply = new ArrayList<Application>();
			apply = appList;
			FileOutputStream fos = new FileOutputStream("Applications.txt");
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	    	oos.writeObject(apply);
	    	System.out.println();
	    	oos.close();
	    	fos.close();
		} 
		catch (IOException ioe) {
	    	ioe.printStackTrace();
		}
	}
	
	//serialize joint application list
	public void writeJA(ArrayList<JointApp> JappList) {
		try {
			ArrayList<JointApp> Japps = new ArrayList<JointApp>();
			Japps = JappList;
			FileOutputStream fos = new FileOutputStream("Joint.txt");
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	    	oos.writeObject(Japps);
	    	System.out.println();
	    	oos.close();
	    	fos.close();
		} 
		catch (IOException ioe) {
	    	ioe.printStackTrace();
		}
	}
	
	//deserialize customer list
	@SuppressWarnings("unchecked")
	public void readfile() {
		try {	
			ArrayList<Customer> deSerializedCustys;
			FileInputStream fis = new FileInputStream(new File ("CustomerData.txt"));
			ObjectInputStream ois = new ObjectInputStream(fis);
        	deSerializedCustys = ((ArrayList<Customer>)ois.readObject());
			custList = deSerializedCustys;
			ois.close();
			fis.close();
    	} 
    	catch (IOException ioe) {
    		System.out.println("Customer List is empty");
    		//ioe.printStackTrace();
        	//return;
    	} 
		catch(ClassNotFoundException c) {
        	System.out.println("Class not found");
        	c.printStackTrace();
        	//return;
    	}
	}
	
	//deserialize normal application list
	@SuppressWarnings("unchecked")
	public void readApps() {
		try {	
			ArrayList<Application> deSerializedApps;
			FileInputStream fis = new FileInputStream(new File ("Applications.txt"));
			ObjectInputStream ois = new ObjectInputStream(fis);
        	deSerializedApps = ((ArrayList<Application>)ois.readObject());
			appList = deSerializedApps;
			ois.close();
			fis.close();
    	} 
    	catch (IOException ioe) {
    		System.out.println("Application List is empty");
    		//ioe.printStackTrace();
        	//return;
    	} 
		catch(ClassNotFoundException c) {
        	System.out.println("Class not found");
        	c.printStackTrace();
        	//return;
    	}
	}
		
	//deserialize joint application list
	@SuppressWarnings("unchecked")
	public void readJA() {
		ArrayList<JointApp> deSerializedJA = new ArrayList<JointApp>();
		try {	
			FileInputStream fis = new FileInputStream(new File ("Joint.txt"));
			ObjectInputStream ois = new ObjectInputStream(fis);
	        deSerializedJA = ((ArrayList<JointApp>)ois.readObject());
			JappList = deSerializedJA;
			ois.close();
			fis.close();
	    } 
	    catch (IOException ioe) {
	        //ioe.printStackTrace();
	    	System.out.println("Joint Application List is empty");
	    	//return;
	    } 
		catch(ClassNotFoundException c) {
	        	System.out.println("Class not found");
	        	c.printStackTrace();
	        	//return;
	    }
	}
}