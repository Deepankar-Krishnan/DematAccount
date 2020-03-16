package Entities;

public class UserShare {
	
	private int accountNumber;
	private String shareName;
	private double averageSharePrice;
	private int shareQuantity;
	
	public UserShare(){
		
	}
	
	public UserShare(int accountNumber, String shareName, double averageSharePrice, int shareQuantity) {
		this.accountNumber = accountNumber;
		this.shareName = shareName;
		this.averageSharePrice = averageSharePrice;
		this.shareQuantity = shareQuantity;
	}

	public int getAccountNumber() {
		return this.accountNumber;
	}
	
	public String getShareName() {
		return this.shareName;
	}
	
	public double getAverageSharePrice() {
		return this.averageSharePrice;
	}
	
	public int getShareQuantity() {
		return this.shareQuantity;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
		return;
	}
	
	public void setShareName(String shareName) {
		this.shareName = shareName;
		return;
	}

	public void setAveragePrice(double averageSharePrice) {
		this.averageSharePrice = averageSharePrice;
		return;
	}

	public void setShareQuantity(int shareQuantity) {
		this.shareQuantity = shareQuantity;
		return;
	}

	public String toString() {
		return this.accountNumber+","+this.shareName+","+this.averageSharePrice+","+this.shareQuantity;
	}
	
	public static void main (String [] args) {
		UserShare deepankar = new UserShare (719601, "Adani", 256.68, 100);
		System.out.println(deepankar.toString());
	}
}
