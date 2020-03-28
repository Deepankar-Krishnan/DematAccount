/**
 * 
 */
package UserInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

import DataStructures.UserDataHashMap;
import UserFunctions.AdminFunctions;
import UserFunctions.CustomerFunctions;

/**
 * @author kddeepan
 *
 */
public class UserMenu {
	public static int customerMainMenu(){
		Scanner userInput = new Scanner (System.in);
		System.out.println("Main Menu Options");
		System.out.println("==================");
		System.out.println("0 - Quit");
		System.out.println("1 - Display Account details");
		System.out.println("2 - Deposit Money");
		System.out.println("3 - Withdraw Money");
		System.out.println("4 - Buy Share");
		System.out.println("5 - Sell Share");
		System.out.println("6 - View Transaction report");
		System.out.println("Enter a number between 0 to 6 : " );
		int customerChoice = userInput.nextInt();
		return customerChoice;
	}
	public static int firstPageMenu() {
		Scanner userInput = new Scanner (System.in);
		int choice1;

		do {
			// Starting Menu
			System.out.println("DEMAT Trading Account Manager");
			System.out.println("=============================");
			System.out.println("1 - Create Account");
			System.out.println("2 - Login");
			System.out.println("=============================");
			System.out.println("Enter valid choice either 1 or 2");	
			// Get choice from user
			choice1 = userInput.nextInt();

		} while (!(choice1 == 1 || choice1 == 2));

		return choice1;
	}

	// Method used to display different Main Menu options after login for admin &
	// user respectively

	public static void loginChoicePage(int choice) {
		Scanner userInput = new Scanner (System.in);
		switch (choice) {
		case 1:
			CustomerFunctions.createAccount();
			break;
		case 2:
			int userAccountNumber = 0;
			String inputPassword;
			do {
				String adminPassword = "admin";
				boolean wrongtypeAccountNumber = false;
				do {
					try {
						wrongtypeAccountNumber = false;
						System.out.println("Enter Account number : ");
						userAccountNumber = userInput.nextInt();
					} catch (InputMismatchException except) {
						System.out.println("Invalid Account Number type is entered, enter integer type account number");
						userInput = new Scanner(System.in);
						wrongtypeAccountNumber = true;
					}
				} while (wrongtypeAccountNumber);
				
				System.out.println("Enter Password : ");
				inputPassword = userInput.next();
				// Check if admin is logged in or the user to provided appropriate menu options
				if (userAccountNumber == 12345678 && inputPassword.equals(adminPassword)) {
					// Admin menu options are provided
					boolean showAdminPage = false;	
	
					do{
						int adminchoice = UserMenu.adminFirstPage();
					
					
						switch(adminchoice)
							{
							case 0:
								System.out.println("You have quit the session");
								System.exit(0);
							case 1:
								AdminFunctions.addShare();
								break;
							case 2:
								AdminFunctions.updateSharePrice();
								break;
							case 3:
								AdminFunctions.updateShareQuantity();
								break;
							case 4:
								AdminFunctions.updateTransactionCharge();
								break;
							case 5:
								AdminFunctions.updateSTTCharge();
								break;
							}
					
					}while(true);
					
				}	
			
			}while(!UserDataHashMap.validateLogin(userAccountNumber,inputPassword));
					
					do {
						int choice2 = customerMainMenu();
						switch(choice2)
						{
						case 0:
								System.out.println("You have quit the session");
								System.exit(0);					
						case 1:
							CustomerFunctions.displayAcountDetails(userAccountNumber);
							break;
						case 2:
							CustomerFunctions.depositMoney(userAccountNumber);
							break;
						case 3:
							CustomerFunctions.withdrawMoney(userAccountNumber);
							break;
						case 4:
							CustomerFunctions.buyShares(userAccountNumber);
							break;
						case 5:
							CustomerFunctions.sellShares(userAccountNumber);
							break;
						case 6:
							CustomerFunctions.viewTransactionReport(userAccountNumber);
							break;
						}
				}while(true);
		
		}
	}
	
	public static int adminFirstPage(){
		Scanner userInput = new Scanner(System.in);
		System.out.println("Admin Menu Options");
		System.out.println("==================");
		System.out.println("0 - Quit");
		System.out.println("1 - Add Shares");
		System.out.println("2 - Update Share price");
		System.out.println("3 - Update Share quantity");
		System.out.println("4 - Update Transaction Charge ");
		System.out.println("5 - Update STT");
		System.out.println("Enter Option : ");
		int adminChoice = userInput.nextInt();
		return adminChoice;
	}
}
