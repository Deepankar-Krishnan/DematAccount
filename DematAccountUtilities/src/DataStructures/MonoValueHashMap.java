/**
 * 
 */
package DataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseMaintainer;
import Entities.ShareMarket;
import Entities.UserData;

/**
 * @author kddeepan
 *
 */

public class MonoValueHashMap {
	
	private static Map<Integer, UserData> userDataMap;
	private static Map<String, ShareMarket> shareMarketMap;
	
	static {
		MonoValueHashMap.userDataMap = new HashMap <Integer, UserData> ();
		MonoValueHashMap.shareMarketMap = new HashMap <String, ShareMarket>();
	}

	private MonoValueHashMap() {
		
	}
	
	public Map<Integer, UserData> getUserDataMap() {
		return MonoValueHashMap.userDataMap;
	}
	
	public Map<String, ShareMarket> getShareMarketMap() {
		return MonoValueHashMap.shareMarketMap;
	}
	
	public void setUserDataMap(Map<Integer, UserData> userDataMap) {
		MonoValueHashMap.userDataMap = userDataMap;
	}
	
	public void setShareMarketMap(Map<String, ShareMarket> shareMarketMap) {
		MonoValueHashMap.shareMarketMap = shareMarketMap;
	}
	
	private static List <String> storeMap(String entityType) {
		List <String> dataList = new ArrayList<String> ();
		if (entityType.equals("UserData")) {			
			for(int accountNumber: MonoValueHashMap.userDataMap.keySet()) {				
				dataList.add(MonoValueHashMap.userDataMap.get(accountNumber).toString());
				}
		}
		else {			
			for(String shareName : MonoValueHashMap.shareMarketMap.keySet()) {
				dataList.add(MonoValueHashMap.shareMarketMap.get(shareName).toString());
			}
		}
		return dataList;
	}
	
	private static void createMap (List <String> dataList, String entityType) {
		if (entityType.equals("UserData")) {			
			for(String userData:dataList) {
				String [] userAttributes = userData.split(",");
				MonoValueHashMap.userDataMap.put(Integer.parseInt(userAttributes[1]), new UserData(userAttributes[0],Integer.parseInt(userAttributes[1]),Double.parseDouble(userAttributes[2]),userAttributes[3]));
				}
		}
		else {			
			for(String shareData:dataList) {
				String [] shareAttributes = shareData.split(",");
				MonoValueHashMap.shareMarketMap.put(shareAttributes[0], new ShareMarket(shareAttributes[0],Double.parseDouble(shareAttributes[1]),Integer.parseInt(shareAttributes[2])));
				}
		}
		
	}
	
	public static UserData getAccountDetails (int accountNumber) {
		MonoValueHashMap.createMap(DatabaseMaintainer.readEntities("UserData"),"UserData");			
		return MonoValueHashMap.userDataMap.get(accountNumber);
	}
	
	public static Map <String, ShareMarket> getShareMarket(){
		MonoValueHashMap.createMap(DatabaseMaintainer.readEntities("ShareMarket"),"ShareMarket");
		return MonoValueHashMap.shareMarketMap;
	}
	
	public static void addUser(UserData newUser) {
		MonoValueHashMap.userDataMap.put(newUser.getAccountNumber(),newUser);
		DatabaseMaintainer.addEntities(newUser.toString(), "UserData");
	}
	
	public static void addShare(ShareMarket newShare) {
		MonoValueHashMap.shareMarketMap.put(newShare.getShareName(),newShare);
		DatabaseMaintainer.addEntities(newShare.toString(), "ShareMarket");
	}
	
	public static void editUser(UserData updatedUser) {
		MonoValueHashMap.userDataMap.put(updatedUser.getAccountNumber(),updatedUser);
		DatabaseMaintainer.writeEntities(MonoValueHashMap.storeMap("UserData"), "UserData");
	}
	
	public static void editShare(ShareMarket updatedShare) {
		MonoValueHashMap.shareMarketMap.put(updatedShare.getShareName(),updatedShare);
		DatabaseMaintainer.writeEntities(MonoValueHashMap.storeMap("ShareMarket"), "ShareMarket");
	}
	
	public static boolean validateLogin (int userAccountNumber, String userAccountPassword) {
		MonoValueHashMap.createMap(DatabaseMaintainer.readEntities("UserData"),"UserData");
		return MonoValueHashMap.userDataMap.containsKey(userAccountNumber) && userAccountPassword.equals(MonoValueHashMap.userDataMap.get(userAccountNumber).getAccountPassword());
	}
	
	public static void main(String[] args) {
		
		/*UserData deepankar = new UserData ("Deepankar", 12345, 1700.50);
		UserData sandeep = new UserData ("Sandeep", 789432, 3628.50);
		UserData ranadheer = new UserData ("Ranadheer", 65489, 89763.50);
		UserData dhruv = new UserData ("Dhruv", 81767, 873426.50);*/
		
		//MonoValueHashMap users = new MonoValueHashMap ("UserData");
		
		/*users.userDataMap.put(12345, new UserData ("Deepankar", 12345, 1700.50));
		users.userDataMap.put(789432, new UserData ("Sandeep", 789432, 3628.50));
		users.userDataMap.put(65489, new UserData ("Ranadheer", 65489, 89763.50));
		users.userDataMap.put(81767, new UserData ("Dhruv", 81767, 873426.50));
		users.userDataMap.put(12345, new UserData ("Krishnan", 12345, 1700.50));
		*/
		
		//userHashMap.put(12345, deepankar);
		
		List <String> list = new ArrayList <String> (Arrays.asList("Deepankar,12345,1700.50","Sandeep,789432,3654.90","Ranadheer,65489,8976.49","Dhruv,81767,873426.50"));
		
		
		
		System.out.println("Initial values./n");
		System.out.println();
		
		
		for(String data: list) {
			System.out.println(data);
		}
		
		list = new ArrayList <String> (Arrays.asList("Krishna,12345,850","Deepankar Krishnan,11208147,7.05"));
		
		
		
		System.out.println();
		System.out.println("After updating the value./n");
		System.out.println();
		
		
		for(String data: list) {
			System.out.println(data);
		}

	}

}
