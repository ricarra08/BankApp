package Testing;


import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertArrayEquals;

import bankingApp.*;

public class Tester {
	//int n=1;
	ArrayList<Application> array = new ArrayList<Application>();
	ArrayList<JointApp> jointArray = new ArrayList<JointApp>();
	Application app = new Application("Vinish", "Kumar", "vinish", "password", 10);
	JointApp ja = new JointApp("Vinish Kumar", "Ricardo Carrasco", "vicar", "pw", 10);
	Employee e = new Employee();
	Bankadmin ba = new Bankadmin();
	
	@Test
	public void testEmployeeDenyApplication() {
		array.add(app);
		int n = 1;
		e.denyApplication(array, n);
		ArrayList<Application> expected = new ArrayList<Application>();
		assertArrayEquals(expected.toArray(), array.toArray());
	}
	
	@Test
	public void testEmployeeApproveApplication() {
		array.add(app);
		int n = 1;
		e.approveApplication(array, n);
		ArrayList<Application> expected = new ArrayList<Application>();
		assertArrayEquals(expected.toArray(), array.toArray());
	}
	
	@Test
	public void testEmployeeDenyJointApplication() {
		jointArray.add(ja);
		int n = 1;
		e.denyJointApplication(jointArray, n);
		ArrayList<Application> expected = new ArrayList<Application>();
		assertArrayEquals(expected.toArray(), array.toArray());
	}
	
	@Test
	public void testEmployeeApproveJointApplication() {
		jointArray.add(ja);
		int n = 1;
		e.approveJointApplication(jointArray, n);
		ArrayList<Application> expected = new ArrayList<Application>();
		assertArrayEquals(expected.toArray(), array.toArray());
	}
	
	@Test
	public void testAdminDenyApplication() {
		array.add(app);
		int n = 1;
		ba.denyApplication(array, n);
		ArrayList<Application> expected = new ArrayList<Application>();
		assertArrayEquals(expected.toArray(), array.toArray());
	}
	
	@Test
	public void testAdminApproveApplication() {
		array.add(app);
		int n = 1;
		ba.approveApplication(array, n);
		ArrayList<Application> expected = new ArrayList<Application>();
		assertArrayEquals(expected.toArray(), array.toArray());
	}
	
	@Test
	public void testAdminDenyJointApplication() {
		jointArray.add(ja);
		int n = 1;
		ba.denyJointApplication(jointArray, n);
		ArrayList<Application> expected = new ArrayList<Application>();
		assertArrayEquals(expected.toArray(), array.toArray());
	}
	@Test
	public void testAdminApproveJointApplication() {
		jointArray.add(ja);
		int n = 1;
		ba.approveJointApplication(jointArray, n);
		ArrayList<Application> expected = new ArrayList<Application>();
		assertArrayEquals(expected.toArray(), array.toArray());
	}
}
