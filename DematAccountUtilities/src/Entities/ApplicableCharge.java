package Entities;

public class ApplicableCharge {
	
	private double transactionChargeRate;
	private double securitiesTransferTaxRate;
	
	public ApplicableCharge() {
		
	}
	
	public ApplicableCharge(double transactionCharge, double securitiesTransferTax) {
		this.transactionChargeRate = transactionCharge;
		this.securitiesTransferTaxRate = securitiesTransferTax;
	}
	
	public double getTransactionCharge() {
		return this.transactionChargeRate;
	}

	public double getSecuritiesTransferTax() {
		return this.securitiesTransferTaxRate;
	}

	public void setTransactionCharge(double transactionCharge) {
		this.transactionChargeRate = transactionCharge;
	}
	
	public void setSecuritiesTransferTax(double securitiesTransferTax) {
		this.securitiesTransferTaxRate = securitiesTransferTax;
	}

	public String toString() {
		return this.transactionChargeRate+","+this.securitiesTransferTaxRate;
	}

	public static void main (String [] args) {
		System.out.println("Testing");
	}
}
