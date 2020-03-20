package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.UserShare;

public class HashInHash {
	
	private Map <Integer, Map <String, UserShare>> userShareMap;
	
	public HashInHash () {
		this.userShareMap = new HashMap <Integer, Map <String, UserShare>>();
	}
	
	public List <String> storeUserShareMap(){
		List <String> dataList = new ArrayList <String>();
		for (int accountNumber:this.userShareMap.keySet()) {
			for (String shareName:this.userShareMap.get(accountNumber).keySet()){
				dataList.add(this.userShareMap.get(accountNumber).get(shareName).toString());
			}			
		}		
		return dataList;
	}
	
	public void createUserShareMap(List <String> dataList) {
		for(String userShareDetails: dataList) {
			String [] userShareAttribute = userShareDetails.split(",");
			if(this.userShareMap.containsKey(Integer.parseInt(userShareAttribute[0]))) {
				this.userShareMap.get(Integer.parseInt(userShareAttribute[0])).put(userShareAttribute[1], new UserShare(Integer.parseInt(userShareAttribute[0]), userShareAttribute[1], Double.parseDouble(userShareAttribute[2]), Integer.parseInt(userShareAttribute[3])));
			}
			else {
				this.userShareMap.put(Integer.parseInt(userShareAttribute[0]), new HashMap <String, UserShare> ());
				this.userShareMap.get(Integer.parseInt(userShareAttribute[0])).put(userShareAttribute[1], new UserShare(Integer.parseInt(userShareAttribute[0]), userShareAttribute[1], Double.parseDouble(userShareAttribute[2]), Integer.parseInt(userShareAttribute[3])));
			}
		}		
	}
	
	public List <UserShare> getUserShareMap(int accountNumber){
		List <UserShare> userShareList = new ArrayList <UserShare> ();
		if (this.userShareMap.containsKey(accountNumber)) {
			for(String userShareName: this.userShareMap.get(accountNumber).keySet()) {
				userShareList.add(this.userShareMap.get(accountNumber).get(userShareName));
			}
		}
		return userShareList;
	}
	
	public void updateUserShareMap(UserShare newUserShare) {
		if (this.userShareMap.containsKey(newUserShare.getAccountNumber()) && this.userShareMap.get(newUserShare.getAccountNumber()).containsKey(newUserShare.getShareName())) {
			double averageSharePrice = this.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).getAverageSharePrice();
			int shareQuantity= this.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).getShareQuantity();
			averageSharePrice = (averageSharePrice*shareQuantity)+(newUserShare.getAverageSharePrice()*newUserShare.getShareQuantity())/(shareQuantity+newUserShare.getShareQuantity());
			shareQuantity = shareQuantity+newUserShare.getShareQuantity();
			this.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).setAverageSharePrice(averageSharePrice);
			this.userShareMap.get(newUserShare.getAccountNumber()).get(newUserShare.getShareName()).setShareQuantity(shareQuantity);
		}
		else {
			this.userShareMap.put(newUserShare.getAccountNumber(), new HashMap<String,UserShare>());
			this.userShareMap.get(newUserShare.getAccountNumber()).put(newUserShare.getShareName(), newUserShare);
		}
	}

	public static void main(String[] args) {
		
	}

}
