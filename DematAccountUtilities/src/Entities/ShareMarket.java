/**
 * 
 */
package Entities;

/**
 * @author kddeepan
 *
 */
public class ShareMarket {
	
	private String shareName;
	private double sharePrice;
	private int shareQuantity;
	
	public ShareMarket() {
		
	}
	
	public ShareMarket(String shareName, double sharePrice, int shareQuantity) {
		this.shareName = shareName;
		this.sharePrice = sharePrice;
		this.shareQuantity = shareQuantity;
	}

	public String getShareName() {
		return this.shareName;
	}
	
	public double getSharePrice() {
		return this.sharePrice;
	}
	
	public int getShareQuantity() {
		return this.shareQuantity;
	}
	
	public void setShareName(String shareName) {
		this.shareName = shareName;
	}
	
	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}
	
	public void setShareQuantity(int shareQuantity) {
		this.shareQuantity = shareQuantity;
	}
	
	public String toString() {
		return this.shareName+","+this.sharePrice+","+this.shareQuantity;
	}	
}
