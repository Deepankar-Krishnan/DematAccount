/**
 * 
 */
package UserInterface;

import java.util.Scanner;

import DataStructures.UserDataHashMap;
import UserFunctions.AdminFunctions;
import UserFunctions.CustomerFunctions;

/**
 * @author kddeepan
 *
 */
public class MainPage {
	public static void main(String[] args) {
		UserMenu menu = new UserMenu();
		int choice1 = menu.firstPageMenu();
	    menu.loginChoicePage(choice1);
	}
}
