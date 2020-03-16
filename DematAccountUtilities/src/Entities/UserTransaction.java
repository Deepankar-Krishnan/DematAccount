/**
 * 
 */
package Entities;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

/**
 * @author kddeepan
 *
 */
public class UserTransaction {

	private int accountNumber;
	private LocalDateTime transactionDateTime;
	private String transactionType;
	private double sharePrice;
	private int shareQuantity;
	
	public UserTransaction() {
		
	}
	
	public UserTransaction(int accountNumber, String transactionType, double sharePrice, int shareQuantity) {
		this.accountNumber = accountNumber;
		this.transactionDateTime = LocalDateTime.now();
		this.transactionType = transactionType;
		this.sharePrice = sharePrice;
		this.shareQuantity = shareQuantity;
	}
	
	public int getAccountNumber() {
		return this.accountNumber;
	}
	
	public String getTransactionDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return formatter.format(this.transactionDateTime);
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
	
	public void setTransactionDateTime(String transactionDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");		
		this.transactionDateTime = LocalDateTime.parse(transactionDateTime, formatter);
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
		return this.accountNumber+","+this.getTransactionDateTime()+","+this.transactionType+","+this.sharePrice+","+this.shareQuantity;
	}
	
	public static void main(String[] args) {
		System.out.println("Testing the attributes");
		UserTransaction deepankarTransaction = new UserTransaction (123456, "Buy", 190.68, 150);
		System.out.println(deepankarTransaction.toString());
		System.out.println(deepankarTransaction.transactionDateTime);

	}

}
