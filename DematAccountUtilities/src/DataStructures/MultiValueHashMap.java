/**
 * 
 */
package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.UserShare;
import Entities.UserTransaction;

/**
 * @author kddeepan
 *
 */
public class MultiValueHashMap {
	
	private Map<Integer, List <UserShare>> userShareMap;
	private Map<Integer, List <UserTransaction>> userTransactionMap;
	
	public MultiValueHashMap() {
		
	}

	public MultiValueHashMap(String entityType) {
		if (entityType.equals("UserShare")) {
			//List <UserShare> userShareList = new ArrayList <UserShare> ();
			this.userShareMap = new HashMap<Integer, List <UserShare>>();
		}
		else {
			//List <UserTransaction> userTransactionList = new ArrayList <UserTransaction>();
			this.userTransactionMap = new HashMap<Integer, List<UserTransaction>>();
		}
	}

	public Map<Integer, List <UserShare>> getUserShareMap(){
		return this.userShareMap;
	}
	
	public Map<Integer, List <UserTransaction>> getUserTransactionMap(){
		return this.userTransactionMap;
	}
	
	public void setUserShareMap(Map <Integer, List <UserShare>> userShareMap) {
		this.userShareMap = userShareMap;
	}
	
	public void setUserTransactionMap(Map <Integer, List <UserTransaction>> userTransactionMap) {
		this.userTransactionMap = userTransactionMap;
	}
	
	public void toString(String entityType) {
		
		if (entityType.equals("UserShare")) {
			for(int accountNumber: this.userShareMap.keySet()) {
				for(UserShare userShare: this.userShareMap.get(accountNumber)) {
					System.out.println(userShare.toString());
				}
			}
		}
		else {
			for(int accountNumber: this.userTransactionMap.keySet()) {
				for(UserTransaction userTransaction: this.userTransactionMap.get(accountNumber)) {
					System.out.println(userTransaction.toString());
				}
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("CheckMultiValueHashMap");
		 /*UserShare deepankar = new UserShare (719601, "Adani", 256.68, 100);
		 UserShare sandeep = new UserShare (123456, "Ambani", 256.68, 100);
		 UserShare ranadheer = new UserShare (897651, "Reliance", 256.68, 100);
		 UserShare dhruv = new UserShare (185678, "Amazon", 256.68, 100);*/
		 
		 /*List <UserShare> userShareList = new ArrayList <UserShare> ();
		 
		 userShareList.add(deepankar);
		 userShareList.add(sandeep);
		 userShareList.add(ranadheer);
		 userShareList.add(dhruv);*/
		 
		 MultiValueHashMap shares = new MultiValueHashMap("UserShare");
		 
		 /*Map<Integer, List <UserShare>> map = shares.getUserShareMap();
		 map.put(719601,userShareList);
		 shares.toString("UserShare");*/
		 
		 shares.userShareMap.put(123456, new ArrayList <UserShare>());
		 List <UserShare> list = shares.userShareMap.get(123456);
		 list.add(new UserShare (123456, "Ambani", 256.68, 100));
		 //shares.toString("UserShare");
		 
		 //shares.userShareMap.put(123456, new ArrayList <UserShare>());
		 list = shares.userShareMap.get(123456);
		 list.add(new UserShare (123456, "Adani", 256.68, 100));
		 shares.toString("UserShare");
		 
		 /*UserTransaction deepankarTransaction = new UserTransaction (123489, "Buy", 190.68, 150);
		 UserTransaction sandeepTransaction = new UserTransaction (123456, "Buy", 190.68, 150);
		 UserTransaction ranadheerTransaction = new UserTransaction (897651, "Buy", 190.68, 150);
		 UserTransaction dhruvTransaction = new UserTransaction (185678, "Buy", 190.68, 150);*/

	}

}
