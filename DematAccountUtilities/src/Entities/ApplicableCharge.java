package Entities;

public class ApplicableCharge {
	
	private double transactionCharge;
	private double securitiesTransferTax;
	
	public ApplicableCharge() {
		
	}
	
	public ApplicableCharge(double transactionCharge, double securitiesTransferTax) {
		this.transactionCharge = transactionCharge;
		this.securitiesTransferTax = securitiesTransferTax;
	}
	
	public double getTransactionCharge() {
		return this.transactionCharge;
	}

	public double getSecuritiesTransferTax() {
		return this.securitiesTransferTax;
	}

	public void setTransactionCharge(double transactionCharge) {
		this.transactionCharge = transactionCharge;
	}
	
	public void setSecuritiesTransferTax(double securitiesTransferTax) {
		this.securitiesTransferTax = securitiesTransferTax;
	}

	public String toString() {
		return this.transactionCharge+","+this.securitiesTransferTax;
	}

	public static void main (String [] args) {
		System.out.println("Testing");
	}
}
