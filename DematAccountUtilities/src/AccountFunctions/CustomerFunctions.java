package AccountFunctions;

import java.util.Map;
import java.util.Scanner;

import DataStructures.HashInHash;
import DataStructures.MultiValueHashMap;
import DataStructures.ShareMarketHashMap;
import DataStructures.UserDataHashMap;
import Entities.ApplicableCharge;
import Entities.ShareMarket;
import Entities.UserData;
import Entities.UserShare;
import Entities.UserTransaction;

public class CustomerFunctions {
	
	public static void createUser() {
		System.out.println("Enter the desired user name:");
		Scanner scanInput = new Scanner(System.in);
		String newUserName = scanInput.nextLine();
		System.out.println("Enter the desired account password (Do not include spaces):");
		String newUserPassword = scanInput.nextLine();
		UserData newUser = new UserData(newUserName,newUserPassword);
		System.out.println("Your account has been created successfully. Please note the below details for future login purpose:");
		System.out.println("Your account number:"+newUser.getAccountNumber());
		System.out.println("Your account password:"+newUser.getAccountPassword());
		UserDataHashMap.addUser(newUser);
		scanInput.close();
	}
	
	public static void createShare() {
		System.out.println("Enter the share name:");
		Scanner scanInput = new Scanner(System.in);
		String shareName = scanInput.nextLine();
		System.out.println("Enter the share price:");
		double sharePrice = scanInput.nextDouble();
		System.out.println("Enter the share quantity:");
		int sharequantity = scanInput.nextInt();
		ShareMarket newShare = new ShareMarket (shareName, sharePrice, sharequantity);
		ShareMarketHashMap.addShare(newShare);
		scanInput.close();
	}
	//Can be checked with the account number - 1813312812
	public static void buyShares(int accountNumber) {
		Scanner userInput = new Scanner(System.in);
		//Need to work on the formatting the display text
		System.out.println("Share Name\tSharePrice\tAvailableQuantity");
		Map<String, ShareMarket> availableShares = ShareMarketHashMap.getShareMarket();
		String shareName;
		double sharePrice;
		int availableQuantity;
		for(String company : availableShares.keySet()) {
			shareName = availableShares.get(company).getShareName();
			sharePrice = availableShares.get(company).getSharePrice();
			availableQuantity = availableShares.get(company).getShareQuantity();
			System.out.println(shareName+"\t"+sharePrice+"\t"+availableQuantity);
		}
		//The above part can be treated as one segment
		//Have to come up with better checks for below
		System.out.println("Enter the name of the share as mentioned above (Names are case sensitive):");
		String buyShare = userInput.next();
		if (!availableShares.containsKey(buyShare)) {
			System.out.println("Share name entered by you is not available. Please ensure that spelling is correct");
			return;
		}
		System.out.println("Please enter the quantity of the shares you want to purchase (Value should be less than or equal to available shares):");
		int buyQuantity;
		if(!userInput.hasNextInt()) {
			System.out.println("Quantity entered in invalid, it should be a postive integer.");
			return;
		}
		buyQuantity = userInput.nextInt();
		int currentAvailableShares = availableShares.get(buyShare).getShareQuantity();
		if (buyQuantity>currentAvailableShares||buyQuantity<0) {
			System.out.println("Please enter valid buy quantity as per the available shares (Value should not be negative)");
			return;
		}		
		double totalSharesPrice = (availableShares.get(buyShare).getSharePrice())*buyQuantity;
		double transactionCost = (totalSharesPrice*ApplicableCharge.getTransactionChargeRate())/100<100?100:(totalSharesPrice*ApplicableCharge.getTransactionChargeRate())/100;
		double securitiesTransferCost = (totalSharesPrice*ApplicableCharge.getSecuritiesTransferTaxRate())/100;
		double totalPurchaseCost = totalSharesPrice+transactionCost+securitiesTransferCost;
		double userCurrentAccountBalance = UserDataHashMap.getAccountDetails(accountNumber).getAccountBalance();
		if (totalPurchaseCost>userCurrentAccountBalance){
			System.out.println("Cannot proceed with the transaction as your account has insufficient balance.");
			System.out.println("Amount required for the transaction: "+totalPurchaseCost);
			System.out.println("Your current account balance: "+userCurrentAccountBalance);
			System.out.println("Deficit: "+(totalPurchaseCost-userCurrentAccountBalance));
			System.out.println("You may try again after depositing the above defict.");
			return;
		}
		UserDataHashMap.getAccountDetails(accountNumber).setAccountBalance(userCurrentAccountBalance-totalPurchaseCost);
		availableShares.get(buyShare).setShareQuantity(currentAvailableShares-buyQuantity);
		UserDataHashMap.editUser();
		ShareMarketHashMap.editShare();
		HashInHash.updateUserShareMap(new UserShare(accountNumber,buyShare,availableShares.get(buyShare).getSharePrice(),buyQuantity),"Bought");
		MultiValueHashMap.updateUserTransactionMap(new UserTransaction(accountNumber,buyShare,"Bought",availableShares.get(buyShare).getSharePrice(),buyQuantity,ApplicableCharge.getTransactionChargeRate(),ApplicableCharge.getSecuritiesTransferTaxRate()));						
		System.out.println("Transaction successful. Your current account balance after deducting transaction charges: "+(userCurrentAccountBalance-totalPurchaseCost));
	}
	//Can be checked with the account number - 1813312812
	public static void sellShares(int accountNumber) {
		Map <String, UserShare> currentUserShares = HashInHash.getUserShareMap(accountNumber);
		if(currentUserShares.isEmpty()) {
			System.out.println("Curently you do not hold any shares to sell.");
			return;
		}
		Scanner userInput = new Scanner(System.in);
		String shareName;
		double sharePrice;
		int availableQuantity;
		//Need to work on the formatting the display text
		System.out.println("Share Name\tSharePrice\tAvailableQuantity");
		for(String userShare:currentUserShares.keySet()){
			shareName = currentUserShares.get(userShare).getShareName();
			sharePrice = currentUserShares.get(userShare).getAverageSharePrice();
			availableQuantity = currentUserShares.get(userShare).getShareQuantity();
			System.out.println(shareName+"\t"+sharePrice+"\t"+availableQuantity);
		}
		//The above part can be treated as one segment
		//Have to come up with better checks for below
		System.out.println("Enter the name of the share as mentioned above (Names are case sensitive):");
		String sellShare = userInput.next();
		if(!currentUserShares.containsKey(sellShare)) {
			System.out.println("Currently you do not hold any shares for this company.");
			return;
		}
		Map<String, ShareMarket> availableShares = ShareMarketHashMap.getShareMarket();
		double currentMarketPrice = availableShares.get(sellShare).getSharePrice();
		System.out.println("Enter the quantity of shares you want to sell at current market price: "+currentMarketPrice);
		int sellQuantity;
		if(!userInput.hasNextInt()) {
			System.out.println("Quantity entered in invalid, it should be a postive integer.");
			return;
		}		
		sellQuantity = userInput.nextInt();
		int currentUserHoldings = currentUserShares.get(sellShare).getShareQuantity();
		if(sellQuantity>currentUserHoldings || sellQuantity<0) {
			System.out.println("Please enter valid buy quantity as per the available shares (Value should not be negative)");
			return;
		}
		int currentMarketQuantity = availableShares.get(sellShare).getShareQuantity();
		double totalSharesPrice = (availableShares.get(sellShare).getSharePrice())*sellQuantity;
		double transactionCost = (totalSharesPrice*ApplicableCharge.getTransactionChargeRate())/100<100?100:(totalSharesPrice*ApplicableCharge.getTransactionChargeRate())/100;
		double securitiesTransferCost = (totalSharesPrice*ApplicableCharge.getSecuritiesTransferTaxRate())/100;
		double totalSaleCost = transactionCost+securitiesTransferCost;
		double userCurrentAccountBalance = UserDataHashMap.getAccountDetails(accountNumber).getAccountBalance();
		UserDataHashMap.getAccountDetails(accountNumber).setAccountBalance(userCurrentAccountBalance+totalSharesPrice-totalSaleCost);
		availableShares.get(sellShare).setShareQuantity(currentMarketQuantity+sellQuantity);
		UserDataHashMap.editUser();
		ShareMarketHashMap.editShare();
		HashInHash.updateUserShareMap(new UserShare(accountNumber,sellShare,availableShares.get(sellShare).getSharePrice(),sellQuantity),"Sold");
		MultiValueHashMap.updateUserTransactionMap(new UserTransaction(accountNumber,sellShare,"Sold",availableShares.get(sellShare).getSharePrice(),sellQuantity,ApplicableCharge.getTransactionChargeRate(),ApplicableCharge.getSecuritiesTransferTaxRate()));						
		System.out.println("Transaction successful. Your current account balance after deducting transaction charges: "+(userCurrentAccountBalance+totalSharesPrice-totalSaleCost));
	}
	
	public static void main(String[] args) {
		/*ApplicableCharge.setTransactionCharge(2.5);
		CustomerFunctions.buyShares();
		ApplicableCharge.setTransactionCharge(1.5);
		CustomerFunctions.buyShares();
		ApplicableCharge.setTransactionCharge(0);
		CustomerFunctions.buyShares();*/
		/*ApplicableCharge.setTransactionChargeRate(0.5);
		ApplicableCharge.setSecuritiesTransferTaxRate(0.1);
		CustomerFunctions.buyShares();*/
		//Can be checked with the account number - 1813312812
		CustomerFunctions.buyShares(1813312812);
		CustomerFunctions.sellShares(1813312812);
	}
}