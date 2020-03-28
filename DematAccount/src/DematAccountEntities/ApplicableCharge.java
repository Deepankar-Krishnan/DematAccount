package DematAccountEntities;

import java.util.ArrayList;
import java.util.List;

import Database.DatabaseMaintainer;

public class ApplicableCharge {
	
	private static double transactionChargeRate;
	private static double securitiesTransferTaxRate;
	
	private ApplicableCharge() {
		
	}
	
	static {
		String [] applicableChargeDetails = (DatabaseMaintainer.readEntities("ApplicableCharge")).get(0).split(",");
		ApplicableCharge.transactionChargeRate = Double.parseDouble(applicableChargeDetails[0]);
		ApplicableCharge.securitiesTransferTaxRate = Double.parseDouble(applicableChargeDetails[1]);
	}
	
	public static double getTransactionChargeRate() {		
		return ApplicableCharge.transactionChargeRate;
	}

	public static double getSecuritiesTransferTaxRate() {		
		return ApplicableCharge.securitiesTransferTaxRate;
	}

	public static void setTransactionChargeRate(double transactionChargeRate) {
		ApplicableCharge.transactionChargeRate = transactionChargeRate;
		List <String> charges = new ArrayList<String> ();
		charges.add(ApplicableCharge.transactionChargeRate+","+ApplicableCharge.securitiesTransferTaxRate);
		DatabaseMaintainer.writeEntities(charges,"ApplicableCharge");
	}
	
	public static void setSecuritiesTransferTaxRate(double securitiesTransferTaxRate) {
		ApplicableCharge.securitiesTransferTaxRate = securitiesTransferTaxRate;
		List <String> charges = new ArrayList<String> ();
		charges.add(ApplicableCharge.transactionChargeRate+","+ApplicableCharge.securitiesTransferTaxRate);
		DatabaseMaintainer.writeEntities(charges,"ApplicableCharge");
	}
}
