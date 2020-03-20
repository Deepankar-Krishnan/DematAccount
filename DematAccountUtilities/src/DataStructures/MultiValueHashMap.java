/**
 * 
 */
package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.UserTransaction;

/**
 * @author kddeepan
 *
 */
public class MultiValueHashMap {
	
	private Map<Integer, List <UserTransaction>> userTransactionMap;

	public MultiValueHashMap(){
			this.userTransactionMap = new HashMap<Integer, List<UserTransaction>>();
	}
	
	public Map<Integer, List <UserTransaction>> getUserTransactionMap(){
		return this.userTransactionMap;
	}
	
	public void setUserTransactionMap(Map <Integer, List <UserTransaction>> userTransactionMap) {
		this.userTransactionMap = userTransactionMap;
	}
	
	public List <String> storeUserTransactionMap() {		
		List <String> dataList = new ArrayList <String> ();		
			for(int accountNumber: this.userTransactionMap.keySet()) {
				for(UserTransaction userTransaction: this.userTransactionMap.get(accountNumber)) {
					dataList.add(userTransaction.toString());
				}
			}
		return dataList;
	}
	
	public void createUserTransactionMap(List <String> dataList) {
		String [] userTransactionAttribute;
		for(String userTransactionData: dataList) {
			userTransactionAttribute = userTransactionData.split(",");
			if(this.userTransactionMap.containsKey(Integer.parseInt(userTransactionAttribute[0]))){
				this.userTransactionMap.get(Integer.parseInt(userTransactionAttribute[0])).add(new UserTransaction(Integer.parseInt(userTransactionAttribute[0]),userTransactionAttribute[1],userTransactionAttribute[2],Double.parseDouble(userTransactionAttribute[3]),Integer.parseInt(userTransactionAttribute[4])));
			}
			else {
				this.userTransactionMap.put(Integer.parseInt(userTransactionAttribute[0]), new ArrayList <UserTransaction> ());
				this.userTransactionMap.get(Integer.parseInt(userTransactionAttribute[0])).add(new UserTransaction(Integer.parseInt(userTransactionAttribute[0]),userTransactionAttribute[1],userTransactionAttribute[2],Double.parseDouble(userTransactionAttribute[3]),Integer.parseInt(userTransactionAttribute[4])));
			}
		}		
	}
	
	public List <String> getUserTransactionMap(int accountNumber){
		List <String> userTransactionList = new ArrayList <String>();
		if(this.userTransactionMap.containsKey(accountNumber)) {
			for(UserTransaction userTransaction: this.userTransactionMap.get(accountNumber)) {
				userTransactionList.add(userTransaction.toString());
			}
		}
		return userTransactionList;
	}
	
	public void updateUserTransactionMap(UserTransaction newTransaction){
		this.userTransactionMap.get(newTransaction.getAccountNumber()).add(newTransaction);
	}
	
	
	public static void main(String[] args) {
		System.out.println("CheckMultiValueHashMap");
		 
	}

	}
