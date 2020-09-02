package bankingApp;
import java.util.*;

public class Employee {
	public ArrayList<Application> denyApplication(ArrayList<Application> array, int n) {
        if(n==0) {
            System.out.println("No more applications to deny. For security purposes, please log in again to continue.");
        } else if(n<0 || n>array.size()) {
            System.out.println("Invalid input. For security purposes, please log in again to continue.");
        } else {
            array.remove(n-1);
            System.out.println("Application ready to be denied......");
        }
        System.out.println(array);
        return array;
    }
	
	public ArrayList<Application> approveApplication(ArrayList<Application> array, int n) {
		ArrayList<Application> approved = new ArrayList<Application>();
		if(n==0) {
			System.out.println("No more applications to deny. For security purposes, please log in again to continue.");
		} else if(n<0 || n>array.size()) {
			System.out.println("Invalid input. For security purposes, please log in again to continue.");
		} else {
			approved.add(array.get(n-1));
			System.out.println("Application ready to be approved......");
			array.remove(n-1);
		}
		return approved;
	}
	
	public ArrayList<JointApp> denyJointApplication(ArrayList<JointApp> array, int n) {
        if(n==0) {
            System.out.println("No more applications to deny. For security purposes, please log in again to continue.");
        } else if(n<0 || n>array.size()) {
            System.out.println("Invalid input. For security purposes, please log in again to continue.");
        } else if(n>array.size()) {
            
        } else {
        	array.remove(n-1);
            System.out.println("Application denied. For security purposes, please log in again to continue.");
        }
        System.out.println(array);
        return array;
    }
	
	public ArrayList<JointApp> approveJointApplication(ArrayList<JointApp> array, int n) {
		ArrayList<JointApp> approved = new ArrayList<JointApp>();
		if(n==0) {
			System.out.println("No more applications to deny. For security purposes, please log in again to continue.");
		} else if(n<0 || n>array.size()) {
			System.out.println("Invalid input. For security purposes, please log in again to continue.");
		} else {
			approved.add(array.get(n-1));
			System.out.println("Application approved. For security purposes, please log in again to continue.");
		}
		return approved;
	}
}