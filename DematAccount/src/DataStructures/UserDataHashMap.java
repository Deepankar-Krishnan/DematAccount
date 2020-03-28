/**
 * 
 */
package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseMaintainer;
import DematAccountEntities.UserData;

/**
 * @author kddeepan
 *
 */

public class UserDataHashMap {
	
	private static Map<Integer, UserData> userDataMap;
	
	static {
		UserDataHashMap.userDataMap = new HashMap <Integer, UserData> ();
		UserDataHashMap.createMap(DatabaseMaintainer.readEntities("UserData"));
	}

	private UserDataHashMap() {
		
	}
	
	private static List <String> storeMap() {
		List <String> dataList = new ArrayList<String> ();			
		for(int accountNumber: UserDataHashMap.userDataMap.keySet()) {				
				dataList.add(UserDataHashMap.userDataMap.get(accountNumber).toString());
		}
		return dataList;
	}
	
	private static void createMap(List <String> dataList) {			
		for(String userData:dataList) {
			String [] userAttributes = userData.split(",");
			UserDataHashMap.userDataMap.put(Integer.parseInt(userAttributes[1]), new UserData(userAttributes[0],Integer.parseInt(userAttributes[1]),Double.parseDouble(userAttributes[2]),userAttributes[3]));
		}
	}
	
	public static UserData getAccountDetails(int accountNumber) {			
		return UserDataHashMap.userDataMap.get(accountNumber);
	}
	
	public static void addUser(UserData newUser) {
		UserDataHashMap.userDataMap.put(newUser.getAccountNumber(),newUser);
		DatabaseMaintainer.addEntities(newUser.toString(), "UserData");
	}
	
	public static void editUser() {
		DatabaseMaintainer.writeEntities(UserDataHashMap.storeMap(), "UserData");
	}
	
	public static boolean validateLogin (int userAccountNumber, String userAccountPassword) {
		return UserDataHashMap.userDataMap.containsKey(userAccountNumber) && userAccountPassword.equals(UserDataHashMap.userDataMap.get(userAccountNumber).getAccountPassword());
	}
}