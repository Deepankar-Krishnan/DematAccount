/**
 * 
 */
package UserFunctions;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import DataStructures.HashInHash;
import DataStructures.MultiValueHashMap;
import DataStructures.ShareMarketHashMap;
import DataStructures.UserDataHashMap;
import DematAccountEntities.ApplicableCharge;
import DematAccountEntities.ShareMarket;
import DematAccountEntities.UserData;
import DematAccountEntities.UserShare;
import DematAccountEntities.UserTransaction;
import UserInterface.UserMenu;

/**
 * @author kddeepan
 *
 */
public class CustomerFunctions {
	// Method to deposit money in user's INR account.
	public static void depositMoney(int accountNumber) {             // Account number to be fed from main DematClass where this function will be called.            
		Scanner userInput = new Scanner(System.in);
		System.out.print("Enter the amount you want to deposit : "); // Asking for the deposit amount.
		if(!userInput.hasNextInt()) {
			System.out.println("Invalid Amount entered :( , you will be returned to main menu"+'\n');
			return;
		}		
		// Initializing scanner class object to take input.
		double depositMoneyValue = userInput.nextDouble();// Storing the input 
		if(depositMoneyValue<0){
			System.out.println("Negative amount deposit is not accepted, you will be returned to main menu"+'\n');
			return;
			}
		double currentBalance = UserDataHashMap.getAccountDetails(accountNumber).getAccountBalance();
		double updatedBalance = currentBalance+depositMoneyValue;			
		UserDataHashMap.getAccountDetails(accountNumber).setAccountBalance(updatedBalance);
		UserDataHashMap.editUser();				
		System.out.println("Deposit successful. Your updated account balance is : "+(updatedBalance)+"/- INR." +'\n');
	}
				
	// Method to withdraw money from user's INR account.
	public static void withdrawMoney(int accountNumber) {	
		Scanner userInput = new Scanner(System.in);
		System.out.print("Enter the amount you want to withdraw : ");
		if(!userInput.hasNextInt()) {
			System.out.println("Invalid Amount entered :( , you will be returned to main menu"+'\n');
			return;
		}	
		double withdrawMoneyValue = userInput.nextDouble();
		double currentBalance = UserDataHashMap.getAccountDetails(accountNumber).getAccountBalance();
		if (withdrawMoneyValue>currentBalance){
			System.out.println("Cannot proceed with the withdrawal as your account has insufficient balance.");
			System.out.println("Your current account balance is : "+currentBalance+" /- INR.");
			System.out.println("And deficit is : "+(withdrawMoneyValue-currentBalance+" /- INR."));
			System.out.println("Please try again with a valid withdrawal amount!, you will be returned to main menu"+'\n');
			return;
		}
		else if(withdrawMoneyValue <0){
			System.out.println("Negative amount withdrawls is not accepted, you will be returned to main menu"+'\n');
			return;
		}
		else {
			double updatedBalance = currentBalance - withdrawMoneyValue;			
			UserDataHashMap.getAccountDetails(accountNumber).setAccountBalance(updatedBalance);
			UserDataHashMap.editUser();				
			System.out.println("Deposit successful. Your updated account balance is : "+(updatedBalance)+"/- INR." +'\n');
		} 
	}	
	
