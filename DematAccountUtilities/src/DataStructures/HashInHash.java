package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.DatabaseMaintainer;
import Entities.UserShare;

public class HashInHash {
	
	private static Map <Integer, Map <String, UserShare>> userShareMap;
	
	static {
		HashInHash.userShareMap = new HashMap <Integer, Map <String, UserShare>>();
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
	
	public static List <UserShare> getUserShareMap(int accountNumber){
		HashInHash.createUserShareMap(DatabaseMaintainer.readEntities("UserShares"));
		List <UserShare> userShareList = new ArrayList <UserShare> ();
		if (HashInHash.userShareMap.containsKey(accountNumber)) {
			for(String userShareName: HashInHash.userShareMap.get(accountNumber).keySet()) {
				userShareList.add(HashInHash.userShareMap.get(accountNumber).get(userShareName));
			}
		}
		return userShareList;
	}
	
	public static void updateUserShareMap(UserShare newUserShare) {
		HashInHash.createUserShareMap(DatabaseMaintainer.readEntities("UserShare"));
		if (HashInHash.userShareMap.containsKey(newUserShare.getAccountNumber()) && HashInHash.userShareMap.get(newUserShare.getAccountNumber()).containsKey(newUserShare.getShareName())) {
			double averageSharePrice = HashInHash.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).getAverageSharePrice();
			int shareQuantity= HashInHash.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).getShareQuantity();
			averageSharePrice = (averageSharePrice*shareQuantity)+(newUserShare.getAverageSharePrice()*newUserShare.getShareQuantity())/(shareQuantity+newUserShare.getShareQuantity());
			shareQuantity = shareQuantity+newUserShare.getShareQuantity();
			HashInHash.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).setAverageSharePrice(averageSharePrice);
			HashInHash.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).setShareQuantity(shareQuantity);
			DatabaseMaintainer.writeEntities(HashInHash.storeUserShareMap(), "UserShare");
		}
		else {
			HashInHash.userShareMap.put(newUserShare.getAccountNumber(), new HashMap<String,UserShare>());
			HashInHash.userShareMap.get(newUserShare.getAccountNumber()).put(newUserShare.getShareName(), newUserShare);
			DatabaseMaintainer.addEntities(newUserShare.toString(), "UserShare");
		}
	}

	public static void main(String[] args) {
		
	}

}
