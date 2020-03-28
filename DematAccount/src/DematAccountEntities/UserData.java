/**
 * 
 */
package DematAccountEntities;

import java.util.UUID;

/**
 * @author kddeepan
 *
 */
public class UserData{
	private String userName;
	private int accountNumber;
	private double accountBalance;
	private String accountPassword;
	
	public UserData () {
		
	}
	
	public UserData (String userName, String accountPassword) {
		this.userName = userName;
		this.accountNumber = getUniqueNumber();
		this.accountBalance = 10000.00;
		this.accountPassword = accountPassword;
	}
	
	public UserData (String userName, int accountNumber, double accountBalance, String accountPassword) {
		this.userName = userName;
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
		this.accountPassword = accountPassword;
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
	
	public String getAccountPassword() {
		return this.accountPassword;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	
	public String toString() {
		return this.userName+","+this.accountNumber+","+this.accountBalance+","+this.accountPassword;
	}
	
	public static int getUniqueNumber() {
		UUID uniqueID = UUID.randomUUID();
        String stringID=""+uniqueID;        
        int uniqueInteger = stringID.hashCode();
        stringID = ""+uniqueInteger;
        stringID = stringID.replaceAll("-", "");
        uniqueInteger = Integer.parseInt(stringID);
        return uniqueInteger;
	}	
}