	//Can be checked with the account number - 1813312812
	public static void buyShares(int accountNumber) {
		Scanner userInput = new Scanner(System.in);
		//Need to work on the formatting the display text
		String column1 = "ShareName";
		String column2 = "SharePrice";
		String column3 = "AvailableQuantity";
		System.out.printf("%-15s %-15s %s %n",column1,column2,column3);
		Map<String, ShareMarket> availableShares = ShareMarketHashMap.getShareMarket();
		String shareName;
		double sharePrice;
		int availableQuantity;
		for(String company : availableShares.keySet()) {
			shareName = availableShares.get(company).getShareName();
			sharePrice = availableShares.get(company).getSharePrice();
			availableQuantity = availableShares.get(company).getShareQuantity();
			System.out.printf("%-15s %-15.2f %d %n",shareName,sharePrice,availableQuantity);
		}
		//The above part can be treated as one segment
		//Have to come up with better checks for below
		System.out.println('\n'+"Enter the name of the share as mentioned above (Names are case sensitive):");
		String buyShare = userInput.next();
		if (!availableShares.containsKey(buyShare)) {
			System.out.println("Share name entered by you is not available. Please ensure that spelling is correct. You will be returned to main menu" +'\n');
			return;
		}
		System.out.println("Please enter the quantity of the shares you want to purchase (Value should be less than or equal to available shares):");
		int buyQuantity;
		if(!userInput.hasNextInt()) {
			System.out.println("Quantity entered in invalid, it should be a postive integer. You will be returned to main menu" +'\n');
			return;
		}
		buyQuantity = userInput.nextInt();
		int currentAvailableShares = availableShares.get(buyShare).getShareQuantity();
		if (buyQuantity>currentAvailableShares||buyQuantity<0) {
			System.out.println("Please enter valid buy quantity as per the available shares (Value should not be negative). You will be returned to main menu" +'\n');
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
			System.out.println("You may try again after depositing the above defict. You will be returned to main menu" +'\n');
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
		String column1 = "ShareName";
		String column2 = "SharePrice";
		String column3 = "AvailableQuantity";
		System.out.printf("%-15s %-15s %s %n",column1,column2,column3);
		String shareName;
		double sharePrice;
		int availableQuantity;
		for(String company : currentUserShares.keySet()) {
			shareName = currentUserShares.get(company).getShareName();
			sharePrice = currentUserShares.get(company).getAverageSharePrice();
			availableQuantity = currentUserShares.get(company).getShareQuantity();
			System.out.printf("%-15s %-15.2f %d %n",shareName,sharePrice,availableQuantity);
		}
		//The above part can be treated as one segment
		//Have to come up with better checks for below
		System.out.println('\n'+"Enter the name of the share as mentioned above (Names are case sensitive):");
		Scanner userInput = new Scanner(System.in);
		String sellShare = userInput.next();
		if(!currentUserShares.containsKey(sellShare)) {
			System.out.println("Currently you do not hold any shares for this company. You will be returned to main menu"+'\n');
			return;
		}
		Map<String, ShareMarket> availableShares = ShareMarketHashMap.getShareMarket();
		double currentMarketPrice = availableShares.get(sellShare).getSharePrice();
		System.out.println("Enter the quantity of shares you want to sell at current market price: "+currentMarketPrice);
		int sellQuantity;
		if(!userInput.hasNextInt()) {
			System.out.println("Quantity entered in invalid, it should be a postive integer. You will be returned to main menu"+'\n');
			return;
		}		
		sellQuantity = userInput.nextInt();
		int currentUserHoldings = currentUserShares.get(sellShare).getShareQuantity();
		if(sellQuantity>currentUserHoldings || sellQuantity<0) {
			System.out.println("Your account does not hold the quantity entered by you to sell. You will be returned to main menu" + '\n');
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
	
	public static void viewTransactionReport(int userAccountNumber) {
		Scanner userInput = new Scanner (System.in);
		System.out.println("View Transaction Report");
		System.out.println("Enter O to view transactions Date wise and 1 to view transactions Share wise");
		int choice9 = userInput.nextInt();
		if (choice9 == 0) {
			System.out.println("Enter the range of dates between you want to view your transaction report in yyyy-MM-dd format");
			LocalDate sDate = null;
			boolean wrongInput = false;
			do {
				try {
					wrongInput = false;
					System.out.println("Enter Start date in yyyy-MM-dd format");
					String startDate = new Scanner(System.in).nextLine();
					sDate = LocalDate.parse(startDate);
				} catch (DateTimeParseException exception) {
					System.out.println("Invalid Date format is provided, Please enter Start date in yyyy-MM-dd format");
					wrongInput = true;
				}
			} while (wrongInput);
		LocalDate eDate = null;
		do {
			try {
				wrongInput = false;
				System.out.println("Enter End date in yyyy-MM-dd format");
				String endDate = new Scanner(System.in).nextLine();
				eDate = LocalDate.parse(endDate);
			} catch (DateTimeParseException exception) {
				System.out.println("Invalid Date format is provided, Please enter End date in yyyy-MM-dd format");
				wrongInput = true;
			}
		} while (wrongInput);
		if (sDate.isAfter(eDate)) {
			System.out.println("Start date entered is after the End Date, Re-enter Start date before the end date by choosing option 6 from Main Menu Options");
			String startDate = new Scanner(System.in).nextLine();
			sDate = LocalDate.parse(startDate);
		} else {
			// UserTransaction list used to get Transaction date wise and display
			List<UserTransaction> userTransactionDetailsList = MultiValueHashMap.getUserTransactionMap(userAccountNumber, sDate, eDate);
			for (int i = 0; i < userTransactionDetailsList.size(); i++) {
				System.out.println("Transaction Date: " + userTransactionDetailsList.get(i).getTransactionDate()
				+ " " + "Share Name: " + userTransactionDetailsList.get(i).getshareName() + " "
				+ "Share Price: " + userTransactionDetailsList.get(i).getSharePrice() + " "
				+ "Transaction Type:  " + userTransactionDetailsList.get(i).getTransactionType() + " "
				+ "Quanity of Shares: " + userTransactionDetailsList.get(i).getShareQuantity() + " "
				+ "Transaction Charge: " + userTransactionDetailsList.get(i).getTransactionChargeRate()
				+ " " + "Securities Transfer Tax: "
				+ userTransactionDetailsList.get(i).securitiesTransferTaxRate());
			}
			}
		} else {
			// List of Strings used to display sharenames and transaction names based on
			// selected share

			List<String> userShareNames = new ArrayList<String>();
			for (UserTransaction userTransaction : MultiValueHashMap.getUserTransactionList(userAccountNumber)) {
				if (!userShareNames.contains(userTransaction.getshareName())) {
					userShareNames.add(userTransaction.getshareName());
				}
			}
			System.out.println("Transacted Shares List by the User");
			System.out.println("-----------------------------------");
			for (int i = 0; i < userShareNames.size(); i++) {
				System.out.println(i + " " + userShareNames.get(i));
			}
			boolean wrongOption = false;
			String selectedShareName = null;
			do {
				try {
					wrongOption = false;
					System.out.println("Enter the option of share to view all transactions");
					int option3 = userInput.nextInt();
					selectedShareName = userShareNames.get(option3);
				} catch (IndexOutOfBoundsException exception) {
					System.out.println("Invalid share option entered, Please enter share option listed above");
					userInput = new Scanner(System.in);
					wrongOption = true;
				}catch (InputMismatchException exception) {
					System.out.println("Invalid share option type entered, Please enter share option integer as listed above");
					userInput = new Scanner(System.in);
					wrongOption = true;
				}
			} while (wrongOption);

			List<UserTransaction> shareTransactionDetailsList = MultiValueHashMap
					.getUserTransactionMap(userAccountNumber, selectedShareName);
			if (shareTransactionDetailsList.size() == 0) {
				System.out.println("This share is not available in your account");
			}
			for (int i = 0; i < shareTransactionDetailsList.size(); i++) {
				System.out.println("Transaction Date: " + shareTransactionDetailsList.get(i).getTransactionDate() + " "
						+ "Share Name: " + shareTransactionDetailsList.get(i).getshareName() + " " + "Share Price: "
						+ shareTransactionDetailsList.get(i).getSharePrice() + " " + "Transaction Type:  "
						+ shareTransactionDetailsList.get(i).getTransactionType() + " " + "Quanity of Shares: "
						+ shareTransactionDetailsList.get(i).getShareQuantity() + " " + "Transaction Charge: "
						+ shareTransactionDetailsList.get(i).getTransactionChargeRate() + " "
						+ "Securities Transfer Tax: " + shareTransactionDetailsList.get(i).securitiesTransferTaxRate());
			}
		}
	}
	public static void displayAcountDetails( int accountnumber){
		UserData userDisplay = UserDataHashMap.getAccountDetails(accountnumber);
		System.out.println("Hi " +userDisplay.getUserName());
		System.out.println("Account number is " +userDisplay.getAccountNumber());
		System.out.println("Account balance is " +userDisplay.getAccountBalance() +"INR" +'\n');
		System.out.println("    Your Current Holdings     "); 
		System.out.println("================================="); 
		Map <String, UserShare> userShareData =  HashInHash.getUserShareMap(accountnumber);
		if(userShareData.isEmpty()){
		 System.out.println("No Shares available in account"+'\n'); 
		}
		else{
			System.out.println("ShareName      " + '\t'+ "SharePrice" + '\t' +"ShareQuantity");
			String shareName ;
			double sharePrice;
			int shareQuantity ;
			for(String share : userShareData.keySet()) {
				shareName = userShareData.get(share).getShareName();
				sharePrice = userShareData.get(share).getAverageSharePrice();
				shareQuantity = userShareData.get(share).getShareQuantity();
				int shareLength = shareName.length();
				for(int i= shareLength;i<15;i++) {
					shareName = shareName + " ";
				}
				System.out.printf(shareName + "%8.2f"+ "%d",sharePrice,shareQuantity);
				System.out.println("");
			}
		}
	 }
	
	public static void createAccount(){
		Scanner userInput = new Scanner (System.in);
		System.out.println("Enter below details");
		System.out.println("Enter Username : ");
		String username = userInput.next();
		String password;
		String passwordMatch;
		// Checks for password match
		do {
			System.out.println("Enter Password : ");
			password = userInput.next();
			System.out.println("Re-enter Password : ");
			passwordMatch = userInput.next();
		} while (!password.equals(passwordMatch));

		UserData newUser = new UserData(username, password);
		UserDataHashMap.addUser(newUser);

		System.out.println("Account created successfully, Your Account number is : " + newUser.getAccountNumber());
		int quitchoice = UserMenu.firstPageMenu();
		UserMenu.loginChoicePage(quitchoice);
	}
}
