/**
 * 
 */
package Entities;

/**
 * @author kddeepan
 *
 */
public class UserData {
	
	private String userName;
	private int accountNumber;
	private double accountBalance;
	
	public UserData () {
		
	}
	
	public UserData (String userName, int accountNumber, double accountBalance) {
		this.userName = userName;
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public int getAccountNumber() {
		return this.accountNumber;
	}
	
	public double getAccountBalance() {

		return this.accountBalance;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
		return;
	}
	
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
		return;
	}
	
	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
		return;
	}
	
	public String toString() {
		return this.userName+","+this.accountNumber+","+this.accountBalance;
	}

	public static void main (String [] args) {
		UserData userDeepankar = new UserData("Deepankar", 719601, 10000);
		UserData userSandeep = new UserData("Sandeep", 8147, 20000);
		
		System.out.println(userDeepankar.getAccountNumber()+","+userDeepankar.getUserName()+","+userDeepankar.getAccountBalance());
		System.out.println(userSandeep.getAccountNumber()+","+userSandeep.getUserName()+","+userSandeep.getAccountBalance());
		
	}
	
}
