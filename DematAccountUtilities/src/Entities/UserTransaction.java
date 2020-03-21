/**
 * 
 */
package Entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author kddeepan
 *
 */
public class UserTransaction {

	private int accountNumber;
	private int transactionID;
	private LocalDate transactionDate;
	private LocalTime transactionTime;
	private String shareName;
	private String transactionType;
	private double sharePrice;
	private int shareQuantity;
	
	public UserTransaction() {
		
	}
	
	public UserTransaction(int accountNumber, String shareName, String transactionType, double sharePrice, int shareQuantity) {
		this.accountNumber = accountNumber;
		this.transactionID = getUniqueNumber();
		this.transactionDate = LocalDate.now();
		this.transactionTime = LocalTime.now();
		this.shareName = shareName;
		this.transactionType = transactionType;
		this.sharePrice = sharePrice;
		this.shareQuantity = shareQuantity;
	}
	
	public UserTransaction(int accountNumber, int transactionID, String transactionDate, String transactionTime, String shareName, String transactionType, Double sharePrice, int shareQuantity) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		this.accountNumber = accountNumber;
		this.transactionID = transactionID;
		this.transactionDate = LocalDate.parse(transactionDate, dateFormatter);
		this.transactionTime = LocalTime.parse(transactionTime, timeFormatter);
		this.shareName = shareName;
		this.transactionType = transactionType;
		this.sharePrice = sharePrice;
		this.shareQuantity = shareQuantity;
	}
	
	public int getAccountNumber() {
		return this.accountNumber;
	}
	
	public int getTransactionID() {
		return this.transactionID;
	}
	
	public String getTransactionDate() {
		return this.transactionDate.toString();
	}
	
	public String getTransactionTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		return formatter.format(this.transactionTime);
	}
	
	public String getshareName() {
		return this.shareName;
	}
	
	public String getTransactionType() {
		return this.transactionType;
	}
	
	public double getSharePrice() {
		return this.sharePrice;
	}
	
	public int getShareQuantity() {
		return this.shareQuantity;
	}
	
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public void setTransactionDate(String transactionDate) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		this.transactionDate = LocalDate.parse(transactionDate, dateFormatter);
	}
	
	public void setTransactionTime(String transactionTime) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");		
		this.transactionTime = LocalTime.parse(transactionTime, timeFormatter);
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}
	
	public void setShareQuantity(int shareQuantity) {
		this.shareQuantity = shareQuantity;
	}
	
	public String toString() {
		return this.accountNumber+","+this.transactionID+","+this.getTransactionDate()+","+this.getTransactionTime()+","+this.shareName+","+this.transactionType+","+this.sharePrice+","+this.shareQuantity;
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
	
	public static void main(String[] args) {
		/*System.out.println("Testing the attributes");
		UserTransaction deepankarTransaction = new UserTransaction (123456, "Adani", "Buy", 190.68, 150);
		System.out.println(deepankarTransaction.toString());
		System.out.println(deepankarTransaction.transactionDateTime);*/
		System.out.println(LocalDate.now());
		System.out.println(LocalDate.now().toString());
		System.out.println(LocalTime.now());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		System.out.println(formatter.format(LocalTime.now()));
		System.out.println(LocalTime.now().toString());
	}

}
