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
	private double transactionChargeRate;
	private double securitiesTransferTaxRate;
	
	public UserTransaction() {
		
	}
	
	public UserTransaction(int accountNumber, String shareName, String transactionType, double sharePrice, int shareQuantity, double transactionChargeRate, double securitiesTransferTaxRate) {
		this.accountNumber = accountNumber;
		this.transactionID = getUniqueNumber();
		this.transactionDate = LocalDate.now();
		this.transactionTime = LocalTime.now();
		this.shareName = shareName;
		this.transactionType = transactionType;
		this.sharePrice = sharePrice;
		this.shareQuantity = shareQuantity;
		this.transactionChargeRate = transactionChargeRate;
		this.securitiesTransferTaxRate = securitiesTransferTaxRate;
	}
	
	public UserTransaction(int accountNumber, int transactionID, String transactionDate, String transactionTime, String shareName, String transactionType, Double sharePrice, int shareQuantity, double transactionChargeRate, double securitiesTransferTaxRate) {
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
		this.transactionChargeRate = transactionChargeRate;
		this.securitiesTransferTaxRate = securitiesTransferTaxRate;
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
	
	public double getTransactionChargeRate() {
		return this.transactionChargeRate;
	}
	
	public double securitiesTransferTaxRate() {
		return this.securitiesTransferTaxRate;
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
	
	public void setTransactionChargeRate(double transactionChargeRate) {
		this.transactionChargeRate = transactionChargeRate;
	}
	
	public void setSecuritiesTransferTaxRate (double securitiesTransferTaxRate) {
		this.securitiesTransferTaxRate = securitiesTransferTaxRate;
	}
	
	public String toString() {
		return this.accountNumber+","+this.transactionID+","+this.getTransactionDate()+","+this.getTransactionTime()+","+this.shareName+","+this.transactionType+","+this.sharePrice+","+this.shareQuantity+","+this.transactionChargeRate+","+this.securitiesTransferTaxRate;
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
