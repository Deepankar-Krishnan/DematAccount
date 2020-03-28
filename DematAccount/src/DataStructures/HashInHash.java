package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseMaintainer;
import DematAccountEntities.UserShare;

public class HashInHash {
	private static Map <Integer, Map <String, UserShare>> userShareMap;
	
	static {
		HashInHash.userShareMap = new HashMap <Integer, Map <String, UserShare>>();
		HashInHash.createUserShareMap(DatabaseMaintainer.readEntities("UserShare"));
	}
	
	private HashInHash () {
		
	}
	
	private static List <String> storeUserShareMap(){
		List <String> dataList = new ArrayList <String>();
		for (int accountNumber:HashInHash.userShareMap.keySet()) {
			for (String shareName:HashInHash.userShareMap.get(accountNumber).keySet()){
				dataList.add(HashInHash.userShareMap.get(accountNumber).get(shareName).toString());
			}			
		}		
		return dataList;
	}
	
	private static void createUserShareMap(List <String> dataList) {
		for(String userShareDetails: dataList) {
			String [] userShareAttribute = userShareDetails.split(",");
			if(HashInHash.userShareMap.containsKey(Integer.parseInt(userShareAttribute[0]))) {
				HashInHash.userShareMap.get(Integer.parseInt(userShareAttribute[0])).put(userShareAttribute[1], new UserShare(Integer.parseInt(userShareAttribute[0]), userShareAttribute[1], Double.parseDouble(userShareAttribute[2]), Integer.parseInt(userShareAttribute[3])));
			}
			else {
				HashInHash.userShareMap.put(Integer.parseInt(userShareAttribute[0]), new HashMap <String, UserShare> ());
				HashInHash.userShareMap.get(Integer.parseInt(userShareAttribute[0])).put(userShareAttribute[1], new UserShare(Integer.parseInt(userShareAttribute[0]), userShareAttribute[1], Double.parseDouble(userShareAttribute[2]), Integer.parseInt(userShareAttribute[3])));
			}
		}		
	}
	
	public static Map <String, UserShare> getUserShareMap(int accountNumber){
		Map <String, UserShare> userShareList = new HashMap <String, UserShare> ();
		if (HashInHash.userShareMap.containsKey(accountNumber)) {
			for(String userShareName: HashInHash.userShareMap.get(accountNumber).keySet()) {
				userShareList.put(userShareName,HashInHash.userShareMap.get(accountNumber).get(userShareName));
			}
		}
		return userShareList;
	}
	
	public static void updateUserShareMap(UserShare newUserShare, String transactionType) {
		if (transactionType.equals("Bought")) {
			if (HashInHash.userShareMap.containsKey(newUserShare.getAccountNumber()) && HashInHash.userShareMap.get(newUserShare.getAccountNumber()).containsKey(newUserShare.getShareName())) {
				double currentAverageSharePrice = HashInHash.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).getAverageSharePrice();
				int currentShareQuantity= HashInHash.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).getShareQuantity();
				currentAverageSharePrice = ((currentAverageSharePrice*currentShareQuantity)+(newUserShare.getAverageSharePrice()*newUserShare.getShareQuantity()))/(currentShareQuantity+newUserShare.getShareQuantity());
				currentShareQuantity = currentShareQuantity+newUserShare.getShareQuantity();
				HashInHash.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).setAverageSharePrice(currentAverageSharePrice);
				HashInHash.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).setShareQuantity(currentShareQuantity);
				DatabaseMaintainer.writeEntities(HashInHash.storeUserShareMap(), "UserShare");
			}
			else {
			HashInHash.userShareMap.put(newUserShare.getAccountNumber(), new HashMap<String,UserShare>());
			HashInHash.userShareMap.get(newUserShare.getAccountNumber()).put(newUserShare.getShareName(), newUserShare);
			DatabaseMaintainer.addEntities(newUserShare.toString(), "UserShare");
			}
		}
		else {
			int currentShareQuantity= HashInHash.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).getShareQuantity();
			currentShareQuantity = currentShareQuantity-newUserShare.getShareQuantity();
			if (currentShareQuantity==0) {
				HashInHash.userShareMap.get(newUserShare.getAccountNumber()).remove(newUserShare.getShareName());
				DatabaseMaintainer.writeEntities(HashInHash.storeUserShareMap(), "UserShare");
				return;
			}
			else {
				HashInHash.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).setShareQuantity(currentShareQuantity);
				DatabaseMaintainer.writeEntities(HashInHash.storeUserShareMap(), "UserShare");
			}
		}
	}
}
