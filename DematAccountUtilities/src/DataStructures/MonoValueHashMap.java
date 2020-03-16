/**
 * 
 */
package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.ShareMarket;
import Entities.UserData;

/**
 * @author kddeepan
 *
 */

public class MonoValueHashMap {
	
	private Map<Integer, UserData> userDataMap;
	private Map<String, ShareMarket> shareMarketMap;

	public MonoValueHashMap() {
		
	}
	
	public MonoValueHashMap(String entityType) {
		if (entityType.equals("UserData")) {
			this.userDataMap = new HashMap <Integer, UserData> ();
		}
		else {
			this.shareMarketMap = new HashMap <String, ShareMarket>();
		}
	}
	
	public Map<Integer, UserData> getUserDataMap() {
		return this.userDataMap;
	}
	
	public Map<String, ShareMarket> getShareMarketMap() {
		return this.shareMarketMap;
	}
	
	public void setUserDataMap(Map<Integer, UserData> userDataMap) {
		this.userDataMap = userDataMap;
	}
	
	public void setShareMarketMap(Map<String, ShareMarket> shareMarketMap) {
		this.shareMarketMap = shareMarketMap;
	}
	
	public List <String> toString(String entityType) {
		List <String> dataList = new ArrayList<String> ();
		if (entityType.equals("UserData")) {			
			for(int accountNumber:this.userDataMap.keySet()) {				
				dataList.add(this.userDataMap.get(accountNumber).toString());
				}
		}
		else {			
			for(String shareName : this.shareMarketMap.keySet()) {
				dataList.add(this.shareMarketMap.get(shareName).toString());
			}
		}
		return dataList;
	}
	
	public void createMap (List <String> dataList, String entityType) {
		if (entityType.equals("UserData")) {			
			for(String data:dataList) {
				String [] userAttributes = data.split(",");
				this.userDataMap.put(Integer.parseInt(userAttributes[1]), new UserData(userAttributes[0],Integer.parseInt(userAttributes[1]),Double.parseDouble(userAttributes[2])));
				}
		}
		else {			
			for(String data:dataList) {
				String [] shareAttributes = data.split(",");
				this.shareMarketMap.put(shareAttributes[0], new ShareMarket(shareAttributes[0],Double.parseDouble(shareAttributes[1]),Integer.parseInt(shareAttributes[2])));
				}
		}
		
	}
	
	public static void main(String[] args) {
		
		/*UserData deepankar = new UserData ("Deepankar", 12345, 1700.50);
		UserData sandeep = new UserData ("Sandeep", 789432, 3628.50);
		UserData ranadheer = new UserData ("Ranadheer", 65489, 89763.50);
		UserData dhruv = new UserData ("Dhruv", 81767, 873426.50);*/
		
		MonoValueHashMap users = new MonoValueHashMap ("UserData");
		
		users.userDataMap.put(12345, new UserData ("Deepankar", 12345, 1700.50));
		users.userDataMap.put(789432, new UserData ("Sandeep", 789432, 3628.50));
		users.userDataMap.put(65489, new UserData ("Ranadheer", 65489, 89763.50));
		users.userDataMap.put(81767, new UserData ("Dhruv", 81767, 873426.50));
		
		//userHashMap.put(12345, deepankar);
		
		List <String> list = users.toString("UserData");
		for(String data: list) {
			System.out.println(data);
		}

	}

}
